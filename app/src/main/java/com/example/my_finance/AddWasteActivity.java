package com.example.my_finance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddWasteActivity extends AppCompatActivity {
    private  long curDate=System.currentTimeMillis();
    private static class SaveWasteTask extends AsyncTask<Void,Void,Void> {
         private AddWasteActivity activity;
        private  Waste waste;

        public SaveWasteTask(AddWasteActivity activity, Waste waste) {
            this.activity = activity;
            this.waste = waste;
        }


        @Override
        protected void onPostExecute(Void unused) {
            activity.finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           Datab db = new Datab(activity);
           db.add(waste.getDate(),waste.getName(),waste.getSum());
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waste);
         Button btn_save = (Button) findViewById(R.id.Waste_SAve_Btn);
         EditText ed_name = (EditText)findViewById(R.id.Waste_name_ED);
         EditText ed_sum = (EditText)findViewById(R.id.Waste_sum_ED);
         CalendarView  calendar = (CalendarView)(findViewById(R.id.Waste_Calendar));
         calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
             @Override
             public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 Calendar c =Calendar.getInstance();
                 c.set(Calendar.YEAR,year);
                 c.set(Calendar.MONTH,month);
                 c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                 curDate = c.getTime().getTime();



             }
         });

        btn_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 int sum = Integer.parseInt(ed_sum.getText().toString());
                 Waste waste = new Waste(0,curDate,ed_name.getText().toString(),sum);
                 SaveWasteTask st = new SaveWasteTask(AddWasteActivity.this,waste);
                 st.execute();
             }
         });
    }}
