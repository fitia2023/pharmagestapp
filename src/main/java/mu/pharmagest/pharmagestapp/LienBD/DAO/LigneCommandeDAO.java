package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.LigneCommande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Pour la commande de medicament detaillant selon la quantite
public class LigneCommandeDAO {


    public LigneCommandeDAO() {
    }

    //Pour ajout de panier commande
    public static Boolean addLigneCommande(LigneCommande ligneCommande) throws SQLException {
        String requete = "INSERT INTO lignecommande (id_medicament, id_commande, qt_vente) VALUES (?, ?, ? );";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, ligneCommande.getMedicament().getId_medicament());
                preparedStatement.setInt(2, ligneCommande.getCommande().getId_commande());
                preparedStatement.setDouble(3, ligneCommande.getQt_medicament());
                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du lignecommande", e);
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

    //Obtenir toute la ligne de commande
    public static List<LigneCommande> getallLigneCommande() throws SQLException {
        String requete = "SELECT * FROM lignecommande";

        List<LigneCommande> ligneCommandes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ligneCommandes.add(mapligneCommande(resultSet));
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du lignecommande", e);
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
        return ligneCommandes;
    }


    //up quantite recu de la commande
    public static Boolean qtrecucommande(LigneCommande ligneCommande) throws SQLException {
        // Requête SQL pour mettre à jour un qt_recu
        String requete = "UPDATE lignecommande SET qt_recu = ? WHERE id_medicament = ? AND id_commande = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setInt(1, ligneCommande.getQt_recu());
                preparedStatement.setInt(2, ligneCommande.getMedicament().getId_medicament());
                preparedStatement.setInt(3, ligneCommande.getCommande().getId_commande());

                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du lignecommande. Cause : " + e.getMessage(), e);
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

    //Obtenir map de ligne commange
    private static LigneCommande mapligneCommande(ResultSet resultSet) throws SQLException {

        return new LigneCommande(
                MedicamentDAO.getMedicamentById(resultSet.getInt("id_medicament")),
                CommandeDAO.getcommandebyid(resultSet.getInt("id_commande")),
                resultSet.getInt("qt_vente"),
                resultSet.getInt("qt_recu")
        );

    }

}
