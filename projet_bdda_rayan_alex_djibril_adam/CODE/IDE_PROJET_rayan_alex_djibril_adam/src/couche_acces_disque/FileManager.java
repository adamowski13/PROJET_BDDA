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
        ByteBuffer bytebuffer = ByteBuffer.allocate(newPage.getPageIdx()) ;
        bytebuffer = buff.getPage(newPage);
        PageId fact1 = new PageId(-1,0);
        PageId fact2 = new PageId(-1,0);
        disc.writePage(fact1, bytebuffer);
        disc.writePage(fact2, bytebuffer);
        buff.freePage(newPage, 0);
        return newPage;
    }

    public PageId addDataPage(TableInfo tabinfo){
        DiskManager.initialize();
        DiskManager disc = DiskManager.getInstance();
        BufferManager buff = BufferManager.getInstance();
        PageId newPage = disc.allocPage();

        ByteBuffer bytebuffer = ByteBuffer.allocate(newPage.getPageIdx());
        bytebuffer = buff.getPage(newPage);

        return newPage;
    }

}
