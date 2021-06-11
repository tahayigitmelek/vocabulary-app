package com.tahayigitmelek.vocabularyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.tahayigitmelek.vocabularyapp.dao.Dao;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseCopyHelper;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseHelper;
import com.tahayigitmelek.vocabularyapp.fragments.KnownFragment;
import com.tahayigitmelek.vocabularyapp.fragments.FavFragment;
import com.tahayigitmelek.vocabularyapp.fragments.MainFragment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int numberOfKnownWords, numberOfUnknownWords, favoriteWordCount;
    public Fragment knownFragment, favFragment, mainFragment;
    private final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        this.favFragment = new FavFragment();
        this.mainFragment = new MainFragment();
        this.knownFragment = new KnownFragment();

        DatabaseCopyHelper databaseCopyHelper = new DatabaseCopyHelper(this);

        databaseCopyHelper.createDataBase();
        databaseCopyHelper.openDataBase();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, this.mainFragment).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab tab) {
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainActivity.this.mainFragment).commit();
                }
                if (tab.getPosition() == 1) {
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainActivity.this.knownFragment).commit();
                }
                if (tab.getPosition() == 2) {
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MainActivity.this.favFragment).commit();
                }
            }
        });
        this.numberOfUnknownWords = new Dao().unknownStat(this.databaseHelper);
        this.numberOfKnownWords = new Dao().knownStat(this.databaseHelper);
        this.favoriteWordCount = new Dao().favStat(this.databaseHelper);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> stringList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager, int i) {
            super(fragmentManager, i);
        }

        public void addFragment(Fragment fragment, String str) {
            this.fragmentList.add(fragment);
            this.stringList.add(str);
        }

        @NonNull
        public Fragment getItem(int i) {
            return this.fragmentList.get(i);
        }

        public int getCount() {
            return this.fragmentList.size();
        }

        public CharSequence getPageTitle(int i) {
            return this.stringList.get(i);
        }
    }
}
