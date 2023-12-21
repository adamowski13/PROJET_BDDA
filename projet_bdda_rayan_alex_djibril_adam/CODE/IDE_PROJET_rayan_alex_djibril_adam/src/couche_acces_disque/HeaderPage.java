package couche_acces_disque;

import java.nio.ByteBuffer;

/**
 * Cette classe représente une page d'en-tête.
 */
public class HeaderPage {
    private ByteBuffer byteBuffer;
    
    /**
     * Constructeur de la classe HeaderPage.
     *
     * @param page L'identifiant de la page d'en-tête.
     */
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
