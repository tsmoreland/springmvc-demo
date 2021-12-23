package tsmoreland.objecttracker.core.models;

public enum Severity {

    LOW(0),
    MEDIUM(1),
    HIGH(2);
    
    private int value;

    private Severity(int value) {
        this.value = value;
    }

    public int asInteger() {
        return value;
    }

}
