package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pot {
	private List<Steen> pot;

	public Pot() {
		pot = new ArrayList<>();
		vulPotOp();
	}

	/**
	 * Pot wordt opgevuld doormiddel van enkele for loops. De eerste for loop voegt
	 * 21 steentjes met waarde 1 toe aan de pot. voor de steentjes met waarde 2 tot
	 * 6 wordt een geneste for loop gebruikt
	 */

	public void vulPotOp() {
		for (int i = 0; i < 21; i++) {
			pot.add(new Steen(1));
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 20; j++) {
				pot.add(new Steen(i + 2));
			}
		}

	}

	public List<Steen> getPot() {
		return pot;
	}

	/**
	 * Zolang de waarde van de steen geen 0 is (steen die al gekozen is), wordt een
	 * random steen uit de pot gegeven
	 */
	public Steen getRandomSteen() {
		Steen steen;
		do {
			Random rand = new Random();
			int randomSteen = rand.nextInt((pot.size() - 1 - 0 + 1)) + 0;
			steen = pot.get(randomSteen);
			pot.set(randomSteen, new Steen(0));
		} while (steen.getWaarde() == 0);
		return steen;
	}

	public Steen tweedeGetRandomSteen() {
		Steen randomSteen;
		Random rnd = new Random();
		if (pot.size() == 0)
			throw new IndexOutOfBoundsException("Pot is leeg, kan geen steentje halen");
		randomSteen = pot.get(rnd.nextInt((pot.size() - 1) - 0 + 1) + 0);
		tweedeRemoveSteen(randomSteen.getWaarde());
		return randomSteen;
	}

	public final void setSteen(Integer steenWaarde) {
		for (Steen steenNul : pot) {
			if (steenNul.getWaarde() == 0) {
				pot.set(pot.indexOf(steenNul), new Steen(steenWaarde));
				break;
			}
		}
	}

	/**
	 * om aan te duiden dat een steen verwijderd is, zetten we da waarde op 0
	 */
	public void removeSteen(Integer steenWaarde) {
		for (Steen steen : pot) {
			if (steen.getWaarde() == steenWaarde) {
				pot.set(pot.indexOf(steen), new Steen(0));
			}
		}
	}

	public void tweedeRemoveSteen(Integer steen) {
		for (Steen s : pot) {
			if(s.getWaarde() == steen)
			{
				pot.remove(pot.indexOf(s));
				break;
			}
		}
	}
}
