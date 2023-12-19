package couche_acces_disque;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManager {

    //pour la prochaine fois..
    //on gère ici l'allocation d'une nouvelle page avec allocpage du Disckmanager
    //on return le pageId rendu par le AllocPage
    //il faut uttiliser un liste chainer avec deux pages ou ya rien avant et apres la page du allocPage en gros
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

    //finir cette methode
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


    //a finir en gros vrm la j'abuse 
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

    
    public ArrayList<PageId> getDataPages(TableInfo tabinfo){
        ArrayList<PageId> listeDePageId = new ArrayList<PageId>();
        BufferManager buff = BufferManager.getInstance();
        PageId newPage = new PageId();
        HeaderPage header = new HeaderPage(tabinfo.getHeaderPageId());

        return listeDePageId;
    }
    

    public RecordId InsertRecordIntoTable(Record record){
        return new RecordId(record.getRelInfo().getHeaderPageId());
    }

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

    //insert et import important le reste on s'en tappe en vrais

}
