package couche_acces_disque;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        //on peut le faire en saaah
    }
    public void Finish(){
    //vide pour l'instant
    }
    /* Doit rajouter a la liste et ensuite actualiser le compteur
    */
    public int AddTableInfo(TableInfo TableInfo){
        tabInfoList.add(TableInfo);
        nbrRelation = tabInfoList.size();
        return nbrRelation ;
    }


    /*retourne la TableInfo associé a l'argument donné */
    public TableInfo GetTableInfo(String nomRelation){
        for (TableInfo tableInfo : tabInfoList) {
            if (tableInfo.getNomRelation().equals(nomRelation)) {
                return tableInfo;
            }
        }
            return null;
    }
}