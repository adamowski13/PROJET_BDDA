package couche_acces_disque;

import java.nio.ByteBuffer;
import java.util.*;

public class Record {
    private TableInfo relInfo;
    private ArrayList<String> recvalues;

    public Record(TableInfo relInfo){
        this.relInfo=relInfo;
        this.recvalues = new ArrayList<String>();
    }
    
    /**
     * Cette méthode doit écrire les valeurs du Record dans le buffer en partant de la position pos, 
     * en utilisant le format à taille fixe ou variable tel que décrit ci-dessus.
     * @param buff le buffer dans lequel on veut ecrir les valeurs du record
     * @param pos position de départ pour la retranscription des donneées dans le buff
     * @return le nombre d'octet total écris dans le buffer
     */
    public int writeToBuffer(ByteBuffer buff, int pos) {
        int oldPosition= pos;
        buff.position(pos);
        for (int i = 0; i < recvalues.size(); i++) {
            if (relInfo.getTypesColonne().get(i).equals("int")) {
                buff.putInt(Integer.parseInt(recvalues.get(i)));
            } 
            else if (relInfo.getTypesColonne().get(i).equals("float")) {
                buff.putFloat(Float.parseFloat(recvalues.get(i)));
            } 
            else if (relInfo.getTypesColonne().get(i).equals("String")||relInfo.getTypesColonne().get(i).equals("Varstring")){
                for(int j = 0; j<recvalues.get(i).length(); j++){
                    buff.putChar(recvalues.get(i).charAt(j));
                }
            }
        }
        int taille = buff.position() - oldPosition;
        return taille;    
    }

    /**
     * Cette méthode devra lire les valeurs du Record depuis le buffer à partir de pos.
     * @param buff le buffer depuis lequel on veux lire les valeur du recode
     * @param pos la position de départ de lecture
     * @return le nombre total d'octet lu depuis le buffer
     */
    public int readFromBuffer(ByteBuffer buff, int pos) {
        int oldPosition = pos;

        buff.position(pos);
        for (int i = 0; i < relInfo.getNbrColonne(); i++) 
        {
            int stringLength;
            if (relInfo.getTypesColonne().get(i).equals("int")) {
                recvalues.add(String.valueOf(buff.getInt()));
            } 
            else if (relInfo.getTypesColonne().get(i).equals("float")) 
            {
                    recvalues.add(String.valueOf(buff.getFloat()));
            }
            else if (relInfo.getTypesColonne().get(i).equals("String")||relInfo.getTypesColonne().get(i).equals("Varstring"))
            {
                stringLength = Integer.parseInt(relInfo.getTypesColonne().get(i));
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < stringLength; j++) {
                    sb.append(buff.getChar());
                }
                recvalues.add(sb.toString());
            }
        }  
        int taille = buff.position() - oldPosition;
        return taille;
    }

    public TableInfo getRelInfo() {
        return relInfo;
    }
    public ArrayList<String> getRecvalues() {
        return recvalues;
    }
    public void setRelInfo(TableInfo relInfo) {
        this.relInfo = relInfo;
    }
        
}


