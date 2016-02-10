package ru.smirnov.dmitrii.mynewsproject;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {

    private List<News> items;
    private SparseBooleanArray selectedItems;

    RecyclerViewAdapter(List<News> newsData) {
        if (newsData == null) {
            throw new IllegalArgumentException("newsData MUST NOT be null");
        }
        items = newsData;
        selectedItems = new SparseBooleanArray();
    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        News news = items.get(position);
        Picasso.with(holder.itemView.getContext()).load(news.imgurl).into(holder.image);
        holder.head.setText(news.header);
        holder.article.setText(news.article);
        holder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static final class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView head, article;
        ImageView image;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.itemImage);
            head = (TextView) itemView.findViewById(R.id.itemHeader);
            article = (TextView) itemView.findViewById(R.id.itemBody);
        }
    }

}
