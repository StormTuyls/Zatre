package domein;

public class Bord {
	private Vak[][] spelbord;

	public Vak[][] getSpelbord() {
		return spelbord;
	}

	public Vak getVak(int rij, int kolom) {
		return spelbord[rij][kolom];
	}

	public Bord() {
		spelbord = new Vak[15][15];
		vulVakken();

	}

	public void vulVakken() {
		/**
		 * bij deze kolommen met rijvak 1 en 15 een muur staan
		 */
		int[] muurArray = { 2, 3, 4, 8, 12, 13, 14 };

		for (int rij = 0; rij < 15; rij++) {
			for (int kolom = 0; kolom < 15; kolom++) {
				spelbord[rij][kolom] = new Vak(0);
			}
		}

		for (int i = 0; i < 15; i++) {
			if (i == 0 || i == 14) {

				/**
				 * bij kolommen 1 en 15 moeten de rijvakken 1-4, 8 en 12-15 muren worden
				 */
				spelbord[i][0] = new Vak(2);
				spelbord[i][1] = new Vak(2);
				spelbord[i][2] = new Vak(2);
				spelbord[i][3] = new Vak(2);

				spelbord[i][7] = new Vak(2);

				spelbord[i][11] = new Vak(2);
				spelbord[i][12] = new Vak(2);
				spelbord[i][13] = new Vak(2);
				spelbord[i][14] = new Vak(2);
				/* rijvakken 7 en 9 moeten grijs worden */
				spelbord[i][6] = new Vak(1);
				spelbord[i][8] = new Vak(1);
			}

			for (int getal : muurArray) {
				if (getal == i) {

					/**
					 * bij elke kolom uit de muurArray moeten de rijvakken 1 en 15 muren worden
					 */
					spelbord[i - 1][0] = new Vak(2);
					spelbord[i - 1][14] = new Vak(2);
				}
			}

			if (i == 7 || i == 9) {
				/**
				 * bij kolom 8 en 10 worden rijvakken 1 en 15 grijs
				 */
				spelbord[i - 1][0] = new Vak(1);
				spelbord[i - 1][14] = new Vak(1);
			}
			/**
			 * bij elke iteratie over de loop worden de diagonalen aangevuld met deze code
			 */
			if (i < 13) {
				spelbord[i + 1][i + 1] = new Vak(1);
			}

			if (i < 13) {
				spelbord[13 - i][i + 1] = new Vak(1);

			}

		}
	}
}
