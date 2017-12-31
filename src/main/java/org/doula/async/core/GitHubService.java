package org.doula.async.core;

import org.doula.async.model.GitHubContributor;
import org.doula.async.model.GitHubRepo;
import org.doula.async.model.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.doula.async.utils.OkClient;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    @Autowired
    OkClient<GitHubUser> gitHubUserOkClient;
    @Autowired
    OkClient<List<GitHubContributor>> gitHubContributorOkClient;
    @Autowired
    OkClient<List<GitHubRepo>> gitHubRepoOkClient;

    private final String target = "https://api.github.com/";

    public Future<GitHubUser> userAsync(String user) {
        return gitHubUserOkClient.asyncCall(target + "users" + "/" + user);
    }

    public Future<List<GitHubRepo>> reposAsync(String user) {
        return gitHubRepoOkClient.asyncCall(target + "users" + "/" + user + "/" + "repos" );
    }

    public Future<List<GitHubContributor>> contributorsAsync(String owner, String repo) {
        return gitHubContributorOkClient.asyncCall("/repos/" + owner + "/" + repo + "/" + "contributors");
    }
}
