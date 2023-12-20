package couche_acces_disque;

/**
 * Décrit l'Id d'un record
 */
public class RecordId {
    private PageId pageId;
    private int slotIdx;


    public RecordId(PageId pageId) {
        this.pageId = pageId;
        this.slotIdx = 0;
    }

    public PageId getPageId() {
        return pageId;
    }


    public void setPageId(PageId pageId) {
        this.pageId = pageId;
    }


    public int getSlotIdx() {
        return slotIdx;
    }


    public void setSlotIdx(int slotIdx) {
        this.slotIdx = slotIdx;
    }
    
}
