package com.xeda.projectmeteor;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.xeda.projectmeteor.api.ApiClient;
import com.xeda.projectmeteor.api.NEOService;
import com.xeda.projectmeteor.models.MeteorObjects;
import com.xeda.projectmeteor.models.NEO;
import com.xeda.projectmeteor.ui.NEOAdapter;
import com.xeda.projectmeteor.viewmodel.LiveDataNeoViewModel;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView mRecyclerView;
    private NEOAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<MeteorObjects> list = new ArrayList<MeteorObjects>();

    public final static String API_KEY = BuildConfig.API_KEY;
    private static final String TAG = MainActivity.class.getSimpleName();

    private LiveDataNeoViewModel mLiveDataNeoViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    return true;
                case R.id.navigation_explore:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY, Place in root folder in file keystore.properties", Toast.LENGTH_LONG).show();
            return;
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NEOAdapter(getApplicationContext(), list);
        mRecyclerView.setAdapter(mAdapter);
//
//        NEOService neoService = ApiClient.getClient().create(NEOService.class);
//        observable = neoService.getFeeds("2015-09-07","2015-09-07",API_KEY);
//        observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NEO>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull NEO neo) {
//                        List<MeteorObjects> result = neo.getNearEarthObjects().get(0).getMeteorObjects();
//                        for(MeteorObjects mo : result){
//                            list.add(mo);
//                        }
//                        Log.d(TAG, "Count of Near Earth Objects " + list.size());
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
////                .subscribe( response -> {
////                    Log.d(TAG, "Count of Near Earth Objects " + response.getNearEarthObjects().get(0).getDate());
////                });

        mLiveDataNeoViewModel = ViewModelProviders.of(this).get(LiveDataNeoViewModel.class);
        subscribe();
    }

    private void subscribe() {
        final Observer<NEO> neoObserver = new Observer<NEO>() {
            @Override
            public void onChanged(@Nullable NEO neo) {
                List<MeteorObjects> results = neo.getNearEarthObjects().get(0).getMeteorObjects();
                list.addAll(results);
                Log.d(TAG, "Count of Near Earth Objects " + neo.getElementCount());
                mAdapter.notifyDataSetChanged();
            }
        };
        mLiveDataNeoViewModel.getNeoObservable().observe(this, neoObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
