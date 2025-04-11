package model;

import java.util.List;
import java.util.Set;

public class CsvResult {
    private final Set<String> headerSet;
    private final List<CsvTransaction> records;

    public CsvResult(Set<String> headerSet, List<CsvTransaction> records) {
        this.headerSet = headerSet;
        this.records = records;
    }

    public Set<String> getHeaderSet() {
        return headerSet;
    }

    public List<CsvTransaction> getRecords() {
        return records;
    }
}
