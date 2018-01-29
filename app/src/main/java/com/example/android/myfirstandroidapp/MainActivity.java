package com.example.android.myfirstandroidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myfirstandroidapp.data.New;
import com.example.android.myfirstandroidapp.utilities.NetworkUtils;
import com.example.android.myfirstandroidapp.utilities.NewsJsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NewsAdapter.ListItemClickListener {

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(this);

        recyclerView.setAdapter(newsAdapter);

        showNews();

    }

    /*
     * Constructs the URL and makes the query in a new thread.
     */
    private void showNews() {
        URL newsUrl = NetworkUtils.getNewsUrl();
        new NewsQueryTask().execute(newsUrl);
    }

    @Override
    public void onListItemClick(String webUrl) {

        Intent startChildActivityIntent = new Intent(MainActivity.this, WebViewActivity.class);

        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, webUrl);

        startActivity(startChildActivityIntent);

        //mToast = Toast.makeText(this, webUrl, Toast.LENGTH_LONG);
        //mToast.show();
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
            // Verificar si no es null
            newsAdapter.setNewsList(newsList);

            //Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            //startActivity(intent);
            //}

        }
    }



}
