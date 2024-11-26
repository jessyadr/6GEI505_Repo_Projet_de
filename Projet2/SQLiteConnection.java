import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteConnection {
    public static Connection connect() {
        String url = "jdbc:sqlite:C:\\Users\\lauri\\OneDrive\\Desktop\\UQAC\\2024_Fall\\Courses\\Methodes_de_gestion_de_projets_informatiques(6GEI505)\\6GEI505_Repo_Projet_de_Session\\base de données\\gestion_projets.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("Connexion à SQLite établie.");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur: Impossible de charger le driver JDBC SQLite.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void connectAndAddUser(String username, String password) {
        String sql = "INSERT INTO Utilisateur_Utl(Utl_id, Utl_mdp) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Nouvel utilisateur ajouté : " + username);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur.");
            e.printStackTrace();
        }
    }
}
