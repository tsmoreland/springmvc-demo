package tsmoreland.objecttracker.core.models;

public enum Severity {

    LOW(0),
    MEDIUM(1),
    HIGH(2);
    
    private int value;

    private Severity(int value) {
        this.value = value;
    }

    public int asInt() {
        return value;
    }

    public Integer asInteger() {
        return Integer.valueOf(value);
    }

    /**
     * converts {@code value} to {@code Severity} if {@code value}
     * is a valid value in the enum, otherwise {@code Severity.LOW}
     * @param value integer value to convert
     * @return converted {@code Severity} or {@code Severity.LOW} if
     *         {@code value} is invalid
     */
    public static Severity FromInteger(Integer value) {
        return switch (value.intValue()) {
            case 0 -> Severity.LOW;
            case 1 -> Severity.MEDIUM;
            case 2 -> Severity.HIGH;
            default -> Severity.LOW;
        };

    }
}
