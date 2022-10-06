package domein;

import java.util.ArrayList;
import java.util.List;

import resourceBundle.TaalKiezer;

public class DomeinController {

	private SpelerRepository spelerRepository;
	private List<Speler> geselecteerdeSpelers;
	private List<Speler> spelerLijst;

	public List<Speler> getSpelerLijst() {
		return spelerLijst;
	}

	private TaalKiezer kiezer;
	private Spel s;

	public Spel getSpel() {
		return s;
	}

	/**
	 * De constructor initialiseert de spelerRepository, spelerlijst,
	 * geselecteerdeSpeler en kiezer. StartSpel wordt aangeroepen
	 */

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spelerLijst = spelerRepository.geefSpelers();
		geselecteerdeSpelers = new ArrayList<>();
		kiezer = new TaalKiezer();
		startSpel();
	}

	public void registreerNieuweSpeler(String naam, int geboortejaar) {
		spelerRepository.voegSpelerToe(new Speler(naam, geboortejaar));
	}

	/**
	 * gekozenSpeler wordt tijdelijk opgevuld met een lege speler. zolang de speler
	 * nog speelkansen heeft, wordt de hele spelerlijst overlopen en wordt telkens
	 * de naam van de te selecteren speler vergeleken met de naam van elke speler.
	 * Komt deze overeen en is deze nog niet geselecteerd, dan wordt gekozenSpeler
	 * gelijkgesteld aan deze speler en wordt deze toegevoegd aan de lijst van
	 * geselecteerdespelers
	 * 
	 */

	public void selecteerSpeler(String gebruikersnaam, int geboortejaar) {
		Speler legeSpeler = new Speler("legeSpeler", 2003);
		Speler gekozenSpeler = legeSpeler;

		if (gekozenSpeler.getAantalSpeelKansen() == 0) {
			throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errorGeenSpeelkansen"));
		}
		for (Speler speler : spelerLijst) {
			if ((speler.getNaam().equals(gebruikersnaam)) && (speler.getGeboortejaar() == geboortejaar)) {

				gekozenSpeler = speler;

				if (!isSpelerGeselecteerd(gekozenSpeler)) {
					if ((speler.getAantalSpeelKansen() != 0))
						getGeselecteerdeSpelers().add(gekozenSpeler);
					else
						throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errorGeenSpeelkansen"));
				} else
					throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errorAlGeselecteerd"));

			}

		}
		if (gekozenSpeler.equals(legeSpeler))
			throw new IllegalArgumentException(kiezer.getTaalBundle().getString("errorBestaatNiet"));

	}

	/**
	 * checkt of meegegeven speler al in de lijst van geselecteerde spelers zit
	 */

	public boolean isSpelerGeselecteerd(Speler speler) {
		for (Speler s : getGeselecteerdeSpelers()) {
			if (s.getNaam() == speler.getNaam() && s.getGeboortejaar() == speler.getGeboortejaar())
				return true;
		}
		return false;
	}

	public String geefSpelersString() {
		return spelerRepository.geefSpelersString();
	}

	public List<String> alleSpelersToString() {

		List<String> spelersStringList = new ArrayList<>();
		for (Speler speler : getSpelerLijst()) {

			spelersStringList.add(String.format("%s, %d, %d", speler.getNaam(), speler.getGeboortejaar(),
					speler.getAantalSpeelKansen()));
		}
		return spelersStringList;
	}

	public List<String> geselecteerdeSpelersToString() {

		List<String> spelersStringList = new ArrayList<>();

		for (Speler speler : getGeselecteerdeSpelers()) {

			spelersStringList.add(String.format("%s, %d, %d", speler.getNaam(), speler.getGeboortejaar(),
					speler.getAantalSpeelKansen()));
		}
		return spelersStringList;
	}

	public List<Speler> getGeselecteerdeSpelers() {
		return geselecteerdeSpelers;
	}

	public void startSpel() {
		s = new Spel(this.getGeselecteerdeSpelers());
	}

	public void clearGeselecteerdeSpelers() {
		geselecteerdeSpelers.clear();
	}

	public void refreshSpelers() {
		spelerLijst = spelerRepository.geefSpelers();
	}
	public void pasSpeelkansenAan(Speler speler)
	{
		spelerRepository.verminderSpeelkansen(speler);
	}
	
}