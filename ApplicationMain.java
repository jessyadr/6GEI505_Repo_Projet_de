package Sprint2;

import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationMain {
    private static ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
    private static ArrayList<Projet> projets = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenue dans le système de gestion de projets.");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Créer un utilisateur");
            System.out.println("2. Créer un projet");
            System.out.println("3. Afficher les utilisateurs");
            System.out.println("4. Afficher les projets");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option: ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerUtilisateur();
                    break;
                case 2:
                    creerProjet();
                    break;
                case 3:
                    afficherUtilisateurs();
                    break;
                case 4:
                    afficherProjets();
                    break;
                case 5:
                    System.out.println("Fin du programme.");
                    return;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    private static void creerUtilisateur() {
        System.out.print("Entrez le nom de l'utilisateur: ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le rôle (Administrateur, Gestionnaire de projet, Employé): ");
        String role = scanner.nextLine();
        Utilisateur utilisateur = new Utilisateur(nom, role);
        utilisateurs.add(utilisateur);
        System.out.println("Utilisateur créé: " + utilisateur);
    }

    private static void creerProjet() {
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur disponible pour créer un projet.");
            return;
        }

        System.out.println("Sélectionnez le créateur du projet:");
        for (int i = 0; i < utilisateurs.size(); i++) {
            System.out.println((i + 1) + ". " + utilisateurs.get(i).getNom() + " (" + utilisateurs.get(i).getRole() + ")");
        }
        int indexCreateur = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (indexCreateur < 0 || indexCreateur >= utilisateurs.size()) {
            System.out.println("Créateur invalide.");
            return;
        }

        Utilisateur createur = utilisateurs.get(indexCreateur);
        if (!createur.peutCreerProjet()) {
            System.out.println("Seuls les administrateurs et les gestionnaires de projet peuvent créer un projet.");
            return;
        }

        System.out.print("Entrez le nom du projet: ");
        String nomProjet = scanner.nextLine();
        Projet projet = new Projet(nomProjet, createur);
        projets.add(projet);
        System.out.println("Projet créé: " + projet);
    }

    private static void afficherUtilisateurs() {
        System.out.println("Liste des utilisateurs:");
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println(utilisateur);
        }
    }

    private static void afficherProjets() {
        System.out.println("Liste des projets:");
        for (Projet projet : projets) {
            System.out.println(projet);
        }
    }
}
