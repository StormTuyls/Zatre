package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mysql.cj.exceptions.NumberOutOfRange;

import domein.Vak;

class VakTest {

	@BeforeEach
	public void setUp() throws Exception {
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 3 })
	public void vakMetFouteKleur_geeftException(int waarde) {

		Assertions.assertThrows(NumberOutOfRange.class, () -> {
			new Vak(waarde);
		});
	}
	@ParameterizedTest
	@ValueSource(ints = {0,1,2})
	public void vakMetJuisteKleur_wordtGeregistreerd(int waarde) {
		Vak vak = new Vak(waarde);
		Assertions.assertEquals(waarde, vak.getKleur());
	}

}
