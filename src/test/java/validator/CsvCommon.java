package validator;

import org.apache.logging.log4j.LogManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvCommon {

    public static void validateMandatoryHeaders(Set<String> actualHeaders) {
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
        Set<String> missingHeader = new HashSet<>(expectedHeaders);
        missingHeader.removeAll(actualHeaders);

        assertTrue(missingHeader.isEmpty(), "The mandatory header is missing: " + missingHeader);
        LogManager.getLogger().info("All mandatory headers are present.");
    }
}
