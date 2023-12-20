package couche_acces_disque;

/**
 *Manipulation des commande de la base de donnée
 */
public class DatabaseManager {
    private DatabaseInfo data;
    private BufferManager buff;

    public DatabaseManager(DatabaseInfo data, BufferManager buff) {
        this.data = data;
        this.buff = buff;
    }

    public DatabaseManager() {
        this.data = new DatabaseInfo(0);
        this.buff = BufferManager.getInstance();
    }

    public void init(){
        data.Init();
        buff = BufferManager.getInstance();
    }

    public void Finish(){
        data.Finish();
        buff.flushAll();
    }

    public void ProcessCommande(String commande){
        //vide pour l'instant
        switch (commande) {
            case "CreatTable":
                System.out.println("");
                
                break;
            
            case "Insert":
                System.out.println("");
                
                break;
            
            case "Import":
                System.out.println("");
                
                break;
        
            default: 
                break;
        }
    }
}
