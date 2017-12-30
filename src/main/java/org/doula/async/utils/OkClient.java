package org.doula.async.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    private T entity = null;

    private final OkHttpClient client = new OkHttpClient();

    public CompletableFuture<? extends Object> asyncCall(String url) throws RuntimeException {
        OkHttpResponseFuture callback = new OkHttpResponseFuture();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(callback);

        return callback.future.thenApply(response -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body().bytes(), this.entity.getClass());
            } catch (JsonParseException | JsonMappingException e) {
                throw new RuntimeException("");
            } catch (IOException ex) {
                throw new RuntimeException("");
            } finally {
                response.close();
            }
        });
    }

}
