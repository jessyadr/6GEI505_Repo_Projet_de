package GestionProjet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Projet {
    private String nomProjet;
    private int idClient; // ID du client
    private double coutProjet; // Coût estimé
    private int etatProjet; // ID de l'état du projet
    private Utilisateur createur;

    // Constructeur avec tous les paramètres
    public Projet(String nomProjet, int idClient, double coutProjet, int etatProjet, Utilisateur createur) {
        this.nomProjet = nomProjet;
        this.idClient = idClient;
        this.coutProjet = coutProjet;
        this.etatProjet = etatProjet;
        this.createur = createur;
    }

    // Constructeur avec seulement deux paramètres
    public Projet(String nomProjet, Utilisateur createur) {
        this.nomProjet = nomProjet;
        this.idClient = 1; // Par défaut : ID client fictif
        this.coutProjet = 0.0; // Par défaut : coût fictif
        this.etatProjet = 0; // Par défaut : état fictif
        this.createur = createur;
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO ProjetSpecifique (nomProjet, idClient, coutProjet, etatProjet) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nomProjet);
            pstmt.setInt(2, this.idClient);
            pstmt.setDouble(3, this.coutProjet);
            pstmt.setInt(4, this.etatProjet);
            pstmt.executeUpdate();
            System.out.println("Projet sauvegardé avec succès dans la base !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du projet : " + e.getMessage());
        }
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    @Override
    public String toString() {
        return "Projet: " + nomProjet + ", Créateur: " + createur.getNom();
    }
}
