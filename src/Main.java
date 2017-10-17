import java.util.Scanner;

// Contient le main avec quelques fonctions pour interagir avec le joueur


public class Main {
	
	// Paramètres de jeu
	private final static int LARGEUR_MAX = 7;
	private final int TEMPS = 5;
	
	private static boolean gagnantNul = false;
	private static boolean gagnantHumain = false;
	private static boolean gagnantOrdi = false;
	
	private static Etat etatCourant;
	private static Coup coupCourant;

	
	public static void main(String[] args) {
		System.out.println("PUISSANCE 4 - MCTS");
		// initialisation
			etatCourant = new Etat();
				
		
		// Choisir qui commence
		etatCourant.setJoueur(demanderJoueurCommence());
		
		
		// boucle de jeu
		// TODO
		while(!partieTerminee()) {		
			affichageTour();
			coupCourant = demanderCoup();
			jouerCoup();
		}
		
		// Affichages de fin
		// TODO
		
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
	
	// testFin
	// TODO
	
	
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
