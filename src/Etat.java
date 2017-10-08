// Definition de l'etat / la position du jeu

public class Etat {
	protected int joueur;
	protected char[][] grille; // la grille de 6 x 7
	protected final int LARGEUR = 7;
	protected final int HAUTEUR = 6;
	
	public Etat() { // constructeur pour l'etat initial
		this.joueur = 0; // par defaut
		this.grille = new char[6][7];
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				this.grille[i][j] = '_'; // on initialise les cases avec du vide
			}
		}
	}
	
	public Etat(int joueur, char[][] grille) { // constructeur d'un etat 'classique'
		if(parametersOK(joueur, grille)) { 
			this.joueur = joueur;
			this.grille = grille;			
		}else {
			System.out.println("Mauvais paramètres passes au constructeur Etat");
		}
	}
	
	public void majEtat(int joueur, char[][] grille){ // mise à jour d'un etat (peut etre inutile)
		if(parametersOK(joueur, grille)){
			this.joueur = joueur;
			this.grille = grille;
		}
	}
	
	// on ne peut pas accepter de grille plus grande ou plus petite que 6 x 7, 
	// ni de joueur autre que 0 ou 1
	private boolean parametersOK(int joueur, char[][] grille) {
		return (grille[0].length == LARGEUR 
				&& grille.length == HAUTEUR 
				&& (joueur == 0 || joueur == 1));
	}
	
	public void setJoueur(int joueur) {
		this.joueur = joueur;
	}
	
	public String toString() {
		String toReturn = "";
		toReturn += "--------------- Haut grille\n";
		for(int i = 0; i<HAUTEUR; i++) {
			for(int j = 0; j<LARGEUR; j++) {
				toReturn += " "+grille[i][j];
			}
			toReturn += "\n";
		}
		toReturn += "--------------- Bas grille\n";
		return toReturn;
	}
}

// TODO : peut etre a completer pour une definition plus complete ou plus juste