package ru.smirnov.dmitrii.mynewsproject;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FragmentArticle extends Fragment {
    final String LOG_TAG = "FRAGMENT ARTICLE SAYS";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, container, false);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(v.getContext());


        String head = pref.getString("head", "Select any NEWS from list");
        String body = pref.getString("body", "");
        String img = pref.getString("img", "http://ceoskillfoundations.com/media/News.jpg");

        ((TextView) v.findViewById(R.id.articleHeader)).setText(head);
        ((TextView) v.findViewById(R.id.articleBody)).setText(body);
        ImageView imageView = (ImageView) v.findViewById(R.id.articleImage);
        Picasso.with(v.getContext()).load(img).into(imageView);


        return v;
    }
}

