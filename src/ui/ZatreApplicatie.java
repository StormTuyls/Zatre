package ui;

import java.util.Scanner;

import domein.DomeinController;
import resourceBundle.TaalKiezer;

public class ZatreApplicatie {

	DomeinController dController;

	public ZatreApplicatie(DomeinController dc) {
		dController = dc;

	}

	public void start() {
		UC2Applicatie uc = new UC2Applicatie(dController);
		SpelerApplicatie speler = new SpelerApplicatie(dController);
		TaalKiezer kiezer = new TaalKiezer();

		Scanner scanner = new Scanner(System.in);
		int keuze = 0;

		System.out.println(kiezer.getTaalBundle().getString("keuze"));
		do {
			System.out.println(kiezer.getTaalBundle().getString("keuze_speel"));
			System.out.println(kiezer.getTaalBundle().getString("keuze_registreer"));

			keuze = scanner.nextInt();
		} while (keuze < 1 || keuze > 2);

		if (keuze == 1) {
			uc.start();
		} else {
			speler.start();

		}
	}
}
