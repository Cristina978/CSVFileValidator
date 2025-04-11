package runner;

import model.CsvResult;
import model.CsvTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CsvUtils;
import validator.CsvCommon;
import validator.CsvValidator;

import java.util.ArrayList;
import java.util.List;


public class CsvRunner {
    private static final Logger logger = LogManager.getLogger(CsvRunner.class);

    public static void main(String[] args) {
        String filePath = "src/test/resources/BPS001_C_0009_201810_EN.csv";

        List<String> validationErrors = new ArrayList<>();

        CsvResult result = CsvUtils.readCsvFile(filePath);

        CsvCommon.validateMandatoryHeaders(result.getHeaderSet());
        List<CsvTransaction> allRows = result.getRecords();
        if (allRows.isEmpty()) {
            logger.warn("CSV file has header but no data rows.");
        }

        CsvValidator.validateRowsFromCurrentMonthAndYear(allRows, validationErrors);

        CsvValidator.validateCurrency(allRows, validationErrors);

        if (!validationErrors.isEmpty()) {
            logger.error("CSV File validation failed with the following errors:");
            validationErrors.forEach(logger::error);
            throw new AssertionError("CSV validation failed with the following details.");
        } else {
            logger.info("All validations passed successfully.");
        }
    }
}