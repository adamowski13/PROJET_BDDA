package couche_acces_disque;

import java.nio.*;
import java.nio.charset.Charset;

public class DiskManagerTest {
    public static void main(String []args){
    	String filePath = "/home/alex/git/PROJET_BDDA/projet_bdda_rayan_alex_djibril_adam/DB";
    	DBParams.DBPath = filePath;
    	DBParams.DMFileCount = 4;
    	DBParams.SGBDPageSize = 7;
    	DiskManager.initialize();
        DiskManager dm = DiskManager.getInstance();
        System.out.println(dm.GetCurrentCountAllocPages());
        ByteBuffer b1 = ByteBuffer.allocate(DBParams.SGBDPageSize);
        //alloue une page
        PageId pi1 = dm.AllocPage();
        System.out.println("file id: "+ pi1.getFileIdx()+"page id: "+ pi1.getPageIdx());
        System.out.println("Test ecriture : ababab");
        //écrit sur la page allouée
        TestEcriturePage(pi1, b1, dm);
        ByteBuffer b2 = ByteBuffer.allocate(DBParams.SGBDPageSize);
        System.out.println("Test Lecture : Attendu : ababab");
        //lit la page dans un buffer
        TestLecturePage(pi1, b2, dm);
        //transforme le buffer en charbuffer pour l'afficher en string
        //CharBuffer charBuffer = b2.asCharBuffer();
        CharBuffer charBuffer = Charset.forName("UTF-8").decode(b2);
        System.out.println(charBuffer.toString());
        DiskManager.persist();
    }

    public static void TestEcriturePage(PageId pi, ByteBuffer buff, DiskManager dm){
    	String s = "ababab";
    	buff.clear();
    	buff.put(s.getBytes());
        dm.WritePage(pi, buff);
    }

    public static void TestLecturePage(PageId pi, ByteBuffer buff, DiskManager dm){
        dm.ReadPage(pi, buff);
    }

}