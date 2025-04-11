package utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import model.CsvResult;
import model.CsvTransaction;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class CsvUtils {

    public static CsvResult readCsvFile(String filePath) {
        try (
                InputStream fis = new FileInputStream(filePath);
                BOMInputStream bis = new BOMInputStream(fis);
                InputStreamReader reader = new InputStreamReader(bis, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)
        ) {

            String headerLine = bufferedReader.readLine();
            if (headerLine == null || headerLine.isBlank()) {
                throw new RuntimeException("CSV file is empty or header is missing.");
            }

            Set<String> headerSet = Arrays.stream(headerLine.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());


            CsvToBean<CsvTransaction> csvToBean = new CsvToBeanBuilder<CsvTransaction>(bufferedReader)
                    .withType(CsvTransaction.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CsvTransaction> records = csvToBean.parse();

            return new CsvResult(headerSet, records);

        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
    }
}






