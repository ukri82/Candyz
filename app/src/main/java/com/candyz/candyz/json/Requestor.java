package com.candyz.candyz.json;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;



import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Windows on 02-03-2015.
 */
public class Requestor {
    public static JSONObject requestMoviesJSON(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                (String)null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.e("requestMoviesJSON", e + "");
        } catch (ExecutionException e) {
            Log.e("requestMoviesJSON", e + "");
        } catch (TimeoutException e) {
            Log.e("requestMoviesJSON", e + "");
        }
        return response;
    }
}
