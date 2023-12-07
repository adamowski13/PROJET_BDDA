package couche_acces_disque;

import java.nio.*;
import java.util.*;

public class BufferManager {
	
	private List<Frame> listFrame;
	private static BufferManager instance;

	private BufferManager(){ 
		this.listFrame = new ArrayList<Frame>();
		for(int i = 0;i<DBParams.FrameCount;i++) {
			listFrame.add(new Frame());
		}
	}

	public static BufferManager getInstance(){
		if(instance == null){
			instance = new BufferManager();
		}
		return instance;
	}
	
	public ByteBuffer getPage(PageId pageId) {
		DiskManager disk = DiskManager.getInstance();
		ByteBuffer buff = ByteBuffer.allocate(pageId.getPageIdx()) ;
		for(int i=0;i<listFrame.size();i++) {
			if(listFrame.get(i).getPageId().equals(pageId)) {
				return listFrame.get(i).getBuffer();
			}
		}
		disk.readPage(pageId,buff);
		return buff;
	}
	
	public void freePage(PageId pageId, int valdirty) {
		for(int i = 0; i<listFrame.size();i++) {
			if(listFrame.get(i).getPageId().equals(pageId)) {
				if(listFrame.get(i).getPageId()!=null && listFrame.get(i).getPin_count() == 0) {
					System.out.println("La page est déjà libre");
				}
				else {
					Frame framefree = listFrame.get(i);
					framefree.setPin_count(framefree.getPin_count()-1);
					framefree.setFlag_dirty(valdirty);
				}
			}    

		}
		System.out.println("Erreur, page non trouvée dans le BufferManager");
	}
	
	//effectue l’écriture de toutes les pages dont le flag dirty = 1 sur disque
	//la remise à 0 de tous les flags/informations et contenus des buffers (buffer pool « vide »)
	public void flushAll() {
		DiskManager disk = DiskManager.getInstance();
		for(int i = 0; i<listFrame.size();i++) {
			if(listFrame.get(i).getFlag_dirty() == 1) {
				disk.writePage(listFrame.get(i).getPageId(), listFrame.get(i).getBuffer());
				listFrame.get(i).reinitFrame();
			}
		}
	}
	
}
