package moreland.spring.sample.mongodemo.internal;

import java.util.Arrays;

public class Guard {
    
    public class Against {
        public static <T> void argumentNull(T value, String name) { // Object would be fine, I justed wanted to use a generic somewhere
            if (value == null) {
                throw new IllegalArgumentException("%s cannot be null".formatted(name));
            }
        }
        public static void argumentNullOrEmpty(String value, String name) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("%s cannot be null".formatted(name));
            }
        }
        public static void argumentNullOrBlank(String value, String name) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("%s cannot be null".formatted(name));
            }
        }
        public static void argumentNegative(int value, String name) {
            if (value < 0) {
                throw new IllegalArgumentException("%s cannot be less than zero".formatted(name));
            }
        }
        public static void argumentNegative(long value, String name) {
            if (value < 0) {
                throw new IllegalArgumentException("%s cannot be less than zero".formatted(name));
            }
        }

        public static void argumentContainsNullItem(String name, Object... source) {
            if (Arrays.stream(source).anyMatch(i -> i == null)) {
               throw new IllegalArgumentException("%s contains at least one null element".formatted(name)); 
            }
        }
    }
}
