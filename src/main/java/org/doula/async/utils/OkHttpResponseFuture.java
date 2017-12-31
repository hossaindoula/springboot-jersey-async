package org.doula.async.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
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
public class OkHttpResponseFuture implements Callback {

    public final CompletableFuture<Response> future = new CompletableFuture<>();

    public OkHttpResponseFuture() {}

    @Override
    public void onFailure(Call call, IOException e) {
        future.completeExceptionally(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        future.complete(response);
    }

}
