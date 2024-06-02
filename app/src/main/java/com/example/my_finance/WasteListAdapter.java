package com.example.my_finance;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WasteListAdapter extends BaseAdapter {
    private Context cn;
    private LayoutInflater lInflater;
    private List<Waste> waste= new ArrayList<>();
    private static class DeleteWasteTask extends AsyncTask<Void,Void,Void> {
        WasteListAdapter adp;
        Waste w;

        public DeleteWasteTask(WasteListAdapter adp, Waste w) {
            this.adp = adp;
            this.w = w;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Datab d = new Datab(adp.cn);
            d.delete(w.id);
            return null;
        }

        @Override
        protected void onPreExecute() {
            adp.waste.remove(w);
            adp.notifyDataSetChanged();


        }
    }

    public WasteListAdapter(Context cn) {
        this.cn = cn;
        lInflater = LayoutInflater.from(cn);

    }
    public void refresh(List<Waste> d){
        waste.clear();
        waste.addAll(d);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return waste.size();
    }

    @Override
    public Object getItem(int i) {
        return waste.get(i);
    }

    @Override
    public long getItemId(int i) {
        return waste.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view==null){
            view = lInflater.inflate(R.layout.item, null);
        }
        Waste w = waste.get(i);
        ((TextView) view.findViewById(R.id.Waste_item_date)).setText( DateFormat.getDateInstance().format(new Date(w.date)));
        ((TextView) view.findViewById(R.id.Waste_item_name)).setText(w.name);
        ((TextView) view.findViewById(R.id.Waste_item_sum)).setText(w.sum+" р.");
        ((ImageButton)view.findViewById(R.id.Waste_delete_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteWasteTask dwt = new DeleteWasteTask(WasteListAdapter.this,w);
                dwt.execute();

            }
        });

        return view;
    }


}
