package com.example.pemil.interview;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements UserAdapter.ListItemClickListener {

    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private Toast mToast;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();
        URL url = NetworkUtils.buildUrl();
        new UserDataQueryTask().execute(url);

        Log.i("USERS SIZE", String.valueOf(users.size()));

        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void initalizeData() {
        users = new ArrayList<>();
        users.add(new User("Emma Wilson", R.drawable.profile));
        users.add(new User("Lavery Maiss", R.drawable.profile));
        users.add(new User("Lillie Watts", R.drawable.profile));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }

    public class UserDataQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
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
                    userAdapter = new UserAdapter(users);
                    recyclerView.setAdapter(userAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
        User user = new User(username, R.drawable.profile);
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
