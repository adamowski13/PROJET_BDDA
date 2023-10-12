package couche_acces_disque;

import java.util.*;

public class BufferManager {
	
	private List<Frame> listFrame;

	private BufferManager(){
		this.listFrame = new ArrayList<Frame>();
		for(int i = 0;i<DBParams.FrameCount;i++) {
			listFrame.add(new Frame());
		}
	}

	
}
