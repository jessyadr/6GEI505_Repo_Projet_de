import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:C:\\Users\\lauri\\OneDrive\\Desktop\\UQAC\\2024_Fall\\Courses\\Methodes_de_gestion_de_projets_informatiques(6GEI505)\\6GEI505_Repo_Projet_de_Session\\base_de_donnees\\gestion_projets.db"; 

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            System.out.println("Connexion à SQLite établie.");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur: Impossible de charger le driver JDBC SQLite.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
        return conn;
    }

    public static boolean addUser(String identifiant, String motDePasse) {
        String sql = "INSERT INTO Utilisateur_Utl (Utl_id, Utl_mdp) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, identifiant);
            pstmt.setString(2, motDePasse);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        connect();
    }
}
