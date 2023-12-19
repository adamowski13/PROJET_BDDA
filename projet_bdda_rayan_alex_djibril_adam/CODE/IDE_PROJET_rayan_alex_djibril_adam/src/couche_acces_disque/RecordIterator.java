package couche_acces_disque;

import java.nio.ByteBuffer;

public class RecordIterator {
    private TableInfo tabinfo;
    private PageId pageId;
    private BufferManager buff;
    private FileManager file;
    private ByteBuffer datapge;
    private static int index;

    //peut être des petit soucis ici on verra..
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
