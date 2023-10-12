package couche_acces_disque;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.*;



public class DiskManager {
	

	private Stack<PageId> pageDesAllouee;
	private List<PageId> pageAllouee;
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
            pageAllouee.add(page);
            return page;
        } else {
        	File repertoire = new File(DBParams.DBPath);
            File[] fichiers = repertoire.listFiles();
            
            if (fichiers != null) {
                for (File f : fichiers) {
                	if(f.length()==0) {
                		int fileId = f.getName().charAt(1) - '0';  
                		PageId page = new PageId(fileId, x);
                		pageAllouee.add(page);
                		x++;
                		return page;
                	}else {
                		File minimum = fichiers[0];
                		for(int i=1; i<fichiers.length; i++) {
                			if(fichiers[i].length()<minimum.length()) {
                				minimum = fichiers[i];
                			}
                		}
                		int fileId = minimum.getName().charAt(1)-'0';
                		PageId page = new PageId(fileId, x);
                		pageAllouee.add(page);
                		x++;
                		return page;                		
                	}
                }
            }
        }
		return null;
    }
	
    public void ReadPage(PageId pageId, ByteBuffer buff) {
        String filePath= DBParams.DBPath + "/F" + pageId.getFileIdx() + ".data";
        try {
            RandomAccessFile fichier = new RandomAccessFile(filePath, "r");
        	long startPos = pageId.getPageIdx()*DBParams.SGBDPageSize;
        	fichier.seek(startPos);
            int bytesRead = fichier.read(buff.array());
            buff.limit(bytesRead);
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writePage(PageId pageId, ByteBuffer buff) {
        String filePath = DBParams.DBPath +"/F" + pageId.getFileIdx() + ".data";
        try {
        	RandomAccessFile fichier = new RandomAccessFile(filePath, "rw");
        	long startPos = pageId.getPageIdx()*DBParams.SGBDPageSize;
        	fichier.seek(startPos);
            fichier.write(buff.array(), 0, buff.limit());
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void DeallocPage(PageId pageId) {
    	for(int i=0; i<pageAllouee.size(); i++) {
    		if(pageId.equals(pageAllouee.get(i))) {
    			pageDesAllouee.push(pageId);
    			pageAllouee.remove(i);
    		}
    	}
    }
    
    public int GetCurrentCountAllocPages() {
    	return pageAllouee.size();
    }
    
}



