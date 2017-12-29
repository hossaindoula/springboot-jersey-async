package org.doula.async.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Mohammed Hossain Doula
 *
 * @hossaindoula | @itconquest
 * <p>
 * http://hossaindoula.com
 * <p>
 * https://github.com/hossaindoula
 */
@Component
public class OkClient {

    private final OkHttpClient client = new OkHttpClient();

    public CompletableFuture<Map<Object, Object>> asyncCall(String url) throws RuntimeException {
        OkHttpResponseFuture callback = new OkHttpResponseFuture();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(callback);

        return callback.future.thenApply(response -> {
            try {
                return ImmutableMap.of("response", response);
            } catch (Exception e) {
                throw new RuntimeException("");
            } finally {
                response.close();
            }
        });
    }

}
