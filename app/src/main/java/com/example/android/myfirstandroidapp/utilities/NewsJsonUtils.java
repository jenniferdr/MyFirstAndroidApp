package com.example.android.myfirstandroidapp.utilities;

import com.example.android.myfirstandroidapp.data.New;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jennifer on 1/28/2018.
 */

public class NewsJsonUtils {

    public static New[] getNewsFromJson(String jsonNews) throws JSONException {

        JSONObject newsJson = new JSONObject(jsonNews);
        JSONArray newsJsonArray = newsJson.getJSONArray("articles");

        New[] newsList = new New[newsJsonArray.length()];

        for (int i = 0; i < newsJsonArray.length(); i++) {

            JSONObject article = newsJsonArray.getJSONObject(i);

            New newObject = new New();
            newObject.title = article.getString("title");
            newObject.author = article.getString("author");
            newObject.description = article.getString("description");
            newObject.url = article.getString("url");

            newsList[i] = newObject;
        }

        return newsList;
    }
}
