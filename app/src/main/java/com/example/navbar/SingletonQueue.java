package com.example.navbar;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonQueue {

    private static SingletonQueue instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private SingletonQueue(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonQueue getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
