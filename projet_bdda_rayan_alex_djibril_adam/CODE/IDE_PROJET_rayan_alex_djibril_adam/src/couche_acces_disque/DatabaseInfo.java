package couche_acces_disque;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void Init() throws FileNotFoundException, ClassNotFoundException{
        String filePath = "../../../../DB/DBInfo.save";
        // créer un chemin pour sauvegarder
        Path path = Paths.get(filePath);
        if(Files.exists(path)){
            try{
                FileInputStream fileIn = new FileInputStream("DB/DBInfo.save");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                DatabaseInfo dbInfo = (DatabaseInfo) objectIn.readObject();
                objectIn.close();
    
            }    catch (FileNotFoundException ex) {
                System.out.println("DBInfo.save non trouvé");}
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void Finish() throws IOException{
            String filePath = "../../../../DB/DBInfo.save";
            Path path = Paths.get(filePath);
            if(Files.notExists(path)){
        try{    

            FileOutputStream DBInfoenregistrement = new FileOutputStream("../../../../DB/DBInfo.save");
            ObjectOutputStream objectOut = new ObjectOutputStream(DBInfoenregistrement);
            objectOut.writeObject(this);
            objectOut.close();
            System.out.println("DatabaseInfo enregistré dans DBInfo.save");

    }   catch (IOException ex) {
            ex.printStackTrace();
        }        }
        else{
        
        try{    

            FileOutputStream DBInfoenregistrement = new FileOutputStream("../../../../DB/DBInfo.save");
            ObjectOutputStream objectOut = new ObjectOutputStream(DBInfoenregistrement);
            objectOut.writeObject(this);
            objectOut.close();
            System.out.println("DatabaseInfo enregistré dans DBInfo.save");

    }   catch (IOException ex) {
            ex.printStackTrace();
        }     
        }
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