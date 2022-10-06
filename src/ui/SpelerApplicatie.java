package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import domein.DomeinController;
import resourceBundle.TaalKiezer;

public class SpelerApplicatie {

	DomeinController dc;

	public SpelerApplicatie(DomeinController dc) {
		this.dc = dc;

	}

	public void start() {
		Scanner scanner = new Scanner(System.in);
		String naam;
		boolean invoerOK = false;
		int geboortejaar;
		TaalKiezer kiezer = new TaalKiezer();

		do {
			try {
				System.out.print(kiezer.getTaalBundle().getString("registratie"));
				naam = scanner.next();
				System.out.print(kiezer.getTaalBundle().getString("geboortejaar"));
				geboortejaar = scanner.nextInt();
				dc.registreerNieuweSpeler(naam, geboortejaar);
				invoerOK = true;

				System.out.printf(kiezer.getTaalBundle().getString("registratie_succesvol"), naam, geboortejaar);

			} catch (IllegalArgumentException ae) {
				System.err.println(ae.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException aa) {
				System.err.println(kiezer.getTaalBundle().getString("errorGetal"));
				scanner.nextLine();
			} catch (RuntimeException ru) {
				System.err.println(kiezer.getTaalBundle().getString("errorBestaat"));
				scanner.nextLine();
			}

		} while (!invoerOK);
	}

}
