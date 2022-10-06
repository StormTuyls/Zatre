package resourceBundle;

import java.util.ResourceBundle;

public class TaalKiezer {

	public static ResourceBundle NL = ResourceBundle.getBundle("resourceBundle.TestBundle");
	public static ResourceBundle EN = ResourceBundle.getBundle("resourceBundle.TestBundle_en_US");
	private static int a = 2;

	public static int getA() {
		return a;
	}

	public TaalKiezer() {
		
	}



	public ResourceBundle getTaalBundle()
	{
		if(a==1)
		{
			return NL;
		}
		else if (a == 2)
		{
			return EN;
		}
		return NL;
	}
	
	public void setTaal(int a)
	{
		if(a == 1 || a == 2)
		TaalKiezer.a = a;
		
	}






}
