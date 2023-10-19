package couche_acces_disque;

import java.nio.*;
import java.util.*;

public class BufferManager {
	
	private List<Frame> listFrame;

	private BufferManager(){
		this.listFrame = new ArrayList<Frame>();
		for(int i = 0;i<DBParams.FrameCount;i++) {
			listFrame.add(new Frame());
		}
	}
	
	public ByteBuffer getPage(PageId pageId) {
		
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
	
	public void flushAll() {
		for(int i = 0; i<listFrame.size();i++) {
			if(listFrame.get(i).getFlag_dirty() == 1) {
				DiskManager.writePage(listFrame.get(i).getPageId(), listFrame.get(i).getBuffer());
				listFrame.get(i).reinitFrame();
			}
		}
	}

	
}
