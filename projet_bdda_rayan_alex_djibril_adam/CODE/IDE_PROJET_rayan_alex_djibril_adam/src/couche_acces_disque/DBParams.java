package couche_acces_disque;

/**
 *Définition des paramètre de notre SGBD
 */
public class DBParams {
	
	/**
	 * Chemin vers le dossier DB
	 */
	public static String DBPath ;
	
	/**
	 * Taille d’une page
	 */
	public static int SGBDPageSize;
	
	/**
	 * Nombre maximal de fichiers gérés par le Disk Manager
	 */
	public static int DMFileCount;
	public static int FrameCount;
	
}
