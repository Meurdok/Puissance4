// Definition d'un coup

public class Coup {	
	protected int colonne;
	
	private final int LARGEUR = 7;
	
	public Coup() { // constructeur par defaut
		this.colonne = -1;
	}
	
	public Coup(int colonne) {
		if(colonne < LARGEUR && colonne >= 0) { 
			this.colonne = colonne;
		}else
			System.out.println("Coup hors grille");
	}
	
	public int getColonne() {
		return this.colonne;
	}
	
	// coupsPossibles (ici, ou dans le main...)
	// TODO
}
