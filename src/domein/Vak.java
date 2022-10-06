package domein;

import com.mysql.cj.exceptions.NumberOutOfRange;

public class Vak {
	private int waarde;
	// waarde 0: wit, waarde 1: grijs, waarde 2: muur
	private int kleur;

	public Vak() {
		setKleur(0);
	}

	public Vak(int kleur) {

		setKleur(kleur);
		setHeeftSteen(0);
	}

	public int getKleur() {
		return kleur;
	}

	private void setKleur(int kleur) {
		if (kleur > -1 && kleur < 3) {
			this.kleur = kleur;

		} else {
			throw new NumberOutOfRange("Kleur moet wit of grijs zijn");
		}
	}

	public int getWaarde() {
		return waarde;
	}

	public final void setHeeftSteen(int waarde) {
		this.waarde = waarde;
	}

}
