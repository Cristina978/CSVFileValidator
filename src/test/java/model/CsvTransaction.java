package model;

import com.opencsv.bean.CsvBindByPosition;

public class CsvTransaction {

    @CsvBindByPosition(position = 0)
    private int year;

    @CsvBindByPosition(position = 1)
    private int month;

    @CsvBindByPosition(position = 2)
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
