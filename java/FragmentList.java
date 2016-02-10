package ru.smirnov.dmitrii.mynewsproject;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentList extends Fragment {
    final String LOG_TAG = "FRAGMENT LIST SAYS";
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private static List<News> fillData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        fillData = new ArrayList<News>();

        new getDataTask().execute();

        return v;
    }


    class getDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String text = null;

            getData exsample = new getData();
            String url = "https://docs.google.com/uc?authuser=0&id=0BxyiZJDI1SglU013U08ydXkzSm8&export=download";
            try {
                text = exsample.run(url);
                parseJson(text);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(LOG_TAG, s);
            adapter = new RecyclerViewAdapter(fillData);
            recyclerView.setAdapter(adapter);

            recyclerView.addOnItemTouchListener(new RecyclerClickListener(recyclerView.getContext(), new RecyclerClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    Log.d(LOG_TAG, "CLICKED #" + position);
                    goNews(position);
                }
            }));

        }
    }

    public class getData {
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }


    private void parseJson(String jsonStr) {
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray newsJ = jsonObj.getJSONArray("news");
                for (int i = 0; i < newsJ.length(); i++) {
                    JSONObject c = newsJ.getJSONObject(i);
                    News news = new News();
                    news.id = c.getString("id");
                    news.imgurl = c.getString("image_url");
                    news.header = c.getString("title");
                    news.article = c.getString("description");
                    fillData.add(news);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    void goNews(int pos) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(recyclerView.getContext());
        String head = String.valueOf(fillData.get(pos).header);
        String body = String.valueOf(fillData.get(pos).article);
        String img = fillData.get(pos).imgurl;

        if (!pref.getBoolean("DUAL", false)) {

//            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("head", head);
            editor.putString("body", body);
            editor.putString("img", img);
            editor.apply();

            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
            FragmentArticle fragmentArticle = new FragmentArticle();
            fragTrans.addToBackStack(null);
            fragTrans.replace(R.id.fragArticleContainer, fragmentArticle);
            fragTrans.commit();




        } else {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.fragArticleContainer);
            ((TextView) fragment.getView().findViewById(R.id.articleHeader)).setText(head);
            ((TextView) fragment.getView().findViewById(R.id.articleBody)).setText(body);
            ImageView imageView = (ImageView) fragment.getView().findViewById(R.id.articleImage);
            Picasso.with(fragment.getView().getContext()).load(img).into(imageView);
        }
    }


}
