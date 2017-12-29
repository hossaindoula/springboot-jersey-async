package org.doula.async.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.doula.async.utils.OkClient;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    @Autowired
    OkClient okClient;

    private final String target = "https://api.github.com/";

    public Future<Map<Object, Object>> userAsync(String user) {
        return okClient.asyncCall(target + "users" + "/" + user);
    }

    public Future<Map<Object, Object>> reposAsync(String user) {
        return okClient.asyncCall(target + "users" + "/" + user + "/" + "repos" );
    }

    public Future<Map<Object, Object>> contributorsAsync(String owner, String repo) {
        return okClient.asyncCall("/repos/" + owner + "/" + repo + "/" + "contributors");
    }
}
