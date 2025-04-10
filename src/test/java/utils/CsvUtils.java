package utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import model.CsvTransaction;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class CsvUtils {

    public static List<CsvTransaction> readCsvFile(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<CsvTransaction> csvToBean = new CsvToBeanBuilder<CsvTransaction>(reader)
                    .withType(CsvTransaction.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
    }
}






