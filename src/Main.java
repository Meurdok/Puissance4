import java.util.Scanner;

// Contient le main avec quelques fonctions pour interagir avec le joueur


public class Main {
	
	// Paramètres de jeu
	private final static int LARGEUR_MAX = 7;
	private final int TEMPS = 5;
	
	/* Pas sur qu'il soit utile en java
	private final String[] finDePartie = {"NON", "MATCHNUL", "ORDI_GAGNE", "HUMAIN_GAGNE"};
	*/
	
	private Etat etatCourant;
	
	private void afficherJeu() {
		// TODO
	}
	
	public static void main(String[] args) {
		Coup coup = demanderCoup();
		// variable FinDePartie fin dans jeu.c peut et sera je pense gere autrement
		
		// initialisation
		Etat etat = new Etat();
		
		// Choisir qui commence
		etat.setJoueur(demanderJoueurCommence());
		
		// boucle de jeu
		// TODO
		
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
				System.out.println("Mauvaise saisie");
				saisieOk = false;
			}
		}
		return str;
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
				if(str >=0 && str <LARGEUR_MAX)
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
	
	// jouerCoup
	// TODO
	
	// fonction copieEtat pas faite car peut etre pas utile
	
	
	
}
