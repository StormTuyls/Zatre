package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mysql.cj.exceptions.NumberOutOfRange;

import domein.Steen;

class SteenTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

    @ParameterizedTest
    @ValueSource(ints = { -5, -1, 7 })
    public void fouteWaardes_gevenException(int waarde) {

	Assertions.assertThrows(NumberOutOfRange.class, () -> {
	    new Steen(waarde);
	});
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 3, 6 })
    public void juisteWaardes_gevenTrue(int waarde) {

	Assertions.assertDoesNotThrow(() -> {
	    new Steen(waarde);
	});
    }
}
