package com.example.my_finance;

public class FIN {
    private int id;
    private String Month;
    private int Profit;
    private int Waste;

    public int getId() {
        return id;
    }

    public String getMonth() {
        return Month;
    }

    public int getProfit() {
        return Profit;
    }

    public int getWaste() {
        return Waste;
    }

    public FIN (int id, String Month, int Profit, int Waste){
        this.id=id;
        this.Month=Month;
        this.Profit=Profit;
        this.Waste=Waste;

    }
}
