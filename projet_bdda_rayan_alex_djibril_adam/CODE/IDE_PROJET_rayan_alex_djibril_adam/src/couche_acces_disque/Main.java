package couche_acces_disque;

public class Main {
	
	public static void main(String [] args) {
		DBParams.DBPath = args[0];
		DBParams.SGBDPageSize = 4096;
		DBParams.DMFileCount = 4;
	}

}
