package com.example.android.myfirstandroidapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.myfirstandroidapp.utilities.NetworkUtils;

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

    class NewsQueryTask extends AsyncTask<URL,Void,String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL urlsearch = urls[0];

            String newsResultString = null;
            try {
                newsResultString = NetworkUtils.getResponseFromHttpUrl(urlsearch);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newsResultString;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null && !s.equals("")){
                resultsTextView.setText(s);
            }

        }
    }



}
