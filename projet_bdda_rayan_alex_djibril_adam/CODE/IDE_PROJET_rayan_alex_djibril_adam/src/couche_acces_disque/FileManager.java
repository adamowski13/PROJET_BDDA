package couche_acces_disque;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Cette classe gère les opérations liées au système de fichiers.
 */
public class FileManager {

	/**
     * Crée une nouvelle page d'en-tête et l'initialise.
     *
     * @return L'identifiant de la nouvelle page d'en-tête.
     */
    public PageId createNewHeaderPage(){
        DiskManager.initialize();
        DiskManager disc = DiskManager.getInstance();
        PageId newPage = disc.allocPage();
        BufferManager buff = BufferManager.getInstance();
        HeaderPage header = new HeaderPage(newPage);
        header.getByteBuffer().rewind();
        header.getByteBuffer().put(buff.getPage(newPage));
        header.getByteBuffer().putInt(0);
        header.getByteBuffer().putInt(-1);
        header.getByteBuffer().putInt(0);
        header.getByteBuffer().putInt(-1);
        buff.freePage(newPage, 0);
        return newPage;
    }

    /**
     * Ajoute une nouvelle page de données à l'en-tête spécifiée.
     *
     * @param tabinfo Les informations de la table.
     * @param header L'en-tête de la table.
     * @return L'identifiant de la nouvelle page de données.
     */
    public PageId addDataPage(TableInfo tabinfo, HeaderPage header){
        DiskManager.initialize();
        DiskManager disc = DiskManager.getInstance();
        BufferManager buff = BufferManager.getInstance();
        PageId newPage = disc.allocPage();

        header.getByteBuffer().rewind();
        header.getByteBuffer().put(buff.getPage(newPage));

        disc.readPage(newPage, header.getByteBuffer());
        tabinfo.setHeaderPageId(newPage);
        buff.freePage(tabinfo.getHeaderPageId(), 1);
        return newPage;
    }
    
    /**
     * Obtient l'identifiant d'une page de données libre.
     *
     * @param tableInfo Les informations de la table.
     * @param sizeRecord La taille du record.
     * @return L'identifiant de la nouvelle page de données.
     */
    public PageId getFreeDataPageId(TableInfo tableInfo, int sizeRecord){
        PageId newPage = new PageId();
        newPage = null;
        for(int i=0;i<sizeRecord;i++){
            if(tableInfo.getHeaderPageId().getPageIdx() == 0){
                if(tableInfo.getNbrColonne() <=4){
                    newPage = tableInfo.getHeaderPageId();
                }
            }else if(tableInfo.getHeaderPageId().getPageIdx() == 1){
                if(tableInfo.getNbrColonne() <=4){
                    newPage = tableInfo.getHeaderPageId();
                }
            }else if(tableInfo.getHeaderPageId().getPageIdx() == 2){
                if(tableInfo.getNbrColonne() <=4){
                    newPage = tableInfo.getHeaderPageId();
                }
            }else if(tableInfo.getHeaderPageId().getPageIdx() == 3){
                if(tableInfo.getNbrColonne() <=4){
                    newPage = tableInfo.getHeaderPageId();
                }
            }
        }
        return newPage;
        
    }
    
    /**
     * Écrit un record dans une page de données spécifiée.
     *
     * @param record Le record à écrire.
     * @param pageId L'identifiant de la page de données.
     * @return L'identifiant du record écrit.
     */
    public RecordId writeRecordToDataPage(Record record, PageId pageId){
        DiskManager.initialize();
        DiskManager disc = DiskManager.getInstance();
        BufferManager buff = BufferManager.getInstance();
        ByteBuffer bytebuffer = ByteBuffer.allocate(1080);
        record.getRelInfo().setHeaderPageId(pageId);
        int Pos1 = record.readFromBuffer(bytebuffer, 0);
        record.writeToBuffer(bytebuffer, Pos1);
        RecordId newRecord = new RecordId(record.getRelInfo().getHeaderPageId());
        buff.freePage(pageId, 1);
        disc.getPageAllouee().add(pageId);
        return newRecord;
    }

    /**
     * Obtient la liste des records dans une page de données spécifiée.
     *
     * @param tableInfo Les informations de la table.
     * @param pageId L'identifiant de la page de données.
     * @return La liste des records dans la page de données.
     */
    public ArrayList<Record> getRecordsInDataPage(TableInfo tableInfo, PageId pageId){
        ArrayList<Record> listeDeRecords = new ArrayList<Record>();
        Record recordPage = new Record(tableInfo);
        BufferManager buff = BufferManager.getInstance();
        ByteBuffer bbuffer = buff.getPage(pageId);
        int size = bbuffer.getInt(DBParams.SGBDPageSize - Integer.BYTES * 2); // taille
        for(int i=0;i<size;i++){
            int newSize = bbuffer.getInt(DBParams.SGBDPageSize - Integer.BYTES * 2) -i*Integer.BYTES*2;
            int newPosition = recordPage.readFromBuffer(bbuffer, newSize);
            Record newRecord = new Record(tableInfo);
            newRecord.readFromBuffer(bbuffer, newPosition);
            listeDeRecords.add(recordPage);
        }
        buff.freePage(pageId, 1);
        return listeDeRecords;
    }

    /**
     * Obtient la liste des pages de données associées à une table.
     * fonction non terminer..
     *
     * @param tabinfo Les informations de la table.
     * @return La liste des identifiants de pages de données.
     */
    public ArrayList<PageId> getDataPages(TableInfo tabinfo){
        ArrayList<PageId> listeDePageId = new ArrayList<PageId>();
        BufferManager buff = BufferManager.getInstance();
        PageId newPage = new PageId();
        HeaderPage header = new HeaderPage(tabinfo.getHeaderPageId());

        return listeDePageId;
    }
    
    /**
     * Insère un record dans une table.
     *
     * @param record Le record à insérer.
     * @return L'identifiant du record inséré.
     */
    public RecordId InsertRecordIntoTable(Record record){
        return new RecordId(record.getRelInfo().getHeaderPageId());
    }
    
    /**
     * Obtient tous les records d'une table.
     *
     * @param tabinfo Les informations de la table.
     * @return La liste de tous les records dans la table.
     */
    public ArrayList<Record> GetAllRecords(TableInfo tabinfo){
        ArrayList<Record> listeDeRecords = new ArrayList<Record>();
        ArrayList<Record> listeRecordsInPage = new ArrayList<Record>();
        ArrayList<PageId> listePageId = new ArrayList<PageId>();
        listePageId = getDataPages(tabinfo);

        for(int i=0;i<listePageId.size();i++){
            listeRecordsInPage = getRecordsInDataPage(tabinfo,listePageId.get(i));
            for(int j =0;j<listeRecordsInPage.size();j++){
                listeDeRecords.add(listeRecordsInPage.get(j));
            }
        }

        return listeDeRecords;
    }


}
