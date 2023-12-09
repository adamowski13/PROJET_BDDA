package couche_acces_disque;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.*;



public class DiskManager implements Serializable{

	private static final long serialVersionUID = 1L;
	private Stack<PageId> pageDesAllouee;
	private List<PageId> pageAllouee;
	private int [] pageFichier;
	private File[] fichiers;
	private static DiskManager instance;
	
	private DiskManager() {
		this.pageDesAllouee = new Stack<PageId>();
		this.pageAllouee = new ArrayList<PageId>();
		pageFichier = new int[4];
		pageFichier[0]=0;
		pageFichier[1]=0;
		pageFichier[2]=0;
		pageFichier[3]=0;
		File repertoire = new File(DBParams.DBPath);
		fichiers = repertoire.listFiles();
	}
	
	public static DiskManager getInstance() {
		if(instance == null) { 
			instance = new DiskManager();
		}
		return instance;
	}
    
    /*Cette méthode doit allouer une page, c’est à dire réserver une nouvelle page à la demande d’une des couches au-dessus. */
    public PageId allocPage() {
        if (!pageDesAllouee.isEmpty()) {
            PageId page = (PageId) pageDesAllouee.pop();
            System.out.println("Page allocated: " + page);
            pageAllouee.add(page);
            return page;
        } else {
            if (fichiers != null) {
                for (File f : fichiers) {
                	if(f.length()==0) {
                		int fileId = f.getName().charAt(1) - '0'; //charAt() donne un char, un char converti en int donne le code ascii du char. On soustrait ´0'pour obtenir le nombre 
                		PageId page = new PageId(fileId, pageFichier[fileId]);
                		pageAllouee.add(page);
                		pageFichier[fileId] +=1;
                		return page;
                	}else {
                		File minimum = fichiers[0];
                		for(int i=1; i<fichiers.length; i++) {
                			if(fichiers[i].length()<minimum.length()) {
                				minimum = fichiers[i];
                			}
                		}
                		int fileId = minimum.getName().charAt(1)-'0';
                		PageId page = new PageId(fileId, pageFichier[fileId]);
                		pageAllouee.add(page);
                		pageFichier[fileId] += 1;
                		return page;                		
                	}
                }
            }
        }
		return null;
    }
	
    /*Cette méthode doit remplir l’argument buff avec le contenu disque de la page identifiée par l’argument pageId */
    public void readPage(PageId pageId, ByteBuffer buff) {
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
    
    /*Cette méthode écrit le contenu de l’argument buff dans le fichier et à la position indiqués par l’argument pageId. */
    public void writePage(PageId pageId, ByteBuffer buff) {
        String filePath = DBParams.DBPath +"/F" + pageId.getFileIdx() + ".data";
        try {
        	RandomAccessFile fichier = new RandomAccessFile(filePath, "rw");
        	long startPos = pageId.getPageIdx()*DBParams.SGBDPageSize;
        	fichier.seek(startPos);
        	buff.flip();
            fichier.write(buff.array(), 0, buff.limit());
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*Cette méthode doit désallouer une page, et la rajouter dans la liste des pages « disponibles » */
    public void deallocPage(PageId pageId) {
    	for(int i=0; i<pageAllouee.size(); i++) {
    		if(pageId.equals(pageAllouee.get(i))) {
    			pageDesAllouee.push(pageId);
    			pageAllouee.remove(i);
    		}
    	}
    }
    
//https://stackoverflow.com/questions/8361301/how-to-read-and-rewrite-a-singleton-object-from-a-file
    public static void persist(){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream("Storage.data");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance.pageDesAllouee);
            oos.writeObject(instance.pageAllouee);
            oos.writeObject(instance.pageFichier);
            oos.writeObject(instance.fichiers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void initialize(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream("Storage.data");
            ois = new ObjectInputStream(fis);
            instance = DiskManager.getInstance();
            instance.pageDesAllouee = (Stack<PageId>) ois.readObject();
            instance.pageAllouee = (List<PageId>) ois.readObject();
            instance.pageFichier = (int[]) ois.readObject();
            instance.fichiers = (File[]) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void reset() {
    	instance = new DiskManager();
    }
    
    public int getCurrentCountAllocPages() {
    	return pageAllouee.size();
    }
    
}
