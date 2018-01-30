package com.example.android.myfirstandroidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.myfirstandroidapp.data.New;
import com.example.android.myfirstandroidapp.utilities.NetworkUtils;
import com.example.android.myfirstandroidapp.utilities.NewsJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Jennifer on 1/29/2018.
 */

public class FavoritesFragment extends Fragment implements NewsAdapter.ListItemClickListener {

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    public FavoritesFragment() {
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


        return view;
    }

    @Override
    public void onListItemClick(String webUrl) {

        Intent startChildActivityIntent = new Intent(this.getActivity(), WebViewActivity.class);

        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, webUrl);

        startActivity(startChildActivityIntent);

    }
}