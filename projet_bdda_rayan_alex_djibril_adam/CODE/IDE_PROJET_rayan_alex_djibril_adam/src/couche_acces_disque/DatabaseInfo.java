package couche_acces_disque;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Contient les informations concernant l’ensemble des relations de votre base de données.
 */
public class DatabaseInfo {

    private List<TableInfo> tabInfoList;
    private int nbrRelation;

    public DatabaseInfo(int nbrRelation){
       tabInfoList = new ArrayList<>();
        nbrRelation = 0; 
    }

    public void Init(){
        String nomFichier = DBParams.DBPath + "DBInfo.save";
        File file = new File(nomFichier);
        //TO DO
    }
    public void Finish(){
    //vide pour l'instant
    }
    
    /**
     * Doit rajouter l'argument tableInfo à la liste et ensuite actualiser le compteur
     * @param TableInfo information d'une relation que l'on veux ajouter à la liste
     * @return le nombre de relation de notre liste
     */
    public int AddTableInfo(TableInfo TableInfo){
        tabInfoList.add(TableInfo);
        nbrRelation = tabInfoList.size();
        return nbrRelation ;
    }
    
   /**
    * Trouve la tableInfo d'une relation donné
    * @param nomRelation me relation pour laquelle on veux connaitre les information
    * @return la TableInfo associé a la relation donné
    */
    public TableInfo GetTableInfo(String nomRelation){
        for (TableInfo tableInfo : tabInfoList) {
            if (tableInfo.getNomRelation().equals(nomRelation)) {
                return tableInfo;
            }
        }
            return null;
    }
}