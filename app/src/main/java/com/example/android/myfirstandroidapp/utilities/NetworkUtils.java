package com.example.android.myfirstandroidapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Jennifer on 1/28/2018.
 * Powered by News API newsapi.org
 */

public class NetworkUtils {

    final static String NEWS_URL = "https://newsapi.org/v2/top-headlines";

    final static String PARAM_SOURCES = "sources";
    final static String source = "bbc-news";

    final static String PARAM_KEY = "apiKey";
    final static String apiKey = "aa850c06292c4baba4a9d3fb861c014c";

    /**
     * Builds the URL used to query News API.
     *
     * @return The URL use to query the News API.
     */
    public static URL getNewsUrl() {
        Uri builtUri = Uri.parse(NEWS_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCES, source)
                .appendQueryParameter(PARAM_KEY, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Returns the string result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
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


}
