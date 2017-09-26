package com.xeda.projectmeteor.viewmodel;

import android.app.Application;
import android.app.DialogFragment;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.xeda.projectmeteor.MainActivity;
import com.xeda.projectmeteor.models.MeteorObjects;
import com.xeda.projectmeteor.models.NEO;
import com.xeda.projectmeteor.repository.DataRepository;
import com.xeda.projectmeteor.ui.DatePickerFragment;
import com.xeda.projectmeteor.utils.DateConverter;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Xeda on 25-09-2017.
 */

public class NeoListViewModel extends ViewModel {

    private DataRepository dataRepository;
    private static LiveData<List<MeteorObjects>> mNeo;

    public NeoListViewModel() {
        dataRepository = DataRepository.getInstance();
        String today = DateConverter.getDateFromTimeStamp(DateConverter.getCurrentTime(), "yyyy-MM-dd");
        this.mNeo = dataRepository.getNeo();
        dataRepository.loadNew(today, today);
    }

    public void changeDate(Date date) {
        Long timestamp = DateConverter.toTimestamp(date);
        String dateString = DateConverter.getDateFromTimeStamp(timestamp, "yyyy-MM-dd");
        dataRepository.emptyData();
        dataRepository.loadNew(dateString, dateString);
    }
    public LiveData<List<MeteorObjects>> getNeoObservable(){
        return mNeo;
    }
}
