package Sprint2;

import java.util.ArrayList;

public class Projet {
    private String nomProjet;
    private ArrayList<String> taches;
    private Utilisateur createur;

    public Projet(String nomProjet, Utilisateur createur) {
        this.nomProjet = nomProjet;
        this.createur = createur;
        this.taches = new ArrayList<>();
    }

    public void ajouterTache(String tache) {
        taches.add(tache);
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    @Override
    public String toString() {
        return "Projet: " + nomProjet + ", Créateur: " + createur.getNom() + ", Tâches: " + taches;
    }
}
