package couche_acces_disque;

import java.nio.*;
import java.util.*;

/**
 *Cette classe forme l'API du gestionnaire disque (elle sera appelé par les couches plus hautes)
 *Gestionnaire du tampon
 */
public class BufferManager {
	
	/**
	 * partition de la memoire disponible en un enssemble de case
	 */
	private List<Frame> listFrame;
	
	/**
	 * Indentifiant de la page
	 */
	private PageId pageId;
	
	/**
	 * Instance de BufferManager
	 */
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

	/**
	 * Cette méthode doit répondre à une demande de page venant des couches plus hautes, et donc retourner un des buffers associés à une frame.
	 * Le buffer sera rempli avec le contenu de la page désignée par l’argument pageId
	 * @param pageId l'identifiant de la page à partire de laquelle on rempli le buffer
	 * @return le buffer rempli
	 */
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
	
	/**
	 * Cette méthode devra décrémenter le pin_count et actualiser le flag dirty de la page 
	 * (et aussi potentiellement actualiser des informations concernant la politique de remplacement)
	 * @param pageId l'identifiant de la page pour laquelle on veux décrémenter le pin_count et actualiser le flag dirty
	 * @param valdirty valeur du nouveau flag dirty
	 */
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
	
	/**
	 * l’écriture de toutes les pages dont le flag dirty = 1 sur disque et la remise à 0 de tous 
	 * les flags/informations et contenus des buffers (buffer pool « vide »)
	 */
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
