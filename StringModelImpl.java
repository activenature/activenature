package com.active.chdating.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.active.chdating.net.OnStringListener;

public class StringModelImpl {

    private Context context;

    public StringModelImpl(Context context){
        this.context=context;
    }

    public void load(String url, final OnStringListener listener){
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
