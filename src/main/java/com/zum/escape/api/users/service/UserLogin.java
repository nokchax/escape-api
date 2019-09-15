package com.zum.escape.api.users.service;

import com.zum.escape.api.users.domain.User;
import com.zum.escape.api.users.dto.URL;
import okhttp3.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

import static java.lang.System.out;


public class UserLogin {

    public static final String boundary = "----WebKitFormBoundaryMIlRKhiheAsBA5xM";
    public static final MediaType MULTIPART = MediaType.parse("multipart/form-data; boundary=" + boundary);
    public static String csrftoken;
    public static String __cfduid;
    public static String LEETCODE_SESSION;

    private OkHttpHelper okHttpHelper;

    private User user;

    public UserLogin(User user) {
        LEETCODE_SESSION = null;
        this.user = user;
        okHttpHelper = OkHttpHelper.getSingleton(true);
    }


    public boolean doLogin() throws IOException {
        boolean success;
        Connection.Response response = Jsoup.connect(URL.LOGIN)
                .method(Connection.Method.GET)
                .execute();
        csrftoken = response.cookie("csrftoken");
        __cfduid = response.cookie("__cfduid");

        OkHttpClient client = okHttpHelper.getOkHttpClient().newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();

        String form_data = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"csrfmiddlewaretoken\"" + "\r\n\r\n"
                + csrftoken + "\r\n"
                + "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"login\"" + "\r\n\r\n"
                + user.getId() + "\r\n"
                + "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"password\"" + "\r\n\r\n"
                + user.getPassword()+ "\r\n"
                + "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"next\"" + "\r\n\r\n"
                + "/problems" + "\r\n"
                + "--" + boundary + "--";
        RequestBody requestBody = RequestBody.create(MULTIPART, form_data);

        Headers headers = new Headers.Builder()
                .add("Content-Type", "multipart/form-data; boundary=" + boundary)
                .add("Connection", "keep-alive")
                .add("Accept", "*/*")
                .add("Origin", URL.HOME)
                .add("Referer", URL.LOGIN)
                .add("Cookie", "__cfduid=" + __cfduid + ";" + "csrftoken=" + csrftoken)
                .build();

        Response loginResponse = okHttpHelper.postSync(URL.LOGIN, requestBody, headers, client);
        headers = loginResponse.headers();
        List<String> cookies = headers.values("Set-Cookie");
        for (String cookie : cookies) {
            int found = cookie.indexOf("LEETCODE_SESSION");
            if (found > -1) {
                int last = cookie.indexOf(";");
                LEETCODE_SESSION = cookie.substring("LEETCODE_SESSION".length() + 1, last);
            }
        }
        if (LEETCODE_SESSION != null) {
            success = true;
            out.println(user.getName() +" Login Successfully");
        } else {
            success = false;
            out.println(user.getName() +" Login Unsuccessfully");
        }
        loginResponse.close();

        return success;
    }

    public Response getResponse() throws IOException {
        return this.okHttpHelper.getSync(URL.PROBLEMS);
    }
}
