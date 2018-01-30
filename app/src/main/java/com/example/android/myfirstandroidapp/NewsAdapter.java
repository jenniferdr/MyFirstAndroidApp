package com.example.android.myfirstandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.ToggleButton;

import com.example.android.myfirstandroidapp.data.New;
import com.example.android.myfirstandroidapp.data.NewsContract;
import com.example.android.myfirstandroidapp.data.NewsDbHelper;


/**
 * Created by Jennifer on 1/28/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    private New[] newsList;
    private SQLiteDatabase mDb;

    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String webUrl);
    }

    public NewsAdapter(ListItemClickListener mOnClickListener, Context context) {
        this.onClickListener = mOnClickListener;
        NewsDbHelper dbHelper = new NewsDbHelper(context);
        mDb = dbHelper.getWritableDatabase();
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

    private long addNewGuest(String title, String author, String url) {
        ContentValues cv = new ContentValues();
        cv.put(NewsContract.NewsEntry.COLUMN_TITLE, title);
        cv.put(NewsContract.NewsEntry.COLUMN_AUTHOR, author);
        cv.put(NewsContract.NewsEntry.COLUMN_URL, url);
        return mDb.insert(NewsContract.NewsEntry.TABLE_NAME, null, cv);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView titleTextView;
        public final ToggleButton toggleButton;

        public NewsAdapterViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.news_title);
            view.setOnClickListener(this);

            toggleButton = (ToggleButton) view.findViewById(R.id.myToggleButton);
            // TODO: Settearlo como true si la noticia esta en la BD
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_empty_star));
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_black_star));
                        int clickedPosition = getAdapterPosition();
                        // guardar newsList[clickedPosition] en la base de datos
                        // TODO: Verificar primero si la noticia no estaba ya en la BD
                        addNewGuest(newsList[clickedPosition].title, newsList[clickedPosition].author, newsList[clickedPosition].url);
                    }else {
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_empty_star));
                        // TODO: Borrar la noticia de la BD
                    }
                }
            });

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
