package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.DomeinController;
import domein.Spel;
import domein.Speler;
import domein.Steen;

class SpelTest {

    private DomeinController dc;

    @BeforeEach
    public void setUp() throws Exception {
	dc = new DomeinController();

	dc.selecteerSpeler("AlexM", 2003);
	dc.selecteerSpeler("arnoud", 2002);
	dc.getGeselecteerdeSpelers().get(0).refreshScoreBlad();
	dc.getGeselecteerdeSpelers().get(1).refreshScoreBlad();
    }

    @Test
    public void bepaaldWinnaarCorrect() {
	dc.getGeselecteerdeSpelers().get(0).getScoreblad().UpdateScoreblad(false, 0, 0, 0, 1, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(0).getScoreblad().UpdateScoreblad(false, 1, 0, 0, 2, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(0).getScoreblad().UpdateScoreblad(false, 0, 1, 0, 3, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(0).getScoreblad().UpdateScoreblad(false, 0, 0, 1, 4, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(0).getScoreblad().UpdateScoreblad(true, 1, 1, 1, 5, dc.getGeselecteerdeSpelers().size());

	dc.getGeselecteerdeSpelers().get(1).getScoreblad().UpdateScoreblad(true, 2, 0, 0, 6, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(1).getScoreblad().UpdateScoreblad(true, 0, 2, 0, 7, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(1).getScoreblad().UpdateScoreblad(true, 0, 0, 2, 8, dc.getGeselecteerdeSpelers().size());
	dc.getGeselecteerdeSpelers().get(1).getScoreblad().UpdateScoreblad(false, 3, 2, 3, 9, dc.getGeselecteerdeSpelers().size());

	assertEquals(dc.getGeselecteerdeSpelers().get(1), dc.getSpel().getSpelerMetHoogsteScore());
    }

}
