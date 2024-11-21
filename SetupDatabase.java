package GestionProjet;

public class SetupDatabase {
    public static void main(String[] args) {
        // Chemin absolu vers ton fichier SQL
        String filePath = "C:\\BD.sql";

        // Exécuter le script SQL pour créer les tables
        DBConnection.executeSQLFromFile(filePath);
    }
}
