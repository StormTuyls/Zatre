package resourceBundle;

import java.util.Locale;

public class I18N {

	public static void main(String[] args)
	{

		Locale locale = Locale.getDefault();


		System.out.println(locale.getDisplayCountry());
	      System.out.println(locale.getDisplayLanguage());
	      System.out.println(locale.getDisplayName());
	      System.out.println(locale.getISO3Country());
	      System.out.println(locale.getISO3Language());
	      System.out.println(locale.getLanguage());
	      System.out.println(locale.getCountry());

	      Locale frenchLocale = new Locale("fr","fr");

	      System.out.println("\nfr Locale Properties:\n");
	      System.out.println(frenchLocale.getDisplayCountry());
	      System.out.println(frenchLocale.getDisplayLanguage());
	      System.out.println(frenchLocale.getDisplayName());
	      System.out.println(frenchLocale.getISO3Country());
	      System.out.println(frenchLocale.getISO3Language());
	      System.out.println(frenchLocale.getLanguage());
	      System.out.println(frenchLocale.getCountry());


	}

}
