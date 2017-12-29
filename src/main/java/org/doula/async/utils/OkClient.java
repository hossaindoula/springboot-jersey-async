package org.doula.async.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class OkClient<T> {

    private final OkHttpClient client = new OkHttpClient();

    public CompletableFuture<T> asyncCall(String url, T entity) throws RuntimeException {
        OkHttpResponseFuture callback = new OkHttpResponseFuture();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(callback);

        return callback.future.thenApply(response -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body().bytes(), <T>.class);
            } catch (Exception e) {
                throw new RuntimeException("");
            } finally {
                response.close();
            }
        });
    }

}
