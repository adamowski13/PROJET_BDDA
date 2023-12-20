package couche_acces_disque;

import java.util.*;

/**
 * Ensemble d’informations spécifiques à une relation
 */
public class TableInfo {

	private int nbrColonne;
	private String nomRelation;
	private List<String> nomsColonne;
	private List<String> typesColonne;
	private PageId headerPageId;

	public TableInfo(String nomRelation, int nbrColonne, PageId page) {
		this.nbrColonne = nbrColonne;
		this.nomRelation = nomRelation;
		this.nomsColonne = new ArrayList<String>();
		this.typesColonne = new ArrayList<String>();
		this.headerPageId = page;
	}

	public PageId getHeaderPageId() {
		return headerPageId;
	}

	public void setHeaderPageId(PageId headerPageId) {
		this.headerPageId = headerPageId;
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
	
}
