package GestionProjet;

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
            scanner.nextLine(); // Consommer la ligne restante

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
        System.out.println("Choisissez le rôle:");
        System.out.println("1. Administrateur");
        System.out.println("2. Gestionnaire de projet");
        System.out.println("3. Employé");
        System.out.print("Entrez le numéro correspondant au rôle: ");
        int roleChoix = scanner.nextInt();
        scanner.nextLine(); 

        String role = "";
        switch (roleChoix) {
            case 1:
                role = "Administrateur";
                break;
            case 2:
                role = "Gestionnaire de projet";
                break;
            case 3:
                role = "Employé";
                break;
            default:
                System.out.println("Rôle invalide. Utilisateur non créé.");
                return;
        }

        Utilisateur utilisateur = new Utilisateur(nom, role);
        utilisateurs.add(utilisateur);
        utilisateur.saveToDatabase(); // Sauvegarde dans la base
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
        scanner.nextLine(); // Consommer la ligne restante

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
        Projet projet = new Projet(nomProjet, createur); // Utilisation du constructeur simplifié
        projets.add(projet);
        projet.saveToDatabase(); // Sauvegarde dans la base
        System.out.println("Projet créé: " + projet);
    }

    private static void afficherUtilisateurs() {
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }

        System.out.println("Liste des utilisateurs:");
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println(utilisateur);
        }
    }

    private static void afficherProjets() {
        if (projets.isEmpty()) {
            System.out.println("Aucun projet enregistré.");
            return;
        }

        System.out.println("Liste des projets:");
        for (Projet projet : projets) {
            System.out.println(projet);
        }
    }
}
