package couche_acces_disque;

import java.util.*;

/**
 * La classe principale qui initialise le système de gestion de base de données
 * et gère les commandes utilisateur à partir de la console.
 */
public class Main {
	
	public static void main(String [] args) {
		// Configuration des paramètres de la base de données à partir des arguments de ligne de commande
		DBParams.DBPath = "PROJET_BDDA/projet_bdda_rayan_alex_djibril_adam/DB";
		DBParams.SGBDPageSize = 4096;
		DBParams.DMFileCount = 4;
		
		// Initialisation du gestionnaire de base de données
		DatabaseManager dataManager = new DatabaseManager();
		dataManager.init();
		
		// initialisation du scanner
		Scanner sc = new Scanner(System.in);
		
		String choixCommande = "";
		
		// Boucle principale de saisie de commandes
		do{
			
			System.out.println("Saisissez la commande ou EXIT si vous voulez quitter : ");
			System.out.println("Les commandes sont les suivantes : ");
			System.out.println("'CreatTable' /  'Insert'  pour l'instant");
			
			choixCommande = sc.next();
			switch (choixCommande) {
				case "CreatTable":
					// Traitement de la commande de création de table
					
					dataManager.ProcessCommande(choixCommande);
					break;
				
				case "Insert":
					// Traitement de la commande d'insertion
					
					dataManager.ProcessCommande(choixCommande);
					break;
				
				case "Import":
					// Traitement de la commande d'importation
					dataManager.ProcessCommande(choixCommande);
					break;
				
				case "EXIT":
					// Fin du programme
					System.out.println("Fin");
					break;
			
				default: 
					// Message d'erreur pour une commande non reconnue
					System.out.println("Veuiller appuyer sur le numéro correspondant a la commande choisis");
					break;
			}
		}while(choixCommande == "EXIT");
		
		sc.close();
	}

}
