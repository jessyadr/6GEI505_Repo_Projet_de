package GestionProjet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {
    private String nom;
    private String role;

    public Utilisateur(String nom, String role) {
        this.nom = nom;
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public String getRole() {
        return role;
    }

    public boolean peutCreerProjet() {
        return role.equalsIgnoreCase("Administrateur") || role.equalsIgnoreCase("Gestionnaire de projet");
    }

    public void saveToDatabase() {
        int roleId = getRoleId();
        if (roleId == 0) {
            System.out.println("Le rôle '" + role + "' n'existe pas dans la table Role. Veuillez l'ajouter d'abord.");
            return;
        }

        String sql = "INSERT INTO Employee (nomEmployee, motdePasse, idRole) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nom);
            pstmt.setString(2, "default_password"); // Mot de passe par défaut
            pstmt.setInt(3, roleId); // ID du rôle
            pstmt.executeUpdate();
            System.out.println("Utilisateur sauvegardé avec succès dans la base !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
        }
    }

    private int getRoleId() {
        String sql = "SELECT idRole FROM Role WHERE nomRole = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.role);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idRole");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID du rôle : " + e.getMessage());
        }
        return 0; // Retourne 0 si le rôle n'est pas trouvé
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Rôle: " + role;
    }
}
