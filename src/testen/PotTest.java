package testen;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Pot;
import domein.Spel;
import domein.Steen;

class PotTest {

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void pot_gevuldMet121Steentjes() {
		Pot p1 = new Pot();
		assertEquals(121, p1.getPot().size());
	}

	@Test
	public void steentjes_hebbenJuisteWaarde() {
		Pot p1 = new Pot();
		int aantal1 = 0, aantal2 = 0, aantal3 = 0, aantal4 = 0, aantal5 = 0, aantal6 = 0;
		List<Steen> pot = p1.getPot();
		for (Steen steen : pot) {
			switch (steen.getWaarde()) {
			case 1: aantal1++;
			break;
			case 2: aantal2++;
			break;
			case 3: aantal3++;
			break;
			case 4: aantal4++;
			break;
			case 5: aantal5++;
			break;
			case 6: aantal6++;
			break;
				
			}
		}
		assertEquals(21, aantal1);
		assertEquals(20, aantal2);
		assertEquals(20, aantal3);
		assertEquals(20, aantal4);
		assertEquals(20, aantal5);
		assertEquals(20, aantal6);
		
	}

}
