package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.DomeinController;
import domein.Regel;
import domein.Scoreblad;

class ScoreBladTest {
    private Scoreblad scoreblad;
    private DomeinController dc;

    @BeforeEach
    public void setUp() throws Exception {
	dc = new DomeinController();
	scoreblad = new Scoreblad();

    }

    @Test
    public void berekenScore_berekentJuistTotaal() {
	dc.selecteerSpeler("AlexM", 2003);
	dc.selecteerSpeler("arnoud", 2002);

	scoreblad.UpdateScoreblad(false, 0, 0, 0, 1, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(false, 1, 0, 0, 2, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(false, 0, 1, 0, 3, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(false, 0, 0, 1, 4, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(true, 1, 1, 1, 5, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(true, 2, 0, 0, 6, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(true, 0, 2, 0, 7, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(true, 0, 0, 2, 8, dc.getGeselecteerdeSpelers().size());
	scoreblad.UpdateScoreblad(false, 3, 2, 3, 9, dc.getGeselecteerdeSpelers().size());

	assertEquals(77, scoreblad.getTotaal());
    }

}
