package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.exceptions.NumberOutOfRange;

import domein.DomeinController;
import domein.Spel;
import resourceBundle.TaalKiezer;

public class UC2Applicatie {
	DomeinController dc;
	Spel spel;
	TaalKiezer kiezer = new TaalKiezer();

	public void start() {
		KiesTaal();
		SelecteerSpeler();
	}

	public UC2Applicatie(DomeinController dc) {
		this.dc = dc;
	}

	private void SelecteerSpeler() {
		Scanner scanner = new Scanner(System.in);
		String naam;
		int geboortejaar;
		int teller = 0;
		int antwoord = 0;
		boolean invoerOK = false;
		boolean stoppenOK = false;

		do {
			do {

				try {
					System.out.print(kiezer.getTaalBundle().getString("naam"));
					naam = scanner.next();

					System.out.print(kiezer.getTaalBundle().getString("geboortejaar"));
					geboortejaar = scanner.nextInt();

					dc.selecteerSpeler(naam, geboortejaar);
					teller++;
					invoerOK = true;

				} catch (InputMismatchException aa) {
					System.err.println(kiezer.getTaalBundle().getString("errorGetal"));
					scanner.nextLine();
				} catch (IllegalArgumentException ae) {
					System.err.println(ae.getMessage());
					scanner.nextLine();
				}
			} while (!invoerOK);

			try {

				if (teller != 4 && teller != 1) {
					do {
						try {
							System.out.print(kiezer.getTaalBundle().getString("keuze_spelers"));
							antwoord = scanner.nextInt();

							if (antwoord != 1 && antwoord != 2)
								throw new IllegalArgumentException(kiezer.getTaalBundle().getString("error1of2"));
						} catch (IllegalArgumentException az) {
							System.err.print(az.getMessage());
						}
					} while (antwoord != 1 && antwoord != 2);

				} else if (teller == 1)
					stoppenOK = false;
				else
					stoppenOK = true;

				if (antwoord == 2) {
					stoppenOK = true;
				}
			} catch (InputMismatchException ae) {
				System.err.print(kiezer.getTaalBundle().getString("errorGetal"));
				scanner.nextLine();
			}

		} while (!stoppenOK);
		for (String s : dc.geselecteerdeSpelersToString()) {
			System.out.println(s);
		}
		
		UC3Applicatie applicatie = new UC3Applicatie(dc);
		applicatie.start();

	}

	public void KiesTaal() {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println(kiezer.getTaalBundle().getString("keuze_taal"));
			int a = scanner.nextInt();

			kiezer.setTaal(a);

		} catch (NumberOutOfRange e) {
			System.err.println(e.getMessage());
		}
	}
}