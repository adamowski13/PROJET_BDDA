package couche_acces_disque;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.*;

public class DiskManager {
	
	private int totalPage;
	private int freePage;

	
	//constructeur
	public DiskManager() {
		
	}
	
    public PageId AllocPage(Stack<PageId> PageLibre, Stack<PageId> PageOccup) {
        if (!PageLibre.isEmpty()) {
            PageId page = PageLibre.pop();
            PageOccup.push(page);
            System.out.println("Page allocated: " + page);
            totalPage++;
            return page;
        } else {
        	/*Sinon, rajouter une page (c’est à dire, rajouter pageSize octets, avec une valeur
        	quelconque, à la fin du fichier) dans le fichier de la plus petite taille disponible 
        	(les fichiers qui n’ont pas encore des pages rajoutées ont une taille 0, que vous les ayez créés ou pas,
         	donc sont à considérer en priorité!) */
        	totalPage++;
        	return null;
        }

    }
	
    public void ReadPage(int pageId, ByteBuffer buff) {
        try {
            String fileName = "F" + pageId + ".data"; 
            RandomAccessFile file = new RandomAccessFile(fileName, "r");

            long position = (long) pageId * buff.capacity();

            file.seek(position);

            byte[] bufferArray = new byte[buff.capacity()];
            int bytesRead = file.read(bufferArray);

            if (bytesRead != -1) {
                buff.clear();
                buff.put(bufferArray, 0, bytesRead);
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void WritePage(int pageId, ByteBuffer buff) {
        try {
            String fileName = "F" + pageId + ".data"; 
            RandomAccessFile file = new RandomAccessFile(fileName, "w");
            //remplir le buff
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void DeallocPage(PageId pageId, Stack<PageId> PageLibre, Stack<PageId> PageOccup) {
    	if(!PageOccup.empty()) {
	    	pageId = PageOccup.pop();
	    	PageLibre.push(pageId);
    	}
    	else {
    		System.out.println("Toutes les pages sont libres");
    	}
    	freePage++;
    }
    
    public int GetCurrentCountAllocPages() {
    	return this.totalPage - this.freePage;
    }
    
}



