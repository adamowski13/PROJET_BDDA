package couche_acces_disque;

public class Main {
	
	public static void main(String [] args) {
		DBParams.DBPath = args[0];
		DBParams.SGBDPageSize = 4096;
		DBParams.DMFileCount = 4;

		DatabaseManager dataManager = new DatabaseManager();
		dataManager.init();

		String choixCommande = "";

		do{
			System.out.println("Saisissez la commande ou EXIT si vous voulez quitter : ");

			switch (choixCommande) {
				case "CreatTable":
					System.out.println("");
					
					dataManager.ProcessCommande(choixCommande);
					break;
				
				case "Insert":
					System.out.println("");
					
					dataManager.ProcessCommande(choixCommande);
					break;
				
				case "Import":
					System.out.println("");
					
					dataManager.ProcessCommande(choixCommande);
					break;
				
				case "EXIT":
					System.out.println("Fin");
					break;
			
				default: 
					System.out.println("Veuiller appuyer sur le numéro correspondant a la commande choisis");
					break;
			}
		}while(choixCommande == "EXIT");
	}

}
