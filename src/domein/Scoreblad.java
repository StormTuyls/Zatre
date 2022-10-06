package domein;

import java.util.ArrayList;
import java.util.List;

public class Scoreblad {
	private int totaal;
	private List<Regel> scoreblad;

	public void berekenScore() {
		totaal = 0;
		for (Regel regel : scoreblad) {
			totaal += regel.getTotaal();
		}
//	while(scoreblad.iterator().hasNext()) {
//	    totaal += scoreblad.getTotaal();
//	}

	}

	public int getTotaal() {
		return totaal;
	}

	public Scoreblad() {

		scoreblad = new ArrayList<Regel>();
	}

	/**
	 * voegt telkens een regel met juiste waarden toe bij het oproepen
	 */
	public void UpdateScoreblad(boolean verdubbelingPunten, int kolom10, int kolom11, int kolom12, int beurt,
			int aantalSpelers) {
		scoreblad.add(new Regel(verdubbelingPunten, kolom10, kolom11, kolom12, beurt, aantalSpelers));
		berekenScore();
	}

	public String toString() {
		String scorebladString = String.format("%6s %5s %10s %10s %10s %10s %10s","Ronde", "x2", "10 (1pt)", "11 (2pt)", "12 (4pt)",
				"Bonus", "Totaal");
		for (Regel regel : scoreblad) {
			scorebladString += regel.toString();
		}
		scorebladString += String.format("\nTotaal: %d\n", totaal);
		return scorebladString;
	}

	public String tussenRondeToString() {
		String scorebladString = String.format("%6s %5s %10s %10s %10s","Ronde", "x2", "10 (1pt)", "11 (2pt)", "12 (4pt)");
		for (Regel regel : scoreblad) {
			scorebladString += regel.tussenRondeToString();
		}
		
		return scorebladString;
	}
}
