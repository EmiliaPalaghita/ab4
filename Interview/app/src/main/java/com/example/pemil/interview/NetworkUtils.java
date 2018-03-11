package com.example.pemil.interview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by pemil on 09.03.2018.
 */

class NetworkUtils {

    private static String BASE_URL = "https://api.stackexchange.com/2.2/users";

    private static String ORDER_QUERY = "order";
    private static String ORDER_VALUE = "desc";

    private static String SORT_QUERY = "sort";
    private static String SORT_VALUE = "reputation";

    private static String SITE_QUERY = "site";
    private static String SITE_VALUE = "stackoverflow";

    private static String PAGESIZE_QUERY = "pagesize";
    private static String PAGESIZE_VALUE = "10";

    static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PAGESIZE_QUERY, PAGESIZE_VALUE)
                .appendQueryParameter(ORDER_QUERY, ORDER_VALUE)
                .appendQueryParameter(SORT_QUERY, SORT_VALUE)
                .appendQueryParameter(SITE_QUERY, SITE_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    static Bitmap downloadImageFromPath(String path) throws IOException {
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            bmp = BitmapFactory.decodeStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
