package pl.org.sbolimowski.async.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections.MapUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
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
public class OkHttpResponseFuture implements Callback {

    private OkHttpClient client;

    @PostConstruct
    public void setup() {
        client = new OkHttpClient();
    }

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

    public Map<String, Object> requestGet(String connectionUrl, Map<String, Object> param)
        throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(connectionUrl).newBuilder();

        if (!MapUtils.isEmpty(param)) {
            param.forEach((k, v) -> urlBuilder.addQueryParameter(k, v.toString()));
        }

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();

        if (response != null) {
            String responseString = response.body().string();
            return ImmutableMap.of("response", responseString);
        }

        return Collections.emptyMap();
    }
}
