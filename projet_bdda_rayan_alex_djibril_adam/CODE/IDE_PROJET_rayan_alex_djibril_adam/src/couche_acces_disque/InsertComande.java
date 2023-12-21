package couche_acces_disque;

import java.util.*;

/**
 * Cette classe représente une commande d'insertion (INSERT) dans une relation.
 */
public class InsertComande {
    private String nomRelation;
    private List<Record> valeurs;
    
    /**
     * Constructeur de la classe InsertCommande.
     *
     * @param command La commande d'insertion.
     */
    public InsertComande(String command){
        //supression des espaces
        command = command.trim(); 
        
        // Vérifier si la commande commence par "INSERT INTO"
        if (!command.startsWith("INSERT INTO")) {
            throw new IllegalArgumentException("La commande INSERT INTO est invalide.");
        }

        // Extraire le nom de la relation
        String[] parts = command.split("INSERT INTO|VALUES");
        nomRelation = parts[1].trim();

        
    }
}
