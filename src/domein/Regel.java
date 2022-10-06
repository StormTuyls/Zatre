package domein;

public class Regel {

	private boolean verdubbelingPunten;
	private int bonus, totaal = 0;
	private int kolom10, kolom11, kolom12;
	private int huidigeBeurt;

	/**
	 * er worden telkens per kolom het juiste aantal punten opgeteld bij het totaal
	 * van de regel
	 */
	private int berekenTotaal() {

		totaal += kolom10 * 1;
		totaal += kolom11 * 2;
		totaal += kolom12 * 4;
		if (kolom10 != 0 && kolom11 != 0 && kolom12 != 0) {
			totaal += bonus;
		}

		if (verdubbelingPunten) {
			totaal *= 2;
		}
		return totaal;

	}

	public int getTotaal() {
		return totaal;
	}

	public Regel(boolean verdubbelingPunten, int kolom10, int kolom11, int kolom12, int beurt, int aantalSpelers) {
		this.verdubbelingPunten = verdubbelingPunten;
		this.kolom10 = kolom10;
		this.kolom11 = kolom11;
		this.kolom12 = kolom12;
		this.huidigeBeurt = beurt;
		if (beurt > 0 && beurt < 5 * aantalSpelers)
			this.bonus = 3;
		if (beurt >= 5 * aantalSpelers && beurt < 9 * aantalSpelers)
			this.bonus = 4;
		if (beurt >= 9 * aantalSpelers && beurt < 13 * aantalSpelers)
			this.bonus = 5;
		if (beurt >= 13 * aantalSpelers)
			this.bonus = 6;
		this.totaal = berekenTotaal();
	}

	public String toString() {
		return String.format("\n%6s %5s %10s %10s %10s %10d %10d", huidigeBeurt, verdubbelingPunten ? "x" : " ",
				getalNaarKruisje(kolom10), getalNaarKruisje(kolom11), getalNaarKruisje(kolom12), bonus, totaal);
	}

	public String tussenRondeToString() {
		return String.format("\n%6s %5s %10s %10s %10s", huidigeBeurt, verdubbelingPunten ? "x" : " ",
				getalNaarKruisje(kolom10), getalNaarKruisje(kolom11), getalNaarKruisje(kolom12));
	}

	public String getalNaarKruisje(int getal) {
		String resultString = "";
		if (getal != 0)
			for (int i = 0; i < getal; i++) {
				resultString += "x";
			}
		else
			resultString = "";
		return resultString;
	}
}
