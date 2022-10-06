package domein;

import java.util.List;

import persistentie.SpelerMapper;

public class SpelerRepository {
	private SpelerMapper spelerMapper;
	private List<Speler> spelers;

	public SpelerRepository() {

		spelerMapper = new SpelerMapper();
		spelers = spelerMapper.geefSpelers();
	}

	public void voegSpelerToe(Speler speler) {
		spelerMapper.voegToe(speler);
	}

	public boolean bestaatSpeler(String naam, int geboortejaar) {
		return spelerMapper.geefSpelers().contains(new Speler(naam, geboortejaar));
	}

	Speler geefSpeler(String naam, int geboortejaar) {
		return spelerMapper.geefSpeler(naam, geboortejaar);
	}

	public List<Speler> geefSpelers() {
		spelers = spelerMapper.geefSpelers();
		return spelers;
	}

	public String geefSpelersString() {
		String str = "";
		for (Speler speler : spelers) {
			str += String.format("%s, %d, %d %n", speler.getNaam(), speler.getGeboortejaar(),
					speler.getAantalSpeelKansen());
		}
		return str;
	}
	
	public void verminderSpeelkansen(Speler speler)
	{
		spelerMapper.slaKredietOp(speler);
	}

}
