package validator;

import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvCommon {

    public static void validateMandatoryHeaders(Set<String> expectedHeaders, String headerLine) {
        Set<String> actualHeaders = Arrays.stream(headerLine.replace("\ufeff", "").split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        Set<String> missingHeader = new HashSet<>(expectedHeaders);
        missingHeader.removeAll(actualHeaders);
        assertTrue(missingHeader.isEmpty(), "The mandatory header is missing: " + missingHeader);

        LogManager.getLogger().info("All mandatory headers are present.");
    }
}
