package com.example.android.myfirstandroidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.myfirstandroidapp.data.New;
import com.example.android.myfirstandroidapp.utilities.NetworkUtils;
import com.example.android.myfirstandroidapp.utilities.NewsJsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView resultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultsTextView = (TextView) findViewById(R.id.m_news_result);

        showNews();
    }

    /*
     * Constructs the URL and makes the query in a new thread.
     */
    private void showNews() {
        URL newsUrl = NetworkUtils.getNewsUrl();
        new NewsQueryTask().execute(newsUrl);
    }

    class NewsQueryTask extends AsyncTask<URL,Void,New[]> {

        @Override
        protected New[] doInBackground(URL... urls) {
            URL urlsearch = urls[0];

            New[] newsList = null;
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(urlsearch);
                newsList = NewsJsonUtils.getNewsFromJson(jsonResponse);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return newsList;
        }

        @Override
        protected void onPostExecute(New[] newsList) {
            //if(s!=null && !s.equals("")){
                resultsTextView.setText(newsList[0].title);
            //}

        }
    }



}
