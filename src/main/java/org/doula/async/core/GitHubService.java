package org.doula.async.core;

import org.doula.async.model.GitHubContributor;
import org.doula.async.model.GitHubRepo;
import org.doula.async.model.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.doula.async.utils.OkClient;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    @Autowired
    OkClient<GitHubUser> gitHubUserOkClient;
    @Autowired
    OkClient<GitHubContributor> gitHubContributorOkClient;
    @Autowired
    OkClient<GitHubRepo> gitHubRepoOkClient;

    private final String target = "https://api.github.com/";

    public Future<GitHubUser> userAsync(String user) {
        return (Future<GitHubUser>)gitHubUserOkClient.asyncCall(target + "users" + "/" + user);
    }

    public Future<GitHubUser> reposAsync(String user) {
        return (Future<GitHubUser>)gitHubRepoOkClient.asyncCall(target + "users" + "/" + user + "/" + "repos" );
    }

    public Future<GitHubUser> contributorsAsync(String owner, String repo) {
        return (Future<GitHubUser>)gitHubContributorOkClient.asyncCall("/repos/" + owner + "/" + repo + "/" + "contributors");
    }
}
