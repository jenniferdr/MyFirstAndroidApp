package com.example.android.myfirstandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.android.myfirstandroidapp.data.New;


/**
 * Created by Jennifer on 1/28/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    private New[] newsList;

    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String webUrl);
    }

    public NewsAdapter(ListItemClickListener mOnClickListener) {
        this.onClickListener = mOnClickListener;
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new NewsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        New newItem = newsList[position];
        holder.titleTextView.setText(newItem.title);
    }

    @Override
    public int getItemCount() {
        if(newsList == null) return 0;
        return newsList.length;
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView titleTextView;

        public NewsAdapterViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.news_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(newsList[clickedPosition].url);
        }
    }

    public void setNewsList(New[] news) {
        newsList = news;
        notifyDataSetChanged();
    }
}
