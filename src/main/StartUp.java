package main;

import domein.DomeinController;
import ui.ZatreApplicatie;

public class StartUp {
	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		new ZatreApplicatie(dc).start();
		// new UC2Applicatie(new DomeinController());

	}
}
