import java.util.ArrayList;

// Definition de l'etat / la position du jeu

public class Etat {
	protected int joueur; // le joueur dont on attend l'input
	protected char[][] grille; // la grille de 6 x 7
	protected Coup dernierCoup; // le dernier coup joue = le coup joue pour arriver a cet etat
	
	protected final int LARGEUR = 7;
	protected final int HAUTEUR = 6;
	
	protected final char VALEUR_VIDE = 'O';
	protected final char SYMBOLE_HUMAIN = '#';
	protected final char SYMBOLE_ORDI = '@';
	
	protected final static int LARGEUR_MAX = 7;
	protected final int TEMPS = 5;
	protected final static int NB_ALIGN = 4;

	
	// ------------------ CONSTRUCTEURS ------------------------------------
	
	public Etat() { // constructeur pour l'etat initial
		this.joueur = 0; // par defaut
		this.grille = new char[HAUTEUR][LARGEUR];
		for(int i = 0; i < HAUTEUR; i++) {
			for(int j = 0; j < LARGEUR; j++) {
				this.grille[i][j] = VALEUR_VIDE; // on initialise les cases avec du vide
			}
		}
	}
	
	// Utile pour la construction de l'arbre pour le MCTS
	public Etat(Etat aCopier) { // constructeur de copie
		this.grille = new char[HAUTEUR][LARGEUR];
		for(int i = 0; i < HAUTEUR; i++) {
			for(int j = 0; j < LARGEUR; j++) {
				this.grille[i][j] = aCopier.getCase(i, j); 
			}
		}
		this.dernierCoup = aCopier.getDernierCoup();
	}
	
	
	
	// ------------------- FONCTIONS -----------------------------
	
	public void jouerCoup(Coup coup) {
		this.dernierCoup = coup;
		int colonne = coup.getColonne();
		int ligne = getSommetColonne(colonne);
		char aPoser;
		if(joueur == 1)
			aPoser = SYMBOLE_ORDI;
		else
			aPoser = SYMBOLE_HUMAIN;
		grille[ligne][colonne] = aPoser;	
		changerJoueur();
	}
	
	
	private void changerJoueur() {
		if(this.joueur == 1)
			setJoueur(0);
		else
			setJoueur(1);
	}
	
	
	// retourne vrai si la colonne est pleine 
	// (= si la case de colonne c et de ligne 0 est differente de VALEUR_VIDE)
	public boolean colonnePleine(int c) {
		return grille[0][c] != VALEUR_VIDE;
	}
	
	
	public String toString() {
		String toReturn = "";
		toReturn += "L'humain joue les "+SYMBOLE_HUMAIN+", l'ordinateur les "+SYMBOLE_ORDI+"\n";
		toReturn += "---------------\n";
		for(int i = 0; i<HAUTEUR; i++) {
			for(int j = 0; j<LARGEUR; j++) {
				toReturn += " "+grille[i][j];
			}
			toReturn += " | "+i+"\n";
		}
		toReturn += "---------------\n";
		toReturn += " 0 1 2 3 4 5 6\n";
		return toReturn;
	}
	
	
	// Teste si c'est termine (4 alignes) en designant un gagnant. Cas nul et non termine geres
		public String testFin() {
			int k, coupsJoues = 0;
			char tempSymbole, tempCase;
			for(int i=0; i<HAUTEUR; i++) {
				for(int j=0; j<LARGEUR; j++) {
					if(this.getCase(i, j) != VALEUR_VIDE) {
						coupsJoues ++;
						tempCase = this.getCase(i, j);
						tempSymbole = this.getCase(i, j); 
						
						// colonnes
						k = 0;
						while (k < NB_ALIGN && i+k < HAUTEUR && this.getCase(i+k, j) == tempCase)
							k++;
						if(k == NB_ALIGN) {
							if(tempSymbole == SYMBOLE_ORDI) {
								return "ORDI";
							}else {
								return "HUMAIN";
							}
						}
						
						// lignes
						k = 0;
						while (k < NB_ALIGN && j+k < LARGEUR && this.getCase(i, j+k) == tempCase)
							k++;
						if(k == NB_ALIGN) {
							if(tempSymbole == SYMBOLE_ORDI) {
								return "ORDI";
							}else {
								return "HUMAIN";
							}
						}
						
						// diagonales
						k = 0;
						while(k < NB_ALIGN && i+k < HAUTEUR && j+k < LARGEUR && this.getCase(i+k, j+k) == tempCase)
							k++;
						if(k == NB_ALIGN) {
							if(tempSymbole == SYMBOLE_ORDI) {
								return "ORDI";
							}
							else {
								return "HUMAIN";
							}
						}
						
						k = 0;
						while(k < NB_ALIGN && i+k < HAUTEUR && j-k >= 0 && this.getCase(i+k, j-k) == tempCase)
							k++;
						if(k == NB_ALIGN) {
							if(tempSymbole == SYMBOLE_ORDI) {
								return "ORDI";
							}else {
								return "HUMAIN";
							}
						}
						
					}
				}
			}
			
			// sinon tester le match nul
			if(coupsJoues == LARGEUR * HAUTEUR) {
				return "NUL";
			}
			
			return "NON";
		}
	
	public boolean estFinal() {
		String temp = this.testFin();
		return temp.equals("ORDI") || temp.equals("HUMAIN") || temp.equals("NUL");
	}
	
	public ArrayList<Etat> getSucc(){
		ArrayList<Etat> succ = new ArrayList<Etat>();
		Etat etatTmp;
		
		for(int i = 0; i < LARGEUR; i++) {
			etatTmp = new Etat(this);
			if(!etatTmp.colonnePleine(i)) {
				etatTmp.changerJoueur();
				etatTmp.jouerCoup(new Coup(i));
				etatTmp.changerJoueur();
				succ.add(etatTmp);
			}
		}
		return succ;
	}
	
	// ------------- GETTERS & SETTERS -----------------
	
	public void setJoueur(int joueur) {
		this.joueur = joueur;
	}
	
	public int getJoueur() {
		return this.joueur;
	}
	
	public char getValeurVide() {
		return this.VALEUR_VIDE;
	}
	
	public char getSymboleHumain() {
		return this.SYMBOLE_HUMAIN;
	}
	
	public char getSymboleOrdi() {
		return this.SYMBOLE_ORDI;
	}
	
	
	private int getSommetColonne(int c) {
		if(colonnePleine(c))
			return -1;
		else {
			int sommet = -1;
			for(int i = 0; i<HAUTEUR; i++) {
				if(grille[i][c] == VALEUR_VIDE)
					sommet = i;
			}
			return sommet;
		}
	}
	
	public char getCase(int ligne, int colonne) {
		char toReturn = ' ';
		if(ligne < HAUTEUR && colonne < LARGEUR)
			toReturn = grille[ligne][colonne];
		return toReturn;
	}
	
	public Coup getDernierCoup() {
		return this.dernierCoup;
	}
	
	public int getAutreJoueur() {
		if(this.joueur == 1)
			return 0;
		return 1;
	}
}