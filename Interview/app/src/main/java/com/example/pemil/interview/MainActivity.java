package com.example.pemil.interview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private Toast mToast;

    private ArrayList<User> users;

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();
        URL url = NetworkUtils.buildUrl();
        new UserDataQueryTask().execute(url);

        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        Log.d("SENT USER ", String.valueOf(users.get(clickedItemIndex).toString()));
        intent.putExtra("user", users.get(clickedItemIndex));
        startActivity(intent);
    }

    public class UserDataQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                    userAdapter = new UserAdapter(users, MainActivity.this);
                    recyclerView.setAdapter(userAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class UserBitmap {
        private int id;
        private Bitmap bitmap;

        UserBitmap(int id, Bitmap bmp) {
            this.id = id;
            this.bitmap = bmp;
        }
    }

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

    private void addUser(JSONObject userObj) throws JSONException {
        String username = userObj.getString("display_name");
        String profileURL = userObj.getString("profile_image");
        JSONObject badges = userObj.getJSONObject("badge_counts");
        User user = new User(username);
        user.setUrlPhoto(profileURL);
        String goldBadge = badges.getString("gold");
        String silverBadge = badges.getString("silver");
        String bronzeBadge = badges.getString("bronze");
        HashMap<String, Integer> map = new HashMap<>(3);
        map.put("bronze", Integer.valueOf(bronzeBadge));
        map.put("silver", Integer.valueOf(silverBadge));
        map.put("gold", Integer.valueOf(goldBadge));
        user.setBadges(map);

        users.add(user);
    }
}
