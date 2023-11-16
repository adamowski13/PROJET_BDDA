package couche_acces_disque;

import java.util.*;

public class TableInfo {

	private int nbrColonne;
	private String nomRelation;
	private List<String> nomsColonne;
	private List<String> typesColonne;
	
	public TableInfo(String nomRelation, int nbrColonne) {
		this.nbrColonne = nbrColonne;
		this.nomRelation = nomRelation;
		this.nomsColonne = new ArrayList<String>();
		this.typesColonne = new ArrayList<String>();
	}

	public String getNomRelation() {
		return nomRelation;
	}

	public int getNbrColonne() {
		return nbrColonne;
	}

	public List<String> getTypesColonne() {
		return typesColonne;
	}
	
	public List<String> getNomsColonne(){
		return nomsColonne;
	}
	
//test commit
}
