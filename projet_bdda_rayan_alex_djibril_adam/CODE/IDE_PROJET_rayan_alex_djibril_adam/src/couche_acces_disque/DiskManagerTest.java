package couche_acces_disque;

import java.nio.*;
import java.util.*;

public class DiskManagerTest {
    public static void main(String []args){
    	//On ne peut pas encore effectuer de test
    	//Les méthodes Write et Read doivent prendre des PageId et pas des int
    	//Le chemin dans AllocPage ne marche que sur l'ordinateur de rayan
    	//Initialisation diskmanager
    	Stack<PageId> stack = new Stack<PageId>();
    	List<PageId> liste = new ArrayList<PageId>();
        DiskManager dm = new DiskManager(stack,liste);
        ByteBuffer b1 = ByteBuffer.allocate(4096);
        //alloue une page
        PageId pi1 = dm.AllocPage();
        System.out.println("Test ecriture : aaa");
        //écrit sur la page allouée
        TestEcriturePage(pi1, b1, dm);
        ByteBuffer b2 = ByteBuffer.allocate(4096);
        System.out.println("Test Lecture : Attendu : aaa");
        //lit la page dans un buffer
        TestLecturePage(pi1, b2, dm);
        //transforme le buffer en charbuffer pour l'afficher en string
        CharBuffer charBuffer = b2.asCharBuffer();
        System.out.println(charBuffer.toString());
    }

    public static void TestEcriturePage(PageId pi, ByteBuffer buff, DiskManager dm){
    	String s = "aaa";
    	buff.put(s.getBytes());
        dm.WritePage(pi, buff);
    }

    public static void TestLecturePage(PageId pi, ByteBuffer buff, DiskManager dm){
        dm.ReadPage(pi, buff);
    }

}