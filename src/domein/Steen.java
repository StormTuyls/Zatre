package domein;

import com.mysql.cj.exceptions.NumberOutOfRange;

public class Steen {
	private int waarde;

	public int getWaarde() {
		return waarde;
	}

	private void setWaarde(int waarde) {
		if (waarde >= 0 && waarde < 7) {
			this.waarde = waarde;
		} else {
			throw new NumberOutOfRange("Foute waarde voor steentje");
		}
	}

	public Steen(int waarde) {
		setWaarde(waarde);
	}
}
