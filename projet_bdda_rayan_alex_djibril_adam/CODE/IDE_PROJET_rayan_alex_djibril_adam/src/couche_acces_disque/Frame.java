package couche_acces_disque;

import java.nio.*;

public class Frame {
	
	private ByteBuffer buffer;
	private PageId pageId;
	private int pin_count;
	private int flag_dirty;
	
	public Frame() {
		this.pin_count = 0;
		this.flag_dirty = 0;
		this.pageId = null;
		buffer = null;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	public PageId getPageId() {
		return pageId;
	}

	public void setPageId(PageId pageId) {
		this.pageId = pageId;
	}

	public int getPin_count() {
		return pin_count;
	}

	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}

	public int Flag_dirty() {
		return flag_dirty;
	}

	public void setFlag_dirty(int flag_dirty) {
		this.flag_dirty = flag_dirty;
	}
	
	
	
}
