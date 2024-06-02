package com.example.my_finance;

import android.text.Editable;

public class Waste {
    long id;
    long date;
    String name;
    int sum;

    public long getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    public Waste(long id, long date, String name, int sum) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.sum = sum;
    }
}
