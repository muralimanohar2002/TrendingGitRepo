package com.example.khetipointapp.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.khetipointapp.Adapter.AdapterClass;
import com.example.khetipointapp.ModelClass.ModelClass;
import com.example.khetipointapp.MySingleton;
import com.example.khetipointapp.R;
import com.example.khetipointapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AdapterClass adapterClass;
    List<ModelClass> modelClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        modelClassList = new ArrayList<>();
        initData();
        menuBarTap();
        pullDownRefresh();
    }




    //............................................................................................................Function for custom Action bar
    private void menuBarTap() {
        binding.tripleDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,binding.tripleDot);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.starSort:
                                Collections.sort(modelClassList, ModelClass.byStars);
                                adapterClass.notifyDataSetChanged();
                                return  true;
                            case R.id.nameSort:
                                Collections.sort(modelClassList, ModelClass.byAuthorName);
                                adapterClass.notifyDataSetChanged();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
    }






    //..............................................................................................................Fetching of API data using Volley Library
    public void initData() {
        //RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://private-anon-baa3246138-githubtrendingapi.apiary-mock.com/repositories"; //......For "RetryActivity", try changing the URL

        //.....................................Request a Json-Array response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject repoObject = response.getJSONObject(i);
                                ModelClass modelClass = new ModelClass();
                                modelClass.setImageUrl(repoObject.getString("avatar"));
                                modelClass.setAuthorName(repoObject.getString("author"));
                                modelClass.setRepoName(repoObject.getString("name"));
                                modelClass.setAbout(repoObject.getString("description"));
                                modelClass.setRepoUrl(repoObject.getString("url"));
                                modelClass.setLanguageUsed(repoObject.getString("language"));
                                modelClass.setCurrStars(repoObject.getString("stars"));
                                modelClass.setCurrForks(repoObject.getString("forks"));
                                modelClassList.add(modelClass);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        binding.shimmerFrame.stopShimmer();
                        binding.shimmerFrame.setVisibility(View.GONE);
                        adapterClass = new AdapterClass(modelClassList,getApplicationContext());        //.....Adapter populated
                        binding.Recycler.setAdapter(adapterClass);                                      //.......Recycler view enabled
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                startActivity(new Intent(MainActivity.this, RetryActivity.class));       //...........Error listener activity intent
            }
        })
        {
            //.................................................................................................Caching code
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 15 * 60 * 1000;                              // in 15 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000;                                  // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }
            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonArrayRequest);
    }







    //.........................................................................................................Pull Down Refresh
    private void pullDownRefresh() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shimmerFrame.setVisibility(View.VISIBLE);
                modelClassList.clear();
                initData();
                adapterClass.notifyDataSetChanged();
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }
}