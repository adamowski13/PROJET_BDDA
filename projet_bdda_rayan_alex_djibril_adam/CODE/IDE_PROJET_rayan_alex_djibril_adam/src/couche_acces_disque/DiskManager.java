package couche_acces_disque;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.*;



public class DiskManager {
	

	private Stack<PageId> pageDesAllouee = new Stack<PageId>();
	private List<PageId> pageAllouee = new ArrayList<PageId>();
	private int x;
	
	
	public DiskManager(Stack<PageId> pageDesAllouee, List<PageId> pageAllouee) {
		this.pageDesAllouee  = pageDesAllouee;
		this.pageAllouee = pageAllouee;
		this.x = 0;
	}
	
    public PageId AllocPage() {
        if (!pageDesAllouee.isEmpty()) {
            PageId page = (PageId) pageDesAllouee.pop();
            System.out.println("Page allocated: " + page);
            return page;
        } else {
        	File repertoire = new File("/Users/rayanalmohaize/Desktop/depotGIT/PROJET_BDDA/projet_bdda_rayan_alex_djibril_adam/DB");
            File[] fichiers = repertoire.listFiles();
            
            if (fichiers != null) {
                for (File f : fichiers) {
                	if(f.length()==0) {
                		PageId page = new PageId(f.getName().charAt(1), x);
                		x++;
                		return page;
                	}
                	else {
                		File minimum = fichiers[0];
                		for(int i=1; i<fichiers.length; i++) {
                			if(fichiers[i].length()<minimum.length()) {
                				minimum = fichiers[i];
                			}
                		}
                		PageId page = new PageId(minimum.getName().charAt(1), x);
                		x++;
                		return page;                		
                	}
                }
            }
        }
		return null;
    }
	
    public void ReadPage(int pageId, ByteBuffer buff) {
        String fileName = "F" + pageId + ".data";
        try {
            RandomAccessFile fichier = new RandomAccessFile(fileName, "r");
            int bytesRead = fichier.read(buff.array());
            buff.limit(bytesRead);
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void WritePage(int pageId, ByteBuffer buff) {
        String fileName = "F" + pageId + ".data";
        try {
        	RandomAccessFile fichier = new RandomAccessFile(fileName, "rw");
            fichier.write(buff.array(), 0, buff.position());
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void DeallocPage(PageId pageId) {
    	for(int i=0; i<pageAllouee.size(); i++) {
    		if(pageId.equals(pageAllouee.get(i))) {
    			pageDesAllouee.push(pageId);
    		}
    	}
    }
    
    public int GetCurrentCountAllocPages() {
    	return pageAllouee.size();
    }
    
}



