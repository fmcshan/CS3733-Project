package edu.wpi.teamname.simplify;

import com.google.api.client.json.Json;
import edu.wpi.teamname.Authentication.AuthenticationManager;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Requests {
    public static Response post(String _url, JSONObject headers) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(_url);

            post.addHeader(HttpHeaders.AUTHORIZATION, AuthenticationManager.getInstance().userId());

            if (headers != null) {
                List<NameValuePair> formData = new ArrayList<NameValuePair>();

                headers.keySet().forEach(k -> {
                    String val = headers.getString(k);
                    formData.add(new BasicNameValuePair(k, val));
                });

                post.setEntity(new UrlEncodedFormEntity(formData));
            }

            HttpResponse response = client.execute(post);
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder data = new StringBuilder();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    data.append(line);
                }
                return new Response(response.getStatusLine().getStatusCode(), response.getAllHeaders(), data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new Response(response.getStatusLine().getStatusCode(), response.getAllHeaders());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(500);
    }

    public static Response post(String _url) {
        return post(_url, null);
    }

    public static Response get(String _url) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(_url);

            get.addHeader(HttpHeaders.AUTHORIZATION, AuthenticationManager.getInstance().userId());

            HttpResponse response = client.execute(get);
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder data = new StringBuilder();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    data.append(line);
                }
                return new Response(response.getStatusLine().getStatusCode(), response.getAllHeaders(), data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new Response(response.getStatusLine().getStatusCode(), response.getAllHeaders());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(500);
    }
}
