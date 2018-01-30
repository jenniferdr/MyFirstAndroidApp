package com.example.android.myfirstandroidapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.myfirstandroidapp.data.New;
import com.example.android.myfirstandroidapp.data.NewsContract;
import com.example.android.myfirstandroidapp.data.NewsDbHelper;

/**
 * Created by Jennifer on 1/30/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>{

    private New[] newsList;
    private SQLiteDatabase mDb;
    Cursor mCursor;

    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String webUrl);
    }

    public FavoritesAdapter(ListItemClickListener mOnClickListener, Context context) {
        this.onClickListener = mOnClickListener;
        NewsDbHelper dbHelper = new NewsDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        // Set newsList
        mCursor = mDb.query(
                NewsContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            );
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

        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String title = mCursor.getString(mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_TITLE));

        //New newItem = newsList[position];
        holder.titleTextView.setText(title);
    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
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
            if (!mCursor.moveToPosition(clickedPosition))
                return; // bail if returned null

            String url = mCursor.getString(mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL));
            onClickListener.onListItemClick(url);
        }
    }

    public void updateData() {
        mCursor = mDb.query(
                NewsContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        notifyDataSetChanged();
    }
}
