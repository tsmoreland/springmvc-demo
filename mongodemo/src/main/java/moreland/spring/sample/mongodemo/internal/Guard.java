package moreland.spring.sample.mongodemo.internal;

public class Guard {
    
    public class Against {
        public static void ArgumentNull(Object value, String name) {
            if (value == null) {
                throw new IllegalArgumentException("%s cannot be null".formatted(name));
            }
        }
        public static void ArgumentNullOrEmpty(String value, String name) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("%s cannot be null".formatted(name));
            }
        }
        public static void ArgumentNullOrBlank(String value, String name) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("%s cannot be null".formatted(name));
            }
        }
        public static void ArgumentNegative(int value, String name) {
            if (value < 0) {
                throw new IllegalArgumentException("%s cannot be less than zero".formatted(name));
            }
        }
        public static void ArgumentNegative(long value, String name) {
            if (value < 0) {
                throw new IllegalArgumentException("%s cannot be less than zero".formatted(name));
            }
        }
    }
}
