package tsmoreland.objecttracker.core.models;

import java.util.List;
import java.util.Optional;

public class ObjectModel {
    
    private final Optional<Long> id;
    private String name;
    private int progress;
    private List<LogEntry> logEntries;

    public ObjectModel(String name, int progress) {
        this(Optional.empty(), name, progress, List.of());
    }
    public ObjectModel(long id, String name, int progress, List<LogEntry> logEntries) {
        this(Optional.of(Long.valueOf(id)), name, progress, logEntries);

    }
    public ObjectModel(Optional<Long> id, String name, int progress, List<LogEntry> logEntries) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("progress must be between 0 and 100");
        }

        this.id = id;
        this.name = name;
        this.progress = progress;
        this.logEntries = logEntries;
    }

    public Optional<Long> getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("progress must be between 0 and 100");
        }
        this.progress = progress;
    }

    public Iterable<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void addLogEntry(LogEntry lgoEntry) {
        logEntries.add(lgoEntry);
    }
}
