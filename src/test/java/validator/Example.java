package validator;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;

public class Example {

    private static final Logger logger = LogManager.getLogger(Example.class);

    public static class Transaction {
        @CsvBindByName(column = "\ufeffYear")
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

    public static void main(String[] args) {
        String filePath = "src/test/resources/BPS001_C_0009_201810_EN.csv"; // adaptează path-ul dacă e cazul

        try {
            List<Transaction> transactions = new CsvToBeanBuilder<Transaction>(new FileReader(filePath))
                    .withType(Transaction.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            logger.info("===== CSV VALIDATION REPORT =====");

            if (transactions.isEmpty()) {
                logger.error("No transactions found in the CSV file.");
                return;
            } else {
                logger.info("Loaded {} transactions.", transactions.size());
            }

            // Step 1: validate date
            LocalDate now = LocalDate.now();
            boolean dateCheck = true;
            for (Transaction t : transactions) {
                if (t.getYear() != now.getYear() || t.getMonth() != now.getMonthValue()) {
                    logger.error("Invalid date: {}", t);
                    dateCheck = false;
                }
            }
            if (dateCheck) {
                logger.info("All dates are from current month and year: {} {}", now.getMonth(), now.getYear());
            }

            // Step 2: validate currency
            boolean currencyCheck = true;
            for (Transaction t : transactions) {
                String curr = t.getCurrency();
                if (!curr.equalsIgnoreCase("USD") && !curr.equalsIgnoreCase("PEN")) {
                    logger.error("Invalid currency: {} in transaction {}", curr, t);
                    currencyCheck = false;
                }
            }
            if (currencyCheck) {
                logger.info("All currencies are valid (USD or PEN).");
            }

            // Summary
            if (dateCheck && currencyCheck) {
                logger.info("🎉 CSV file passed all validations!");
            } else {
                logger.warn("⚠️ CSV file failed one or more validations.");
            }

            logger.info("==================================");

        } catch (Exception e) {
            logger.error("Exception during CSV validation: ", e);
        }
    }
}