package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.LigneVente;
import mu.pharmagest.pharmagestapp.Modele.Medicament;
import mu.pharmagest.pharmagestapp.Modele.Vente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour obtenir les donnees de la base
 */
public class LigneVenteDAO {

    public LigneVenteDAO() {
    }

    //Obtenir tous les lignesventes
    public static List<LigneVente> getalllignevente() throws SQLException {

        String requete = "SELECT * FROM lignevente";

        List<LigneVente> ligneVentes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ligneVentes.add(maplignevente(resultSet));
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la recuperation liste ligneventes. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }

        return ligneVentes;
    }

    /**
     * Ajoute une lignevente à la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean addLigneVente(LigneVente ligneVente) throws SQLException {
        String requete = "INSERT INTO lignevente(id_vente, id_medicament, qt) VALUES (?, ?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, ligneVente.getVente().getId_vente());
                preparedStatement.setInt(2, ligneVente.getMedicament().getId_medicament());
                preparedStatement.setInt(3, ligneVente.getQt());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du lignevente", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Supprime vente de la base de données.
     *
     * @param idVente Identifiant du venté à supprimer.
     * @return True si la suppression réussit, sinon False.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static boolean deleteVenteLigne(Integer idVente) throws SQLException {
        String requete = "DELETE FROM lignevente WHERE id_vente = ?";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, idVente);

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la suppression du vente ligne. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Récupère vente par id.
     *
     * @param id dont id_vente
     * @return lignevente correspondant à l'id.
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL.
     */
    public static List<LigneVente> getLigneVentesById(Integer id) throws SQLException {
        // Requête SQL pour récupérer les lignevente par id
        String requete = "SELECT * FROM lignevente WHERE id_vente = ?;";

        List<LigneVente> ligneVentes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            // Définir la valeur du paramètre id_vente
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ligneVentes.add(maplignevente(resultSet));
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la recuperation du vente ligne par id. Cause : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Gérer l'exception de fermeture de la connexion
                    e.printStackTrace();
                }
            }
        }

        return ligneVentes;
    }

    //Obtenir ligneVente
    private static LigneVente maplignevente(ResultSet resultSet) throws SQLException {

        Vente vente = VenteDAO.getVenteById(
                resultSet.getInt("id_vente")
        );
        Medicament medicament = MedicamentDAO.getMedicamentById(
                resultSet.getInt("id_medicament")
        );

        return new LigneVente(
                vente,
                medicament,
                resultSet.getInt("qt")
        );

    }

}
