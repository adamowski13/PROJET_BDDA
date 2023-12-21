package couche_acces_disque;

import java.util.*;


/**
 * Cette classe représente une commande de création de table.
 */
public class CreateTableCommand {
    private String nomRelation;
    private int nbColonne;
    private List<String> nomDesColonnes;
    private List<String> typeDesColonnes;
    
    
    /**
     * Constructeur de la classe CreateTableCommand.
     * 
     * @param command La commande de création de table.
     * @throws IllegalArgumentException Si la commande est invalide.
     */
    public CreateTableCommand(String command){
        //supression es espaces
        command = command.trim(); 
        
        // Vérifier si la commande commence par "CREATE TABLE"
        if (!command.startsWith("CREATE TABLE")) {
            throw new IllegalArgumentException("La commande CREATE TABLE est invalide.");
        }

        // Extraire le nom de la table
        int NomDeLaTable = "CREATE TABLE".length();
        int FinNomTable = command.indexOf('(');
        nomRelation = command.substring(NomDeLaTable, FinNomTable).trim();

        // Extraire les colonnes et leurs types
        String colones = command.substring(FinNomTable + 1, command.length() - 1).trim();
        String[] lesColones = colones.split(",");

        nbColonne = lesColones.length;
        nomDesColonnes = new ArrayList<String>();
        typeDesColonnes = new ArrayList<String>();

        for (String colone : lesColones) {
            String[] parts = colone.trim().split("\\s+"); // Supprimer les espaces autour du nom et du type
            nomDesColonnes.add(parts[0].trim());
            typeDesColonnes.add(parts[1].trim());
        }
    }
    
    /**
     * Exécute la commande de création de table.
     */
    public void Excecute(){
        FileManager fileManager = new FileManager();
        PageId headerPage = fileManager.createNewHeaderPage();
        TableInfo tableInfo = new TableInfo(nomRelation, nbColonne, headerPage);
        DatabaseInfo databaseInfo = new DatabaseInfo(nbColonne);
        databaseInfo.AddTableInfo(tableInfo);
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de la commande de création de table.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nom de la relation : " + this.nomRelation);
        sb.append("\n");
        sb.append("nombre de colone : " + this.nbColonne);
        sb.append("\n");
        sb.append("Nom des colones :");
        for(int i=0;i<nomDesColonnes.size();i++){
            sb.append(" " + nomDesColonnes.get(i).toString() + " , ");
        }
        sb.append("Type des colones :");
        for(int j=0;j<typeDesColonnes.size();j++){
            sb.append(" " + typeDesColonnes.get(j).toString() + " , ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
