package com.example.android.myfirstandroidapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.myfirstandroidapp.data.New;

/**
 * Created by Andre on 1/30/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>{

    private New[] newsList;

    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String webUrl);
    }

    public FavoritesAdapter(ListItemClickListener mOnClickListener) {
        this.onClickListener = mOnClickListener;
    }

    @Override
    public FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.favorites_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new FavoritesAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavoritesAdapterViewHolder holder, int position) {
        New newItem = newsList[position];
        holder.titleTextView.setText(newItem.title);
    }

    @Override
    public int getItemCount() {
        if(newsList == null) return 0;
        return newsList.length;
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView titleTextView;

        public FavoritesAdapterViewHolder(View view) {
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
