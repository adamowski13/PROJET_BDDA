package couche_acces_disque;

import java.nio.ByteBuffer;

/**
 * Cette classe représente un itérateur pour parcourir les enregistrements d'une page de données.
 */
public class RecordIterator {
    private TableInfo tabinfo;
    private PageId pageId;
    private BufferManager buff;
    private FileManager file;
    private ByteBuffer datapge;
    private static int index;
    
    /**
     * Constructeur de la classe RecordIterator.
     *
     * @param tabinfo Informations sur la table associée à l'itérateur.
     * @param pageId  Identifiant de la page de données à parcourir.
     */
    public RecordIterator(TableInfo tabinfo, PageId pageId) {
        this.tabinfo = tabinfo;
        this.pageId = pageId;
        buff  = BufferManager.getInstance();
        datapge = buff.getPage(pageId);
        file = new FileManager();
        datapge.position(0);
        index = -1;
    }
    
    public Record GetNextRecord(){
        Record record = file.getRecordsInDataPage(tabinfo, pageId).get(index);
        index ++;
        return record;
    }

    public void close(){
        buff.freePage(pageId, 1);
    }

    public void Reset(){
        index = -1;
        datapge.position(0);
    }
}
