package couche_acces_disque;

import java.util.ArrayList;
import java.util.List;

public class Record {
    private TableInfo tabInfo;
    private List<String> recvalues;

    public Record(TableInfo tabInfo){
        this.tabInfo=tabInfo;
        recvalues = new ArrayList<>(null);
    }

}
