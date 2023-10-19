package couche_acces_disque;

public class TableInfo {
		private String nomRel;
		private int nbCol;
		private ColInfo[] colInfo;
		
		public TableInfo(String nomRel,int nbCol ){
			this.nomRel = nomRel;
			this.nbCol = nbCol;
			colInfo = new ColInfo[nbCol];
		}
		
		
		
		
}
