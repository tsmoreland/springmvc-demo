package tsmoreland.objecttracker.core.models;

import java.util.Optional;

public class ObjectModel {
    
    private final Optional<Long> id;
    private String name;
    private int progress;

    public ObjectModel(String name, int progress) {
        this(Optional.empty(), name, progress);
    }
    public ObjectModel(long id, String name, int progress) {
        this(Optional.of(Long.valueOf(id)), name, progress);

    }
    public ObjectModel(Optional<Long> id, String name, int progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("progress must be between 0 and 100");
        }

        this.id = id;
        this.name = name;
        this.progress = progress;
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


}
