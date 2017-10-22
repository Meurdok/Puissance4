import java.util.Scanner;

// Contient le main avec quelques fonctions pour interagir avec le joueur


public class Main {
	
	// Paramètres de jeu
	private final static int LARGEUR_MAX = 7;
	private final int TEMPS = 5;
	private final static int NB_ALIGN = 4;
	
	private static boolean gagnantNul = false;
	private static boolean gagnantHumain = false;
	private static boolean gagnantOrdi = false;
	
	private static String gagnant;
	private static int hauteur;
	private static int largeur;
	
	private static Etat etatCourant;
	private static Coup coupCourant;

	
	public static void main(String[] args) {
		System.out.println("PUISSANCE 4 - MCTS");
		// initialisation
			etatCourant = new Etat();
			hauteur = etatCourant.getHauteur();
			largeur = etatCourant.getLargeur();
		
		// Choisir qui commence
		etatCourant.setJoueur(demanderJoueurCommence());
		
		
		// boucle de jeu
		while(!partieTerminee()) {		
			affichageTour();
			coupCourant = demanderCoup();
			jouerCoup();
			gagnant = testFin();
		}
		
		// Affichages de fin
		System.out.println(etatCourant.toString());
		System.out.println("Gagnant de la partie : "+gagnant);
			
	}
	
	private static int demanderJoueurCommence() {
		Scanner sc;
		int str =-1;
		boolean saisieOk = false;
		while(!saisieOk) {
			sc = new Scanner(System.in);
			System.out.println("Qui commence (0 : humain, 1 : ordinateur) ? ");
			try {
				str = sc.nextInt();
				if(str == 0 || str == 1)
					saisieOk = true;
				
			}catch(Exception e) {
				//System.out.println("Mauvaise saisie");
				saisieOk = false;
			}
		}
		return str;
	}
	
	private static void affichageTour() {
		if(etatCourant.getJoueur() == 1)
			System.out.println("C'est au tour de l'ordinateur");
		else
			System.out.println("C'est au tour de l'humain");
		System.out.println(etatCourant.toString());
	}
	
	private static Coup demanderCoup() {
		Scanner sc;
		int str = -1;
		boolean saisieOk = false;
		while(!saisieOk) {
			sc = new Scanner(System.in);
			System.out.println("Jouer quelle colonne ? (0-6)");
			try {
				str = sc.nextInt();
				// si on veut jouer dans la grille est que la colonne n'est pas pleine
				if(str >=0 && str <LARGEUR_MAX && !etatCourant.colonnePleine(str))
					saisieOk = true;
				
			}catch(Exception e) {
				System.out.println("Mauvaise saisie");
				saisieOk = false;
			}
		}
		return new Coup(str);
	}
	
	// ordiJoueMCTS 
	// TODO
	
	// Teste si c'est termine (4 alignes) en designant un gagnant. Cas nul et non termine geres
	private static String testFin() {
		int k, coupsJoues = 0;
		char tempSymbole, tempCase;
		for(int i=0; i<hauteur; i++) {
			for(int j=0; j<largeur; j++) {
				if(etatCourant.getCase(i, j) != etatCourant.getValeurVide()) {
					coupsJoues ++;
					tempCase = etatCourant.getCase(i, j);
					tempSymbole = etatCourant.getCase(i, j); 
					
					// colonnes
					k = 0;
					while (k < NB_ALIGN && i+k < hauteur && etatCourant.getCase(i+k, j) == tempCase)
						k++;
					if(k == NB_ALIGN) {
						if(tempSymbole == etatCourant.getSymboleOrdi()) {
							gagnantOrdi = true;
							return "ORDI";
						}else {
							gagnantHumain = true;
							return "HUMAIN";
						}
					}
					
					// lignes
					k = 0;
					while (k < NB_ALIGN && j+k < largeur && etatCourant.getCase(i, j+k) == tempCase)
						k++;
					if(k == NB_ALIGN) {
						if(tempSymbole == etatCourant.getSymboleOrdi()) {
							gagnantOrdi = true;
							return "ORDI";
						}else {
							gagnantHumain = true;
							return "HUMAIN";
						}
					}
					
					// diagonales
					k = 0;
					while(k < NB_ALIGN && i+k < hauteur && j+k < largeur && etatCourant.getCase(i+k, j+k) == tempCase)
						k++;
					if(k == NB_ALIGN) {
						if(tempSymbole == etatCourant.getSymboleOrdi()) {
							gagnantOrdi = true;
							return "ORDI";
						}
						else {
							gagnantHumain = true;
							return "HUMAIN";
						}
					}
					
					k = 0;
					while(k < NB_ALIGN && i+k < hauteur && j-k >= 0 && etatCourant.getCase(i+k, j-k) == tempCase)
						k++;
					if(k == NB_ALIGN) {
						if(tempSymbole == etatCourant.getSymboleOrdi()) {
							gagnantOrdi = true;
							return "ORDI";
						}else {
							gagnantHumain = true;
							return "HUMAIN";
						}
					}
					
				}
			}
		}
		
		// sinon tester le match nul
		if(coupsJoues == etatCourant.getLargeur() * etatCourant.getHauteur()) {
			gagnantNul = true;
			return "NUL";
		}
		
		return "NON";
	}
	
	
	// Si il y a match nul ou qu'un des deux joueurs a gagne, la partie est terminee
	private static boolean partieTerminee() {
		return gagnantNul || gagnantOrdi || gagnantHumain;	
	}
	
	
	// jouerCoup met à jour etatCourant en lui appliquant coupCourant
	private static void jouerCoup() {
		etatCourant.jouerCoup(coupCourant);
	}
	
	// fonction copieEtat pas faite car peut etre pas utile
	
	
	
}
