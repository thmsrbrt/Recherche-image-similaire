package Model;

import java.sql.*;

public class BDD {
    private static int id = 0;
    private Connection conn;
    private final String drop = "DROP TABLE Image;";
    private final String tableImage = "CREATE TABLE Image (" +
            "id INT primary key AUTO_INCREMENT," +
            "nom VARCHAR(255) not null, " +
            "histosRGB TEXT not null," +
            "histosHSV TEXT NOT NULL" +
            ");";


    /**
     * Constructeur, crée la connexion à la base de donnée
     */
    public BDD() {
        String urlBDD = "jdbc:mariadb://localhost:3306/BDD_trobert7";
        String user = "trobert7";
        String password = "0503";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(urlBDD, user, password);
            System.err.println("connection OK");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("ERREUR lors de l'initialisation de la BDD");
        }
    }

    /**
     * Supprime la table Image
     */
    public void dropImageTable() {
        try {
            Statement requeteStatique = conn.createStatement();
            requeteStatique.executeUpdate(drop);
            System.err.println("table Image droped");
        } catch (SQLException e) {
            System.err.println("ERREUR table Image deja supprimé : " + e);
        }
    }

    /**
     * Crée la table Image
     */
    public void creatImageTable() {
        try {
            Statement requeteStatique = conn.createStatement();
            requeteStatique.executeUpdate(tableImage);
            System.err.println("création de la table Image réussi");
        } catch (SQLException e) {
            System.err.println("ERREUR création de la table Image : " + e);
        }
    }

    /**
     * Insertion des données dans la table Image
     * @param nom de l'image associée
     * @param histoRGB histogramme de l'image associée
     */
    public void insertToImage(String nom, String histoRGB, String histoHSV) {
        try {
            String sql = "INSERT INTO Image(nom, histosRGB, histosHSV) VALUES (?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, nom);
            prep.setString(2, histoRGB);
            prep.setString(3, histoHSV);
            prep.executeUpdate();
            System.err.println("insert ok : " + id++);
        } catch (SQLException e) {
            System.err.println("ERREUR lors de insertion : " + e);
        }
    }

    public String getImageByName(String name, boolean typeHisto) {
        try {
            String requete = "SELECT nom, " + (typeHisto ? "histosRGB" : "histosHSV") +  " FROM Image WHERE nom = ?";
            PreparedStatement prep = conn.prepareStatement(requete);
            prep.setString(1, name);
            ResultSet resultSet = prep.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString((typeHisto ? "histosRGB" : "histosHSV"));
            }
        } catch (SQLException e) {
            System.err.println("ERREUR lors du select by name : " + e);
        }

        return null;
    }

    public String[] getAllImage(boolean typeHisto) {
        String [] images = new String[2];
        images[0] = "";
        images[1] = "";
        try {
            String requete = "SELECT nom, " + (typeHisto ? "histosRGB" : "histosHSV") +" FROM Image ORDER BY nom";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                images[0] += resultSet.getString("nom") + ";";
                images[1] += resultSet.getString((typeHisto ? "histosRGB" : "histosHSV")) + "histo";
            }
        } catch (SQLException e) {
            System.err.println("ERREUR : select all from Image " + e);
        }
        return images;
    }

    /**
     * Ferme la connexion de la base de donnée
     */
    public void closeConn() {
        try {
            conn.close();
            System.err.println("connexion close");
        } catch (SQLException e) {
            System.err.println("ERREUR impossible de fermer la connexion : " + e);
        }
    }
}
