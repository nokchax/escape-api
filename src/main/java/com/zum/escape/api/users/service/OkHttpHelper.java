package com.zum.escape.api.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zum.escape.api.thirdPartyAdapter.leetcode.response.ProblemResponse;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class OkHttpHelper {
    private static volatile OkHttpHelper okHttpHelper;
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;


    private OkHttpHelper() {
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new UserCookie())
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();
        objectMapper = new ObjectMapper();
    }

    public static OkHttpHelper getSingleton(boolean reUse) {
        if(reUse==true) {
            okHttpHelper = new OkHttpHelper();
            return okHttpHelper;
        }
        OkHttpHelper result = okHttpHelper;
        if (result == null) {
            synchronized (OkHttpHelper.class) {
                result = okHttpHelper;
                if (result == null) {
                    result = okHttpHelper = new OkHttpHelper();
                }
            }
        }
        return result;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Response getSync(String url) throws IOException {
        return getSync(url, null);
    }

    public Response getSync(String url, Headers headers) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .tag(ProblemResponse.class)
                .build();

        if (headers != null) {
            request.newBuilder().headers(headers)
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        }

        Call call = okHttpClient.newCall(request);
        return call.execute();
    }

    public Response postSync(String url, RequestBody requestBody, Headers headers, OkHttpClient client) throws IOException {
        Request request = new Request.Builder()
                .headers(headers)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                .post(requestBody)
                .url(url)
                .build();
        Call call = client.newCall(request);
        return call.execute();
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws IOException {
        return objectMapper.readValue(json,classOfT);
    }
}
