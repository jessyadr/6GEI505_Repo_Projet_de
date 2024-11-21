package GestionProjet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Chemin complet vers le fichier SQLite
    private static final String URL = "jdbc:sqlite:C:/myDB.db"; // Remplacez <VotreNom> par votre nom d'utilisateur
    private static Connection connection = null;

    /**
     * Méthode pour obtenir une connexion à la base de données SQLite.
     *
     * @return Connection object si la connexion est réussie, sinon null.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                System.out.println("Tentative de connexion à la base de données avec l'URL : " + URL);
                connection = DriverManager.getConnection(URL);
                System.out.println("Connexion à la base de données réussie !");
            } catch (SQLException e) {
                System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
                e.printStackTrace(); // Affiche les détails de l'erreur pour le débogage
            }
        }
        return connection;
    }

    /**
     * Méthode pour exécuter un script SQL à partir d'un fichier.
     *
     * @param filePath Chemin du fichier contenant le script SQL.
     */
    public static void executeSQLFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
                // Exécuter chaque commande SQL terminée par un point-virgule
                if (line.trim().endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql.setLength(0); // Réinitialiser pour la prochaine commande
                }
            }
            System.out.println("Script SQL exécuté avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier SQL : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de l'exécution du script : " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("La connexion à la base de données est nulle. Vérifiez la configuration.");
            e.printStackTrace();
        }
    }
}
