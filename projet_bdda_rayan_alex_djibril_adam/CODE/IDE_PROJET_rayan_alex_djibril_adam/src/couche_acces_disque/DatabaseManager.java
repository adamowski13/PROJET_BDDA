package couche_acces_disque;

import java.util.Scanner;

/**
 *Manipulation des commande de la base de donnée
 */
public class DatabaseManager {
    private DatabaseInfo data;
    private BufferManager buff;
    
    /**
     * Constructeur de la classe DatabaseManager.
     * 
     * @param data Informations sur la base de données.
     * @param buff Gestionnaire de tampons.
     */
    public DatabaseManager(DatabaseInfo data, BufferManager buff) {
        this.data = data;
        this.buff = buff;
    }
    
    /**
     * Constructeur par défaut de la classe DatabaseManager.
     * Initialise les informations sur la base de données et le gestionnaire de tampons.
     */
    public DatabaseManager() {
        this.data = new DatabaseInfo(0);
        this.buff = BufferManager.getInstance();
    }
    
    /**
     * Initialise les informations sur la base de données et le gestionnaire de tampons.
     */
    public void init(){
        try {
            data.Init();
            buff = BufferManager.getInstance();
        } catch (Exception e) {
            System.out.println("Opération échouer");
        }
    }
    
    /**
     * Termine les opérations sur la base de données et vide tous les tampons.
     */
    public void Finish(){
        try{
            data.Finish();
            buff.flushAll();
        }catch(Exception e){
            System.out.println("Opération échouer");
        }
        
    }
    
    /**
     * Traite la commande spécifiée.
     * 
     * @param commande La commande à traiter.
     */
    public void ProcessCommande(String commande){
        Scanner sc = new Scanner(System.in);

        //commande qui sera appeler dans le constructeur des class de Commande
        String commandeUtil; 
        switch (commande) {
            case "CreatTable":                
                System.out.println("Tapez maintenant votre commande, elle doit être du type suivant :");
                System.out.println("CREATE TABLE NomRelation (NomCol_1:TypeCol_1,NomCol_2:TypeCol_2, ... NomCol_NbCol:TypeCol_NbCol)");
                commandeUtil = sc.next();
                CreateTableCommand creatTable = new CreateTableCommand(commandeUtil);
                creatTable.Excecute();
                break;
            
            case "Insert":
                System.out.println("Tapez maintenant votre commande, elle doit être du type suivant :");
                System.out.println("INSERT INTO nomRelation VALUES (val1,val2, ... ,valn)");
                commandeUtil = sc.next();
                break;
            
            case "Import":
                System.out.println("");
                
                break;
        
            default: 
                break;
        }

        sc.close();
    }
}
