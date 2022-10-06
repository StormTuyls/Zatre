package resourceBundle;

import java.util.ResourceBundle;
import java.util.Scanner;

public class TaalApp {

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Kies een taal (1 NL 2 EN)");
		int a = scanner.nextInt();
		TaalKiezer kiezer = new TaalKiezer();
		System.out.println(kiezer.getTaalBundle().getString("vraag"));

	}



	public static void displayValues(ResourceBundle bundle)
	{
		System.out.println(bundle.getString("begroeting"));
		System.out.println(bundle.getString("afsluit"));
		System.out.println(bundle.getString("vraag"));
		System.out.println();
	}
}
