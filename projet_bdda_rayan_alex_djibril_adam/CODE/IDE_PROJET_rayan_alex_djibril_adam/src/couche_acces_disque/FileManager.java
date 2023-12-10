package couche_acces_disque;

import java.nio.ByteBuffer;

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
        PageId fact1 = new PageId(-1,0);
        PageId fact2 = new PageId(-1,0);
        disc.writePage(fact1, header.getByteBuffer());
        disc.writePage(fact2, header.getByteBuffer());
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
        TableInfo table =new TableInfo(null, 0, newPage);
        table.setHeaderPageId(newPage);
        return newPage;
    }

    public PageId getFreeDataPageId(TableInfo tableInfo, int sizeRecord){
        PageId newPage = new PageId();
        if(tableInfo.getHeaderPageId().getPageIdx() == 0){
            if(tableInfo.getNbrColonne() <=4){
                newPage = tableInfo.getHeaderPageId();
            }
            return newPage;
        }else if(tableInfo.getHeaderPageId().getPageIdx() == 1){
            if(tableInfo.getNbrColonne() <=4){
                newPage = tableInfo.getHeaderPageId();
            }
            return newPage;
        }else if(tableInfo.getHeaderPageId().getPageIdx() == 2){
            if(tableInfo.getNbrColonne() <=4){
                newPage = tableInfo.getHeaderPageId();
            }
            return newPage;
        }else if(tableInfo.getHeaderPageId().getPageIdx() == 3){
            if(tableInfo.getNbrColonne() <=4){
                newPage = tableInfo.getHeaderPageId();
            }
            return newPage;
        }else{
            return null;
        }
    }

    public RecordId writeRecordToDataPage(Record record, PageId pageId){
        ByteBuffer buff = ByteBuffer.allocate(1080);
        int Pos1 = record.readFromBuffer(buff, 0);
        int Pos2 = record.writeToBuffer(buff, Pos1);
        RecordId newRecord = new RecordId(pageId);
        return newRecord;
    }

}
