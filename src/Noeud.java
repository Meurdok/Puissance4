import java.util.ArrayList;

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
	
	
	// constructeur du noeud pour la racine
	public Noeud() {
		new Noeud(null); // pas sur que ce soit juste mais bon
	}
	
	
	// constructeur equivalent a nouveauNoeud dans le jeu.c
	public Noeud(Coup coup) {
		if(coup != null) {
			this.coup = coup;
		}else {
			this.coup = new Coup();
			this.joueur = 0;
		}
		this.etat = new Etat();
		this.enfants = new ArrayList<Noeud>();
		
		// Pour MCTS
		this.nbVictoires = 0;
		this.nbSimulations = 0;
	}
	
	
	// ajouter un enfant à un parent en jouant un coup
	public void ajouterEnfant (Coup coup) {
		this.enfants.add(new Noeud(coup));
	}

	public void incNbSimulations() {
		this.nbSimulations ++;
	}
	
	public void incNbVictoires(int inc) {
		this.nbVictoires += inc;
	}
	
	public String toString() {
		String toReturn = "";
		toReturn += "Informations sur le noeud\n";
		toReturn += "Nb simulations effectuees : "+this.nbSimulations+"\n";
		toReturn += "Nb victoires possibles : "+this.nbVictoires+"\n";
		toReturn += "Nb enfants : "+this.enfants.size()+"\n";
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
}
