package validator;

import model.CsvTransaction;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public class CsvValidator extends CsvCommon {

    public static void validateRowsFromCurrentMonthAndYear(List<CsvTransaction> allRows, List<String> errors) {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        List<CsvTransaction> invalidDates = allRows.stream()
                .filter(row -> row.getYear() != currentYear || row.getMonth() != currentMonth)
                .toList();

        if (!invalidDates.isEmpty()) {
            LogManager.getLogger().error("Invalid dates found. Only current month/year dates are allowed:");
            invalidDates.forEach(row -> LogManager.getLogger().error("Invalid dates: {}", row));
            errors.add("Some rows are not from the current month/year: " + currentMonth + "/" + currentYear);
        } else {
            LogManager.getLogger().info("All rows are from current month and year: {}/{}", currentMonth, currentYear);
        }
    }


    public static void validateCurrency(List<CsvTransaction> allRows, List<String> errors) {
        Set<String> allowedCurrencies = Set.of("USD", "PEN");

        List<CsvTransaction> invalidCurrency = allRows.stream()
                .filter(row -> !allowedCurrencies.contains(row.getCurrency()))
                .toList();

        if (!invalidCurrency.isEmpty()) {
            LogManager.getLogger().error("Invalid currency values found. Only USD and PEN are allowed:");
            invalidCurrency.forEach(row -> LogManager.getLogger().error("Invalid currency: {}", row));
            errors.add("Currency validation failed. Found: " + invalidCurrency);
        } else {
            LogManager.getLogger().info("All columns contains currencies USD or PEN.");
        }
    }
}
