package runner;

import model.CsvTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CsvUtils;
import validator.CsvCommon;
import validator.CsvValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;


public class CsvRunner {
    private static final Logger logger = LogManager.getLogger(CsvRunner.class);

    public static void main(String[] args) {
        String filePath = "src/test/resources/BPS001_C_0009_201810_EN.csv";

        // read csv file
        List<CsvTransaction> allRows;
        try {
            allRows = CsvUtils.readCsvFile(filePath);
        } catch (RuntimeException e) {
            logger.error("Failed to read or parse CSV file: {}", e.getMessage(), e);
            return;
        }

        // read all lines from csv
        List<String> CsvLines;
        try {
            CsvLines = Files.readAllLines(Paths.get(filePath));
            if (CsvLines.isEmpty()) {
                logger.error("CSV file is empty!");
                return;
            }
        } catch (IOException e) {
            logger.error("Failed to read the CSV file: {}", e.getMessage());
            e.printStackTrace();
            return;
        }

        String headerLine = CsvLines.getFirst();
        Set<String> expectedHeaders = Set.of(
                "Year",
                "Month",
                "Currency",
                "Originating participant code",
                "Originating participant name",
                "Destination participant code",
                "Destination participant name",
                "Transaction type code",
                "Transaction type description",
                "Number of submitted transactions",
                "Amount of submitted transactions",
                "Number of received transactions",
                "Amount of received transactions"
        );
        CsvCommon.validateMandatoryHeaders(expectedHeaders, headerLine);

        CsvValidator.validateRowsFromCurrentMonthAndYear(allRows);

        CsvValidator.validateCurrency(allRows);
    }
}
