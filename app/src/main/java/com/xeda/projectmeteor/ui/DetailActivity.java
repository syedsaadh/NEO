package com.xeda.projectmeteor.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xeda.projectmeteor.R;
import com.xeda.projectmeteor.models.MeteorObjects;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    private static String meteorGsonStr;
    private static MeteorObjects meteorObjects;

    public static final String EXTRA = "EXTRA";

    @BindView(R.id.meteor_name) TextView meteorName;
    @BindView(R.id.absolute_diameter) TextView absoluteDiameter;
    @BindView(R.id.absolute_magnitude) TextView absoluteMagnitude;
    @BindView(R.id.close_approach_date) TextView closeApproachDate;
    @BindView(R.id.approaching_body) TextView approachingBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        Intent i = this.getIntent();
        meteorGsonStr = i.getStringExtra(EXTRA);

        Gson gson = new Gson();
        if(meteorGsonStr != null){
            meteorObjects = gson.fromJson(meteorGsonStr, new TypeToken<MeteorObjects>(){}.getType());
            meteorName.setText(meteorObjects.getName());
            absoluteMagnitude.setText(String.format("%.4f", meteorObjects.getAbsoluteMagnitudeH()));
            absoluteDiameter.setText(
                    String.format("%.4f", meteorObjects.getEstimatedDiameter().getMeters().getEstimatedDiameterMax())
            );
            closeApproachDate.setText(meteorObjects.getCloseApproachData().get(0).getCloseApproachDate());
            approachingBody.setText(meteorObjects.getCloseApproachData().get(0).getOrbitingBody());
        }
        else {
            Timber.d("Error in Meteor String");
        }
    }
}
