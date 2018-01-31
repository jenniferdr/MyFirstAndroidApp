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
        void dataBaseChanged();
    }

    public NewsAdapter(ListItemClickListener mOnClickListener, Context context) {
        this.onClickListener = mOnClickListener;
        NewsDbHelper dbHelper = new NewsDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

    }

    private void setIsFavNews(){
        for(New newItem: newsList){
            newItem.setIsFav(mDb);
        }
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
        final int pos = position;
        final NewsAdapterViewHolder holderFinal = holder;
        New newItem = newsList[position];
        holder.titleTextView.setText(newItem.title);

        boolean checked = newsList[position].isFav;
        holder.toggleButton.setChecked(checked);
        if(!checked) {
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_empty_star));
        }else{
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_black_star));
        }

        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int clickedPosition = pos;
                if (isChecked) {
                    buttonView.setBackgroundDrawable(ContextCompat.getDrawable(holderFinal.itemView.getContext(), R.drawable.ic_black_star));
                    addNew(newsList[clickedPosition].title, newsList[clickedPosition].author, newsList[clickedPosition].url);
                    // Notify FavoritesFragment the data has changed
                }else {
                    buttonView.setBackgroundDrawable(ContextCompat.getDrawable(holderFinal.itemView.getContext(), R.drawable.ic_empty_star));
                    deleteNew(newsList[clickedPosition].url);
                }
                // Notify NewsFragment the data has changed
                onClickListener.dataBaseChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(newsList == null) return 0;
        return newsList.length;
    }

    private long addNew(String title, String author, String url) {
        ContentValues cv = new ContentValues();
        cv.put(NewsContract.NewsEntry.COLUMN_TITLE, title);
        cv.put(NewsContract.NewsEntry.COLUMN_AUTHOR, author);
        cv.put(NewsContract.NewsEntry.COLUMN_URL, url);
        return mDb.insert(NewsContract.NewsEntry.TABLE_NAME, null, cv);
    }

    private long deleteNew(String url) {
        String whereClause = NewsContract.NewsEntry.COLUMN_URL + "=?";
        return mDb.delete(NewsContract.NewsEntry.TABLE_NAME, whereClause, new String[]{url});
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
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(newsList[clickedPosition].url);
        }
    }

    public void setNewsList(New[] news) {
        newsList = news;
        setIsFavNews();
        notifyDataSetChanged();
    }
}
