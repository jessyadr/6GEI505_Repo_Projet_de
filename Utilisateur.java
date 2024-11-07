package Sprint2;

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

    @Override
    public String toString() {
        return "Nom: " + nom + ", Rôle: " + role;
    }
}
