package com.example.pemil.interview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


/**
 * Created by pemil on 11.03.2018.
 */

public class UserActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView location;
    private TextView goldBadge;
    private TextView silverBadge;
    private TextView bronzeBadge;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        profilePicture = findViewById(R.id.profile_image);
        location = findViewById(R.id.location_tv);
        goldBadge = findViewById(R.id.gold_badge_value);
        silverBadge = findViewById(R.id.silver_badge_value);
        bronzeBadge = findViewById(R.id.bronze_badge_value);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        setTitle(user.getUserName());

        populateViews();
    }

    @SuppressLint("SetTextI18n")
    private void populateViews() {
        new ImageTask().execute(user.getUrlPhoto());
        location.setText(user.getLocation());
        Log.d("BADGE MAP", String.valueOf(user.getBadges()));

        goldBadge.setText(Integer.toString(user.getBadges().get("gold")));
        silverBadge.setText(Integer.toString(user.getBadges().get("silver")));
        bronzeBadge.setText(Integer.toString(user.getBadges().get("bronze")));
    }

    @SuppressLint("StaticFieldLeak")
    class ImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bmp = null;
            try {
                bmp = NetworkUtils.downloadImageFromPath(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("BITMAP", String.valueOf(bmp));
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                drawable.setCircular(true);
                profilePicture.setImageDrawable(drawable);
            }
        }
    }
}
