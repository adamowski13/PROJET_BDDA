package couche_acces_disque;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Record {
    private TableInfo relInfo;
    private ArrayList<String> recvalues;

    public Record(TableInfo relInfo){
        this.relInfo=relInfo;
        this.recvalues = new ArrayList<>();
    }
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
        
}


