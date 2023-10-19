package couche_acces_disque;

import java.io.Serializable;

public class PageId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int FileIdx;
	private int PageIdx;
	
	public PageId(int FileIdx, int PageIdx) {
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
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
