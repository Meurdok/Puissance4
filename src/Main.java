import java.util.Scanner;

// Contient le main avec quelques fonctions pour interagir avec le joueur


public class Main {
	
	// Paramètres de jeu
	protected final static int LARGEUR_MAX = 7;
	protected final static int MAX_TEMPS = 3;
	
	private static String gagnant;
	
	private static Etat etatCourant;
	private static Coup coupCourant;

	
	public static void main(String[] args) {
		System.out.println("PUISSANCE 4 - MCTS");
		// initialisation
			etatCourant = new Etat();
			gagnant = "NON";
		
		// Choisir qui commence
		etatCourant.setJoueur(demanderJoueurCommence());
		
		
		// boucle de jeu
		while(!partieTerminee()) {		
			affichageTour();
			if(etatCourant.getJoueur() == 0) { // si c'est a l'humain de jouer
				coupCourant = demanderCoup();
				jouerCoup();
			}else { // si c'est à la machine de jouer
				ordiJoueMCTS(1, MAX_TEMPS);
			}
			gagnant = etatCourant.testFin();
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
		
	
	// Si il y a match nul ou qu'un des deux joueurs a gagne, la partie est terminee
	private static boolean partieTerminee() {
		return etatCourant.estFinal();
	}
	
	
	// jouerCoup met à jour etatCourant en lui appliquant coupCourant
	private static void jouerCoup() {
		etatCourant.jouerCoup(coupCourant);
	}
	
	// fonction qui applique mcts pour faire jouer l'IA
	private static void ordiJoueMCTS(int joueur, int maxTemps) {
		long debut = System.currentTimeMillis();
		long tempTmp = debut;
		long tempTmpSec = (tempTmp - debut) / 1000; // pour avoir le temps en secondes
		
		int resPartie = -1; // variable resultat des parties 
		
		Noeud racine = new Noeud(etatCourant, etatCourant.getAutreJoueur());
		Noeud noeudCourant = racine;
		
		while(tempTmpSec < maxTemps) { // enchainement de fonctions comme sur le poly du cours (p32)
			noeudCourant = selectionner(noeudCourant, joueur);
			if(!noeudCourant.estFinal()) {
				noeudCourant = developper(noeudCourant);
				resPartie = simuler(noeudCourant);
			}
			noeudCourant = mettreAJour(noeudCourant, resPartie);
			
			tempTmpSec = (System.currentTimeMillis()-debut)/1000;
		}
		Coup aJouer = noeudCourant.getBValeurMax(joueur).getEtat().getDernierCoup();
		etatCourant.jouerCoup(aJouer);
	}
	
	public static Noeud selectionner(Noeud noeud, int joueur) {
		if(noeud.estFinal() || noeud.getNbEnfants() == 0)
			return noeud;
		return selectionner(noeud.getBValeurMax(joueur), joueur);
	}
	
	public static Noeud developper(Noeud noeud) {
		return noeud.getNoeudSuivant();
	}
	
	public static int simuler(Noeud noeud) {
		Noeud copie = new Noeud(noeud);
		return marcheAleatoire(copie);
	}
	
	public static int marcheAleatoire(Noeud noeud) {
		// On cherche en profondeur de facon aleatoire une fin de partie
		while(!noeud.estFinal())
			noeud = noeud.getEnfantRand();
		
		// On recupere la valeur de fin
		String resPartie = noeud.getEtat().testFin();
		if(resPartie.equals("ORDI")) // si l'ordi a gagne, l'execution de la marche aleatoire est un succes
			return 1;
		else
			return 0;	
	}
	
	// Pour mettre a jour tout l'arbre avec le nouveau nombre de simulations et de victoires
	public static Noeud mettreAJour(Noeud noeud, int nbVictoires) {
		while(!noeud.estRacine()) {
			noeud.incNbSimulations();
			noeud.incNbVictoires();
			noeud = noeud.getParent();
		}
		return noeud;
	}
}
