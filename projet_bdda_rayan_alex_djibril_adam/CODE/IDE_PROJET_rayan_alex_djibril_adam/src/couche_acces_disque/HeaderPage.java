package couche_acces_disque;

import java.nio.ByteBuffer;

public class HeaderPage {
    private ByteBuffer byteBuffer;

    public HeaderPage(PageId page) {
        this.byteBuffer = ByteBuffer.allocate(page.getPageIdx());
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
