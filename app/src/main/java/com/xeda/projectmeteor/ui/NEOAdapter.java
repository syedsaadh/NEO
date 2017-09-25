package com.xeda.projectmeteor.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xeda.projectmeteor.R;
import com.xeda.projectmeteor.models.MeteorObjects;
import com.xeda.projectmeteor.models.NearEarthObjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xeda on 25-09-2017.
 */

public class NEOAdapter extends RecyclerView.Adapter<NEOAdapter.ViewHolder> {

    private static List<MeteorObjects> mDataSet;
    private static Context mContext;

    public NEOAdapter(Context context, List<MeteorObjects> mDataSet) {
        this.mContext = context;
        this.mDataSet = mDataSet;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.info_meteor_name)
        TextView meteorName;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, meteorName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.neo_card, null);
        ViewHolder vh = new ViewHolder(view);
        return  vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MeteorObjects neo = mDataSet.get(position);
        holder.meteorName.setText(neo.getName());
    }

    @Override
    public int getItemCount() {
        return (null != mDataSet ? mDataSet.size() : 0);
    }
}
