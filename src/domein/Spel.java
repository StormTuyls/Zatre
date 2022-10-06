package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.ToggleButton;

public class Spel {
	private List<Speler> spelers;
	private Pot pot;
	private Bord bord;
	private boolean eindeSpel = false;

	public void setEindeSpel(boolean eindeSpel) {
		this.eindeSpel = eindeSpel;
	}

	

	/**
	 * Er zijn 2 constructors: die zonder params maakt een nieuw bord, pot en lijst
	 * van spelers aan. Die met param geselecteerdeSpelers gaat nog eens voor elke
	 * geselecteerde speler de spelkansen verminderen en zijn scoreblad refreshen.
	 * Ook gaan de geselecteerde spelers geshuffled worden.
	 */
	public Spel() {
		bord = new Bord();
		pot = new Pot();
		spelers = new ArrayList<>();
	}

	public Spel(List<Speler> geselecteerdeSpelers) {
		this();
		spelers = geselecteerdeSpelers;
		for (Speler speler : geselecteerdeSpelers) {
			speler.verminderSpeelkansenMetEen();
			speler.refreshScoreBlad();
		}

		shuffleSpelers(spelers);

	}

	/**
	 * Als alle steentjes uit de pot waarde 0 (verwijderd) hebben, is het spel
	 * gedaan
	 */
	public boolean isEindeSpel() {
		List<Steen> potTeChecken = pot.getPot();
		boolean result = false;
			if (potTeChecken.size() == 0)
				result = true;
		return result;
	}

	public void shuffleSpelers(List<Speler> speler) {
		Collections.shuffle(speler);

	}

	/**
	 * Vergelijkt de score van elke speler en bepaalt degene met de hoogste score
	 */
	public Speler getSpelerMetHoogsteScore() {
		Speler winnaar = spelers.get(0);
		int hoogsteScore = 0;
		for (Speler speler : spelers) {
			if (speler.getScoreblad().getTotaal() > hoogsteScore)
				winnaar = speler;
			hoogsteScore = winnaar.getScoreblad().getTotaal();
		}
		return winnaar;
	}

	public Pot getPot() {
		return pot;
	}

	public Bord getBord() {
		return bord;
	}

}
