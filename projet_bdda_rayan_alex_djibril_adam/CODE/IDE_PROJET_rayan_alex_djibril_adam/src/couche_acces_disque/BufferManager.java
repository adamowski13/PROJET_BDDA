package couche_acces_disque;

import java.nio.*;
import java.util.*;

public class BufferManager {
	
	private List<Frame> listFrame;
	private PageId pageId;
	private static BufferManager instance;

	private BufferManager(){ 
		this.listFrame = new ArrayList<Frame>();
		this.pageId = new PageId();
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
	
	public PageId getPageId() {
		return pageId;
	}

	public ByteBuffer getPage(PageId pageId) {

		DiskManager disk = DiskManager.getInstance();
		ByteBuffer buff = listFrame.get(0).getBuffer() ;

		for(int i=0;i<listFrame.size();i++) {

			if(listFrame.get(i).getPageId().equals(pageId)) { // si la page existe déja
				buff = listFrame.get(i).getBuffer();

			}else if(listFrame.get(i).getPageId() == null){ // si la case est libre on uttilise la page

				listFrame.get(i).setPageId(pageId);
				listFrame.get(i).setFlag_dirty(1);
				listFrame.get(i).setPin_count(1);
				disk.readPage(pageId,listFrame.get(i).getBuffer());
				buff = listFrame.get(i).getBuffer();
			}
		}
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
