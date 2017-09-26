package com.xeda.projectmeteor;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xeda.projectmeteor.models.MeteorObjects;
import com.xeda.projectmeteor.models.NEO;
import com.xeda.projectmeteor.ui.DatePickerFragment;
import com.xeda.projectmeteor.ui.NEOAdapter;
import com.xeda.projectmeteor.viewmodel.NeoListViewModel;


import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends LifecycleActivity implements DatePickerFragment.DatePickerFragmentListener{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_dialog)
    ProgressBar progressBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private NEOAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public final static String API_KEY = BuildConfig.API_KEY;

    private NeoListViewModel mLiveDataNeoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please obtain your API KEY, Place in root folder in file keystore.properties",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Timber.plant(new Timber.DebugTree());
        initViews();
        initViewModels();

    }

    private void initViews() {
        ButterKnife.bind(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setPadding(0, 300, 0, 150);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NEOAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

    }
    private void onFabClick() {
        DialogFragment newFragment = DatePickerFragment.newInstance(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(Date date) {
        showProgress();
        mLiveDataNeoViewModel.changeDate(date);
    }

    private void initViewModels() {
        mLiveDataNeoViewModel = ViewModelProviders.of(this).get(NeoListViewModel.class);
        subscribe();
    }

    private void subscribe() {
        mLiveDataNeoViewModel.getNeoObservable().observe(this, new Observer<List<MeteorObjects>>() {
            @Override
            public void onChanged(@Nullable List<MeteorObjects> meteorObjectses) {
                if(meteorObjectses != null && meteorObjectses.size() != 0) {
                    hideProgress();
                    showNeoListInUi(meteorObjectses);
                }else  {
                    showProgress();
                }
            }
        });
    }

    private void showNeoListInUi(List<MeteorObjects> items) {
        mAdapter.setItems(items);
    }

    private void showProgress() {
        if(!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    private void hideProgress() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
