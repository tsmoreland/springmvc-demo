package ca.tsmoreland.datafundementals.infrastructure.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoTest {

    @Test
    void ConstructorShouldThrowIllegalArgumentExceptionWhenConnectionProviderIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new BookDao(null));
    }

}