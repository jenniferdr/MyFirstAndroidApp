package com.example.android.myfirstandroidapp;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.android.myfirstandroidapp.data.New;
import com.example.android.myfirstandroidapp.utilities.NetworkUtils;
import com.example.android.myfirstandroidapp.utilities.NewsJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Jennifer on 1/29/2018.
 */

public class NewsFragment extends Fragment implements NewsAdapter.ListItemClickListener {

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_news);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(this);

        recyclerView.setAdapter(newsAdapter);

        showNews();
        return view;
    }

    /*
     * Constructs the URL and makes the query in a new thread.
     */
    private void showNews() {
        URL newsUrl = NetworkUtils.getNewsUrl();
        new NewsFragment.NewsQueryTask().execute(newsUrl);
    }

    @Override
    public void onListItemClick(String webUrl) {

        Intent startChildActivityIntent = new Intent(this.getActivity(), WebViewActivity.class);

        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, webUrl);

        startActivity(startChildActivityIntent);

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