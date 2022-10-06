package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.DomeinController;
import domein.Regel;

class RegelTest {

    private DomeinController dc;

    @BeforeEach
    public void setUp() throws Exception {
	dc = new DomeinController();
    }

    @Test
    public void BerekenTotaalJuist_2Spelers() {
	dc.selecteerSpeler("AlexM", 2003);
	dc.selecteerSpeler("arnoud", 2002);
	for (int i = 0; i < 15 * dc.getGeselecteerdeSpelers().size(); i++) {
	    Regel regelLeeg = new Regel(false, 0, 0, 0, i, dc.getGeselecteerdeSpelers().size());
	    Regel regel10 = new Regel(false, 1, 0, 0, i, dc.getGeselecteerdeSpelers().size());
	    Regel regel11 = new Regel(false, 0, 1, 0, i, dc.getGeselecteerdeSpelers().size());
	    Regel regel12 = new Regel(false, 0, 0, 1, i, dc.getGeselecteerdeSpelers().size());
	    Regel regelVol = new Regel(true, 1, 1, 1, i, dc.getGeselecteerdeSpelers().size());

	    assertEquals(0, regelLeeg.getTotaal());
	    assertEquals(1, regel10.getTotaal());
	    assertEquals(2, regel11.getTotaal());
	    assertEquals(4, regel12.getTotaal());
	    if (i > 0 && i < 5 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(20, regelVol.getTotaal());
	    }
	    if (i >= 5 * dc.getGeselecteerdeSpelers().size() && i < 9 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(22, regelVol.getTotaal());
	    }
	    if (i >= 9 * dc.getGeselecteerdeSpelers().size() && i < 13 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(24, regelVol.getTotaal());
	    }
	    if (i >= 13 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(26, regelVol.getTotaal());
	    }
	}
    }

    @Test
    public void BerekenTotaalJuist_MeerdereWaarden() {
	dc.selecteerSpeler("AlexM", 2003);
	dc.selecteerSpeler("arnoud", 2002);
	dc.selecteerSpeler("Boomer", 2015);
	for (int i = 0; i < 15 * dc.getGeselecteerdeSpelers().size(); i++) {
	    Regel regel10trim = new Regel(true, 2, 0, 0, i, dc.getGeselecteerdeSpelers().size());
	    Regel regel11trim = new Regel(true, 0, 2, 0, i, dc.getGeselecteerdeSpelers().size());
	    Regel regel12trim = new Regel(true, 0, 0, 2, i, dc.getGeselecteerdeSpelers().size());
	    Regel regelVolTrim = new Regel(false, 3, 2, 3, i, dc.getGeselecteerdeSpelers().size());

	    assertEquals(4, regel10trim.getTotaal());
	    assertEquals(8, regel11trim.getTotaal());
	    assertEquals(16, regel12trim.getTotaal());
	    if (i > 0 && i < 5 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(22, regelVolTrim.getTotaal());
	    }
	    if (i >= 5 * dc.getGeselecteerdeSpelers().size() && i < 9 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(23, regelVolTrim.getTotaal());
	    }
	    if (i >= 9 * dc.getGeselecteerdeSpelers().size() && i < 13 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(24, regelVolTrim.getTotaal());
	    }
	    if (i >= 13 * dc.getGeselecteerdeSpelers().size()) {
		assertEquals(25, regelVolTrim.getTotaal());
	    }
	}
    }
}
