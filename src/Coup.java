// Definition d'un coup

public class Coup {	
	protected int ligne;
	protected int colonne;
	
	private final int LARGEUR = 7;
	
	public Coup() { // constructeur par defaut => -1, -1
		this.ligne = -1;
		this.colonne = -1;
	}
	
	public Coup(int colonne) {
		if(colonne < LARGEUR && colonne >= 0) { 
			this.colonne = colonne;
		}else
			System.out.println("Coup hors grille");
	}
	
	// coupsPossibles (ici, ou dans le main...)
	// TODO
}
