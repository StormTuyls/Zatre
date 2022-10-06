package domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.validator.PublicClassValidator;

import resourceBundle.TaalKiezer;

public class Speler {
	private static LocalDate currentDate = LocalDate.now();
	private String naam;
	private int geboortejaar;
	private int aantalSpeelKansen;
	private Scoreblad scoreblad;
	private List<Steen> steentjesInHand;
	private static final int HUIDIG_JAAR = currentDate.getYear();
	private static final int BEGIN_SPEELKANSEN = 5;
	private TaalKiezer kiezer = new TaalKiezer();
	
	public Scoreblad getScoreblad() {
		return scoreblad;
	}
	
	public Speler() {
		this("NaamNietGekozen", 2000, BEGIN_SPEELKANSEN);
	}

	public Speler(String naam, int geboortejaar) {
		this(naam, geboortejaar, BEGIN_SPEELKANSEN);
	}

	/**
	 * De constructor gaan de properties setten
	 */
	public Speler(String naam, int geboortejaar, int aantalSpeelkansen) {
		steentjesInHand = new ArrayList<>();
		setNaam(naam);
		setGeboortejaar(geboortejaar);
		setAantalSpeelKansen(aantalSpeelkansen);
	}

	// UC3
	public int berekenScore() {
		return aantalSpeelKansen;
	}

	public String getNaam() {
		return naam;
	}

	private void setNaam(String naam) {
		if (naam == null || naam.isBlank() || naam.length() < 5)
			throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errornaam"));
		this.naam = naam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		if (HUIDIG_JAAR - geboortejaar < 6)
			throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errorgeboortejaar"));
		this.geboortejaar = geboortejaar;
	}

	public int getAantalSpeelKansen() {
		return aantalSpeelKansen;
	}

	private void setAantalSpeelKansen(int aantalSpeelKansen) {
		if (aantalSpeelKansen < 0)
			throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errorGeenSpeelkansen"));
		this.aantalSpeelKansen = aantalSpeelKansen;
	}

	public void verminderSpeelkansenMetEen() {
		setAantalSpeelKansen(aantalSpeelKansen-1);
	}

	public void pasSpeelKansenAan(int aantal) {
		aantalSpeelKansen += aantal;
	}

	public void refreshScoreBlad() {
		scoreblad = new Scoreblad();
	}

	/**
	 * Wanneer beurt 1 is: er worden 3 random steentjes uit de pot meegegeven met
	 * pot.getRandomSteen. Wanneer beurt !1 is: 2 random steentjes worden meegegeven
	 * uit pot.getRandomSteen.
	 */

	public List<Steen> geefSteentjesUitPot(Pot pot, int beurt) {
		List<Steen> steentjes;
		if (beurt == 1) {
			steentjes = new ArrayList<>(3);

			for (int aantal = 1; aantal < 4; aantal++) {
				steentjes.add(pot.tweedeGetRandomSteen());
				
			}
		} else {
			steentjes = new ArrayList<>(2);
			for (int aantal = 1; aantal < 3; aantal++) {
				
				steentjes.add(pot.tweedeGetRandomSteen());
			}
		}
		return steentjes;
	}

	public String toString(String scoreBladString) {
		String resultString = "";
		resultString = String.format("%60s:\n", naam);
		resultString += scoreBladString;
		return resultString;

	}
//	public boolean isGewonnen(Spel s) {
//		if (s.getSpelerMetHoogsteScore().naam == naam)
//			return true;
//		return false;
//		
//	}

	public void speelBeurt(Pot pot, int beurt) {
	    // TODO Auto-generated method stub
	    
	}

}
