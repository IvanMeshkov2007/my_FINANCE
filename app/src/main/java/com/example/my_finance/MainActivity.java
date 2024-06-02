package com.example.my_finance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView this_month_start_tv;
    private TextView this_month_stop_tv;
    private TextView prev_month_start_tv;
    private TextView prev_month_stop_tv;
    private TextView last_month_start_tv;
    private TextView last_month_stop_tv;
    private TextView this_month_sum_numb;
    private TextView prev_month_sum_numb;
    private TextView last_month_sum_numb;
    private Button this_month_btn;
    private Button prev_month_btn;
    private Button last_month_btn;
    private Button addWasteBtn;
    private long thisStart,prevStart,lastStart;
    private long thisStop,prevStop,lastStop;
    private static class SumSetTask extends AsyncTask<Void,Void,Integer>{
        Context cn;
        long start;
        long stop;
        TextView tv;

        public SumSetTask(Context cn, long start, long stop, TextView tv) {
            this.cn = cn;
            this.start = start;
            this.stop = stop;
            this.tv = tv;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Datab db = new Datab(cn);
            List<Waste> wasteList;

            wasteList =db.diapasone(start,stop);
            int sum=0;
            for (int i=0;i<wasteList.size();i++){
                sum=sum+wasteList.get(i).sum;

            }
            return sum;
        }

        @Override
        protected void onPostExecute(Integer sum) {
            tv.setText(sum+" р.");


        }
    }
    private void calculate(){
        Calendar c = Calendar.getInstance();
        thisStop = c.getTime().getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        thisStart=c.getTime().getTime();
        c.add(Calendar.DAY_OF_MONTH,-1);
        prevStop=c.getTime().getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        prevStart= c.getTime().getTime();
        c = Calendar.getInstance();
        lastStop = c.getTime().getTime();
        c.add(Calendar.MONTH,-1);
        lastStart=c.getTime().getTime();
    }
    private void showDate(){
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        this_month_start_tv.setText( sd.format(new Date(thisStart)));
        this_month_stop_tv.setText(sd.format(new Date((thisStop))));
        prev_month_start_tv.setText( sd.format(new Date(prevStart)));
        prev_month_stop_tv.setText(sd.format(new Date((prevStop))));
        last_month_start_tv.setText( sd.format(new Date(lastStart)));
        last_month_stop_tv.setText(sd.format(new Date((lastStop))));
        SumSetTask sthis = new SumSetTask(this,thisStart,thisStop,this_month_sum_numb);
        sthis.execute();
        SumSetTask sprev = new SumSetTask(this,prevStart,prevStop,prev_month_sum_numb);
        sprev.execute();
        SumSetTask slast = new SumSetTask(this,lastStart,lastStop,last_month_sum_numb);
        slast.execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this_month_btn= findViewById(R.id.This_month_btn);
        prev_month_btn= findViewById(R.id.prev_month_btn);
        last_month_btn= findViewById(R.id.last_month_btn);
        addWasteBtn= findViewById(R.id.add_waste_btn);
        this_month_start_tv= findViewById(R.id.This_month_start_tv);
        prev_month_start_tv= findViewById(R.id.prev_month_start_tv);
        last_month_start_tv= findViewById(R.id.Last_month_start_tv);
        this_month_stop_tv=findViewById(R.id.This_month_stop_tv);
        prev_month_stop_tv=findViewById(R.id.prev_month_stop_tv);
        last_month_stop_tv=findViewById(R.id.Last_month_stop_tv);
        this_month_sum_numb=findViewById(R.id.This_month_sun_numb);
        last_month_sum_numb=findViewById(R.id.Last_month_sun_numb);
        prev_month_sum_numb=findViewById(R.id.prev_month_sun_numb);

        this_month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetalesAvtivity.class);
                intent.putExtra(DetalesAvtivity.STOP_NAME,thisStop);
                intent.putExtra(DetalesAvtivity.START_NAME,thisStart);
                startActivity(intent);


            }
        });
        prev_month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetalesAvtivity.class);
                intent.putExtra(DetalesAvtivity.STOP_NAME,prevStop);
                intent.putExtra(DetalesAvtivity.START_NAME,prevStart);
                startActivity(intent);

            }
        });
        last_month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetalesAvtivity.class);
                intent.putExtra(DetalesAvtivity.STOP_NAME,lastStop);
                intent.putExtra(DetalesAvtivity.START_NAME,lastStart);
                startActivity(intent);

            }
        });
        addWasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddWasteActivity.class);
                startActivity(intent);
            }
        });






        




    }
    @Override
    protected void onResume() {
        super.onResume();
        calculate();
        showDate();
    }
}