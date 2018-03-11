package com.example.pemil.interview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements UserAdapter.ListItemClickListener {

    private RecyclerView recyclerView;
    private TextView internetErrorTextView;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rec_view);
        internetErrorTextView = findViewById(R.id.internet_error_tv);

        checkInternetConnection();

        /*Gets all data needed to be displayed*/
        users = new ArrayList<>();
        URL url = NetworkUtils.buildUrl();
        new UserDataQueryTask().execute(url);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    /*Method that checks if the phone is connected tot the Internet*/
    private void checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            recyclerView.setVisibility(View.INVISIBLE);
            internetErrorTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            internetErrorTextView.setVisibility(View.INVISIBLE);
        }
    }

    /*Opens the UserActivity activity for an user*/
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        Log.d("SENT USER ", String.valueOf(users.get(clickedItemIndex).toString()));
        intent.putExtra("user", users.get(clickedItemIndex));
        startActivity(intent);
    }

    /*Background task that downloads data from the API*/
    @SuppressLint("StaticFieldLeak")
    public class UserDataQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults != null && !searchResults.equals("")) {
                try {
                    parseJSONData(searchResults);
                    UserAdapter userAdapter = new UserAdapter(users, MainActivity.this);
                    recyclerView.setAdapter(userAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*Create the users based on the json parameter*/
    private void parseJSONData(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray;
        jsonArray = object.getJSONArray("items");

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObj = jsonArray.getJSONObject(i);
                addUser(userObj);
            }
        }
    }

    /*return a hashmap that contains all types of badges*/
    private HashMap<String, Integer> createBadgeMap(JSONObject badges) throws JSONException {
        String goldBadge = badges.getString("gold");
        String silverBadge = badges.getString("silver");
        String bronzeBadge = badges.getString("bronze");

        HashMap<String, Integer> map = new HashMap<>();

        map.put("bronze", Integer.valueOf(bronzeBadge));
        map.put("silver", Integer.valueOf(silverBadge));
        map.put("gold", Integer.valueOf(goldBadge));

        return map;
    }

    /*add an user*/
    private void addUser(JSONObject userObj) throws JSONException {

        User user = new User(
                userObj.getString("display_name"),
                userObj.getString("profile_image"),
                createBadgeMap(userObj.getJSONObject("badge_counts")),
                userObj.getString("location")
        );

        users.add(user);
    }
}
