package com.xeda.projectmeteor.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.xeda.projectmeteor.models.NEO;
import com.xeda.projectmeteor.repository.DataRepository;

/**
 * Created by Xeda on 25-09-2017.
 */

public class LiveDataNeoViewModel extends AndroidViewModel {

    private final LiveData<NEO> mNeo;

    public LiveDataNeoViewModel(Application application) {
        super(application);
        this.mNeo = DataRepository.getInstance().getNeo("2015-09-07", "2015-09-07");
    }

    public LiveData<NEO> getNeoObservable(){
        return mNeo;
    }
}
