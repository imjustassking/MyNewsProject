package ru.smirnov.dmitrii.mynewsproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    final String LOG_TAG = "MAIN ACTIVITY SAYS";
//    public boolean DUAL;
//    Fragment fragmentList, fragmentArcticle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View tmp = findViewById(R.id.fragList);
        boolean DUAL = tmp != null && tmp.getVisibility() == View.VISIBLE;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("DUAL", DUAL);
        editor.apply();


        Toast.makeText(MainActivity.this, "DUAL = " + DUAL, Toast.LENGTH_SHORT).show();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (DUAL) {
            Fragment fragmentArcticle = new FragmentArticle();
            fragmentTransaction.add(R.id.fragArticleContainer, fragmentArcticle);
        } else {
            Fragment fragmentList = new FragmentList();
            fragmentTransaction.add(R.id.fragArticleContainer, fragmentList);
        }
        fragmentTransaction.commit();

    }

}
