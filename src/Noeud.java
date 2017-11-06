import java.util.ArrayList;
import java.util.Random;

// Definition du type Noeud

public class Noeud {
	protected int joueur; // joueur qui a joué pour arriver ici
	protected Coup coup; // coup joué par ce joueur pour arriver ici
	protected Etat etat; // etat du jeu
	
	protected Noeud parent; // pas sur que ce soit utile en java au final
	protected ArrayList<Noeud> enfants; // liste d'enfants : chaque enfant correspond à un coup possible
	
	// Pour MCTS
	protected int nbVictoires;
	protected int nbSimulations; //(nb_simus dans le jeu.c)
	
	
	
	// ------------------ CONSTRUCTEURS -------------------------
	
	// constructeur du noeud pour la racine
	public Noeud(Etat etat, int joueur) {
		this.etat = etat;
		this.joueur = joueur;
		this.coup = etat.getDernierCoup();
		this.parent = null;
		this.enfants = new ArrayList<Noeud>();
		
		// Pour MCTS
		this.nbVictoires = 0;
		this.nbSimulations = 0;
	}
	
	
	// constructeur "classique"
	public Noeud(Noeud parent, Etat etat) {	
		if(parent != null) {
			this.parent = parent;
			this.joueur = autreJoueur(parent.getJoueur());
		}else { 
			this.parent = null;
			this.joueur = 0; // pas de parent = racine du MCTS = l'humain vient de jouer
		}	
	
		if(etat != null)
			this.etat = etat;
		else
			this.etat = new Etat();
			
		this.enfants = new ArrayList<Noeud>();
		
		// Pour MCTS
		this.nbVictoires = 0;
		this.nbSimulations = 0;
	}
	
	// constructeur de copie (pour le MCTS)
	public Noeud(Noeud aCopier) {
		this.joueur = aCopier.getJoueur();
		this.coup = aCopier.getCoup();
		this.etat = aCopier.getEtat();
		this.parent = aCopier.getParent();
		this.enfants = new ArrayList<Noeud>();
		for(Noeud n : aCopier.getEnfants())
			this.enfants.add(n);
	}
	
	
	// -------------------------- FONCTIONS --------------------------------
	
	// ajouter un enfant à un parent en jouant un coup
	public void ajouterEnfant (Noeud noeud) {
		this.enfants.add(noeud);
	}
	
	public int autreJoueur(int joueur) {
		if(joueur == 1)
			return 0;
		return 1;
	}
	
	public Noeud getBValeurMax(int joueur) {
		Noeud meilleurChoix = null;
		
		float ratioVS; // ratio Victoires / Simulations
		float c = (float) Math.sqrt(2); // constante c (cf enonce)
		float partForm; // l'autre partie de la formule 
		
		float BValeurMax = Float.NEGATIVE_INFINITY;
		float BValeurCourante;
		
		int victDef; // coefficient pour le ratio
		if(this.joueur == joueur)
			victDef = -1;
		else
			victDef = 1;
		
		for(Noeud e : enfants) {
			// si aucune simulation ou aucune victoire pour cet enfant
			if(e.getNbSimulations() == 0 || e.getNbVictoires() == 0) 
				ratioVS = 0;
			else
				ratioVS = victDef *(float)(e.getNbVictoires() / e.getNbSimulations());
			
			if(e.getNbSimulations() == 0 || nbSimulations == 0)
				partForm = 0;
			else
				partForm = (float)(c * Math.sqrt(Math.log(nbSimulations) / e.getNbSimulations()));
			
			BValeurCourante = ratioVS + partForm;
			if(BValeurCourante > BValeurMax) {
				BValeurMax = BValeurCourante;
				meilleurChoix = e;
			}
		}
		
		return meilleurChoix;
	}
	
	public boolean estFinal() {
		return etat.estFinal();
	}
	
	public boolean estRacine() {
		return this.parent == null;
	}
	
	public Noeud getNoeudSuivant() {
		Noeud suivant = null;
		
		// si on n'as pas encore rempli la liste des enfants pour ce noeud, on la remplit avec les successeurs de l'etat
		if(enfants.size() == 0) {
			for(Etat e : this.etat.getSucc()) {
				this.ajouterEnfant(new Noeud(this, e));
			}
		}
		
		// puis on regarde si on trouve des enfants pas encore developpes (= qui n'ont pas encore subit de simulation)
		ArrayList<Noeud> aDevelopper = new ArrayList<Noeud>();
		for(Noeud e : enfants) {
			if(e.getNbSimulations() == 0)
				aDevelopper.add(e);
		}
		
		// si tous les enfants ont deja subit au moins une simulation
		if(aDevelopper.size() == 0)
			suivant = this.enfants.get((int)(Math.random() * (this.enfants.size() )));// on en prend un au hasard parmis tous
		else // sinon on en prend un au hasard parmis ceux à developper
			suivant = aDevelopper.get((int)(Math.random() * (aDevelopper.size())));
		
		return suivant;
	}
	
	public void incNbSimulations() {
		this.nbSimulations += 1;
	}
	
	public void incNbVictoires(int inc) {
		this.nbVictoires += inc;
	}
	
	public String toString() {
		String toReturn = "";
		toReturn += "Infos Noeud\n";
		toReturn += "Nombre simulation effectuees : "+nbSimulations+"\n";
		toReturn += "Nombre victoires possibles : "+nbVictoires;
		return toReturn;
	}
	
	// -------------------------- GETTERS / SETTERS -------------------------
	
	public ArrayList<Noeud> getEnfants(){
		return this.enfants;
	}
	
	public int getNbEnfants() {
		return this.enfants.size();
	}
	
	public Etat getEtat() {
		return this.etat;
	}
	
	public Noeud getParent() {
		return this.parent;
	}
	
	public int getJoueur() {
		return this.joueur;
	}
	
	public Coup getCoup() {
		return this.coup;
	}
	
	public int getNbVictoires() {
		return this.nbVictoires;
	}
	
	public int getNbSimulations() {
		return this.nbSimulations;
	}
	
	public Noeud getEnfantRand() {
		if(enfants.size() == 0) {
			for(Etat e : etat.getSucc())
				ajouterEnfant(new Noeud(this, e));
		}
		return this.enfants.get((int)(Math.random() * (this.enfants.size() )));
	}
}