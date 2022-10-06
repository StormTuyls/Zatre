package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;

class SpelerTest {

	@Test
	public void controleerNaam_naamOK_registreertSpeler() {
		Speler s1 = new Speler("tomeke", 2015);
		Assertions.assertEquals("tomeke", s1.getNaam());
	}

	@Test
	public void controleerSpeelKansen_SpeelKansen3_registreertSpeler() {
		Speler s1 = new Speler("tomeke", 2015, 3);
		Assertions.assertEquals(3, s1.getAantalSpeelKansen());
	}

	@Test
	public void controleerDatum_datumOK_registreertSpeler() {
		Speler s1 = new Speler("tomeke", 2015);
		Assertions.assertEquals(2015, s1.getGeboortejaar());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "\n", "123", "1234" })
	public void ControleerNaam_LeegOfTekort_exception(String naam) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(naam, 2002));
	}

	@ParameterizedTest
	@ValueSource(ints = { 2021, 2018, 2019 })
	public void ControleerGeboortejaar_teJong_exception(int geboortejaar) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler("Thomas", geboortejaar));
	}

	@ParameterizedTest
	@ValueSource(ints = { 6, 13, 1500, -1, -105 })
	public void ControleerAantalSpeelKansen_kansenNOK_exception(int aantalSpeelKansen) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler("Thomas", 2015, aantalSpeelKansen));
	}

}
