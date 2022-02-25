package Model;

import java.sql.*;
import java.util.ArrayList;

public class BDD {
    private Connection conn;
    private final String drop = "DROP TABLE ImageMoto;";
    private String tableImage = "CREATE TABLE ImageMoto (" +
            "id INT primary key AUTO_INCREMENT," +
            "nom VARCHAR(255) not null, " +
            "histos TEXT not null" +
            ");";

    /**
     * Constructeur, crée la connexion à la base de donnée
     * @throws ClassNotFoundException erreur du driver
     * @throws SQLException erreur de connexion
     */
    public BDD() throws ClassNotFoundException, SQLException {
        String urlBDD = "jdbc:mariadb://localhost:3306/BDD_trobert7";
        String user = "trobert7";
        String password = "0503";
        Class.forName("org.mariadb.jdbc.Driver");
        conn = DriverManager.getConnection(urlBDD, user, password);
        System.err.println("connection OK");
    }


    /**
     * Supprime la table imageMoto
     */
    public void dropImageMotoTable() {
        try {
            Statement requeteStatique = conn.createStatement();
            requeteStatique.executeQuery(drop);
            System.out.println("table imageMoto droped");
        } catch (SQLException e) {
            System.err.println("ERREUR table imageMoto deja supprimé : " + e);
        }
    }

    /**
     * Crée la table imageMoto
     */
    public void creatImageMotoTable() {
        Statement requeteStatique = null;
        try {
            requeteStatique = conn.createStatement();
            requeteStatique.executeQuery(tableImage);
            System.err.println("création de la table imageMoto réussi");
        } catch (SQLException e) {
            System.err.println("ERREUR création de la table imageMoto : " + e);
        }
    }

    /**
     * Insertion des données dans la table imageMoto
     * @param nom de l'image associée
     * @param histo histogramme de l'image associée
     */
    public void insertToImageMoto(String nom, String histo) {
        try {
            String sql = "INSERT INTO ImageMoto(nom, histos) VALUES (?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, nom);
            prep.setString(2, histo);
            boolean rowsAffected = prep.execute();
            System.out.println("insert ok" + rowsAffected);
        } catch (SQLException e) {
            System.err.println("ERREUR lors de insertion : " + e);
        }
    }

    /**
     * Trouver des resemblances entre histogramme
     * @param nbImg nombre d'image souhaité en sortie
     * @param nomImgRef nom de l'image de référence
     * @return une liste de nbImg images resemblent à l'image nomImgRef
     * @throws SQLException erreur SQL
     */
    public ArrayList[][] resemblance(int nbImg, String nomImgRef) throws SQLException {
        ArrayList[][] images = new ArrayList[nbImg][2];
        int i = 0;
        String req = "SELECT distance, nom FROM ImageMoto ORDER BY distance DESC LIMIT ?";
        PreparedStatement prep = conn.prepareStatement(req);
        prep.setInt(1, nbImg);
        ResultSet tableResultat = prep.executeQuery(req);

        if (!tableResultat.next())
            System.out.println("aucune image (c'est louche)");
        else {
            do {
                images[i][1].add(tableResultat.getString("nom"));
                images[i][2].add(tableResultat.getString("distance"));
                i++;
            } while (tableResultat.next());
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
