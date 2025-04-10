package model;

import com.opencsv.bean.CsvBindByName;

public class CsvTransaction {

    @CsvBindByName(column = "Year")
    private int year;

    @CsvBindByName(column = "Month")
    private int month;

    @CsvBindByName(column = "Currency")
    private String currency;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "year=" + year +
                ", month=" + month +
                ", currency='" + currency + '\'' +
                '}';
    }
}
