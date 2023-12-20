package couche_acces_disque;

import java.io.Serializable;

/**
 * Identifiant des pages
 */
public class PageId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Identifiant du fichier
	 */
	private int FileIdx;
	
	/**
	 * Indice de la page dans le fichier
	 */
	private int PageIdx;
	
	public PageId(int FileIdx, int PageIdx) {
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
	}
	
	public PageId() {
		this(0, 0);
	}

	public int getFileIdx() {
		return FileIdx;
	}

	public void setFileIdx(int fileIdx) {
		FileIdx = fileIdx;
	}

	public int getPageIdx() {
		return PageIdx;
	}

	public void setPageIdx(int pageIdx) {
		PageIdx = pageIdx;
	}

	@Override
	public String toString() {
		return "PageId [FileIdx=" + FileIdx + ", PageIdx=" + PageIdx + "]";
	}
	

}
