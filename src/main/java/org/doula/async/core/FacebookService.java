package org.doula.async.core;

import org.doula.async.model.GitHubUser;
import org.doula.async.utils.OkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.doula.async.model.FacebookUser;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.Future;

@Component
public class FacebookService {

    private final String target = "http://graph.facebook.com/";

    @Autowired
    OkClient<FacebookUser> facebookUserOkClient;

    public Future<FacebookUser> getUserAsync(String accessToken) {
        return facebookUserOkClient.asyncCall(target + "me" + "?access_token=" + accessToken);
    }
}
