package org.doula.async.api;

import org.doula.async.core.FacebookService;
import org.doula.async.core.GitHubService;
import org.doula.async.model.GitHubRepo;
import org.doula.async.model.GitHubUser;
import org.doula.async.model.UserInfo;
import org.doula.async.utils.Futures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.doula.async.model.FacebookUser;
import org.doula.async.model.GitHubContributor;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("/")
@Component
public class AsyncResource {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private TaskExecutor executor;

    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void userInfoAsync(@Suspended AsyncResponse asyncResponse, @PathParam("user") String user) {
        CompletableFuture<GitHubUser> gitHubFuture = Futures.toCompletable(gitHubService.userAsync(user), executor);
        CompletableFuture<FacebookUser> facebookFuture = Futures.toCompletable(facebookService.getUserAsync(user), executor);

        gitHubFuture
                .thenCombine(facebookFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

    }

    @GET
    @Path("/contributors/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void contributorsAsync(@Suspended AsyncResponse asyncResponse, @PathParam("user") String user) {
        Futures.toCompletable(gitHubService.reposAsync(user), executor)
                .thenCompose(repos -> getContributors(user, repos))
                .thenApply(contributors -> contributors.flatMap(List::stream))
                .thenApply(contributors -> contributors.collect(Collectors.groupingBy(
                        GitHubContributor::getLogin,
                        Collectors.counting())))
                .thenApply(contributors -> asyncResponse.resume(contributors))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build())
                );
    }

    private CompletableFuture<Stream<List<GitHubContributor>>> getContributors(String user, List<GitHubRepo> repos) {
        return Futures.sequence(
                repos.stream().limit(5).map(r -> Futures.toCompletable(gitHubService.contributorsAsync(user, r.getName()), executor)));
    }

}

