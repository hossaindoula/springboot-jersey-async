package org.doula.async.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    private static final Logger logger = LoggerFactory.getLogger(OkClient.class);

    private T entity = null;

    private final OkHttpClient client = new OkHttpClient();


    @SuppressWarnings("unchecked")
    public CompletableFuture<T> asyncCall(String url) throws RuntimeException {
        Request request = new Request.Builder().url(url).build();

        OkHttpResponseFuture callback = new OkHttpResponseFuture();
        client.newCall(request).enqueue(callback);

        return callback.future.thenApply(response -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String responseStream = response.body().string();
                logger.info("Responses from server : ", responseStream);
                return (T) mapper.readValue(responseStream, this.entity.getClass());
            } catch (JsonParseException | JsonMappingException ex) {
                logger.info("JsonException occurred due to : ", ex);
                throw new RuntimeException("");
            } catch (IOException ex) {
                logger.info("IOException occurred due to : ", ex);
                throw new RuntimeException("");
            } finally {
                response.close();
            }
        });
    }

    private Future<Response> makeRequest(OkHttpClient client, Request request) {
        Call call = client.newCall(request);

        OkHttpResponseFuture result = new OkHttpResponseFuture();

        call.enqueue(result);

        return result.future;
    }

}
