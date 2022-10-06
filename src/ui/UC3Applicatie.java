package ui;

import java.util.List;

import domein.Bord;
import domein.DomeinController;
import domein.Spel;
import domein.Speler;
import domein.Steen;
import domein.Vak;

public class UC3Applicatie {
	private Spel spel;
	private DomeinController dc;
	private List<Speler> geselecteerdeSpelers;

	public UC3Applicatie(DomeinController dc) {
		geselecteerdeSpelers = dc.getGeselecteerdeSpelers();
		this.dc = dc;
		this.spel = dc.getSpel();
	}

	public void start() {
		dc.startSpel();
		for (Speler speler : geselecteerdeSpelers) {
			speler.refreshScoreBlad();
			// speler.verminderSpeelkansenMetEen();
			// System.out.println(speler.getAantalSpeelKansen());
		}
		spel.shuffleSpelers(geselecteerdeSpelers);

		int teller = 0, beurt = 0;
		do {
		    beurt++;
			for (Speler speler : geselecteerdeSpelers) {
				teller++;
				speler.speelBeurt(spel.getPot(), beurt);
				if (teller == 5)
					spel.setEindeSpel(true);
			}
		} while (!spel.isEindeSpel());
		for (Speler speler : geselecteerdeSpelers) {
			String scorebladString = speler.getScoreblad().toString();
			String string = speler.toString(scorebladString);
			System.out.println(string);
		}
		Speler winnaar = spel.getSpelerMetHoogsteScore();
		System.out.printf("Winnaar: %s", winnaar.getNaam());

		Bord b = new Bord();
		Vak[][] spelBord = b.getSpelbord();

		for (int i = 0; i < spelBord.length; i++) {
			for (int j = 0; j < spelBord[i].length; j++) {
				System.out.print(spelBord[i][j].getKleur());
			}
			System.out.println();
		}
//		System.out.println(spelBord);
	}

}
