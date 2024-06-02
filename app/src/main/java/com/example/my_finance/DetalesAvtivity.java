package com.example.my_finance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DetalesAvtivity extends AppCompatActivity {
    public static final String START_NAME= "Start";
    public static final String STOP_NAME= "Stop";
    private WasteListAdapter adapter;
    private static class DetaleTask extends AsyncTask <Void,Void, List<Waste>>{
        public DetaleTask(DetalesAvtivity activity,long start,long stop) {
            this.activity = activity;
            this.start =start;
            this.stop = stop;

        }

        public long getStart() {
            return start;
        }

        public long getStop() {
            return stop;
        }

        private long start;
        private long stop;
        private DetalesAvtivity activity;

        @Override
        protected void onPostExecute(List<Waste> listw) {
            activity.adapter.refresh(listw);
        }


        @Override
        protected List<Waste> doInBackground(Void... voids) {
            Datab d = new Datab(activity);
            return d.diapasone ( start, stop);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detales_avtivity);
        ListView lv = findViewById(R.id.Waste_detale_lv);
        adapter = new WasteListAdapter(this);
        lv.setAdapter(adapter);
        long start = getIntent().getLongExtra(START_NAME,0);
        long stop = getIntent().getLongExtra(STOP_NAME,0);
        DetaleTask dt = new DetaleTask(this,start,stop);
        dt.execute();



    }
}