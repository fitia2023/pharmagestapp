package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;
import mu.pharmagest.pharmagestapp.Modele.Medicament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour obtenir les donnees de la base info de la medicament
 */
public class MedicamentDAO {


    public MedicamentDAO() {
    }

    //Pour ajouter medicament dans la base
    public static Boolean addMedicament(Medicament medicament) throws SQLException {
        String requete = "INSERT INTO medicament(nom_medicament,famille,ordonnance,prix_vente,qt_stock,qt_min,qt_max,seuil_commande,unite,id_fournisseur) VALUES (? ,? ,? ,? ,? ,? ,? ,? , ?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setString(1, medicament.getNom_medicament());
                preparedStatement.setString(2, medicament.getFamille());
                preparedStatement.setBoolean(3, medicament.getOrdonnance());
                preparedStatement.setDouble(4, medicament.getPrix_vente());
                preparedStatement.setInt(5, medicament.getQt_stock());
                preparedStatement.setInt(6, medicament.getQt_min());
                preparedStatement.setInt(7, medicament.getQt_max());
                preparedStatement.setInt(8, medicament.getSeuil_commande());
                preparedStatement.setString(9, medicament.getUnite());
                preparedStatement.setInt(10, medicament.getFournisseur_habituel().getId_fournisseur());
                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception selon vos besoins
            throw new SQLException("Erreur lors de l'ajout du medicament", e);
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

    //obtenir medicament par id
    public static Medicament getMedicamentById(int idMedicament) throws SQLException {
        // Requête SQL pour récupérer les medicament par id
        String requete = "SELECT * FROM medicament WHERE id_medicament = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec le nom du fournisseur (utilisation de % pour une recherche partielle)
                preparedStatement.setInt(1, idMedicament);

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapmedicament(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des medicaments par id. Cause : " + e.getMessage(), e);
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

    //    Obtenir nombre de medicament
    public static int getNbMedicament() throws SQLException {
        // Requête SQL pour récupérer le nombre de médicaments
        String requete = "SELECT count(*) AS nb_medicament FROM medicament;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Récupérer le nombre de médicaments à partir du ResultSet
                        return resultSet.getInt("nb_medicament");
                    } else {
                        // Retourner 0 si aucun résultat trouvé
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération du nombre de médicaments. Cause : " + e.getMessage(), e);
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


    //Pour obtenir tous les medicaments
    public static List<Medicament> getallmedicament() throws SQLException {
        // Requête SQL pour récupérer les medicament
        String requete = "SELECT * FROM medicament";

        List<Medicament> medicaments = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {  // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Parcourir les résultats et ajouter les fournisseurs à la liste
                    while (resultSet.next()) {
                        medicaments.add(mapmedicament(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des medicament . Cause : " + e.getMessage(), e);
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

        return medicaments;
    }

    //Obtenir medicament selon le nom medicament
    public static List<Medicament> getMedicamentsByName(String nomMedicament) throws SQLException {
        // Requête SQL pour récupérer les medicaments par nom (utilisation de LIKE pour une recherche partielle)
        String requete = "SELECT * FROM medicament WHERE nom_medicament LIKE ?;";

        List<Medicament> medicaments = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir le paramètre dans la requête préparée avec le nom du medicament (utilisation de % pour une recherche partielle)
                preparedStatement.setString(1, "%" + nomMedicament + "%");

                // Exécuter la requête SQL
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Parcourir les résultats et ajouter les medicament à la liste
                    while (resultSet.next()) {
                        medicaments.add(mapmedicament(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la récupération des medicaments par nom. Cause : " + e.getMessage(), e);
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

        return medicaments;
    }

    //Pour supprimer medicament
    public static boolean deleteMedicament(Integer id) throws SQLException {
        String requete = "DELETE FROM medicament WHERE id_medicament = ?";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                preparedStatement.setInt(1, id);

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Vous pouvez ajouter des informations supplémentaires si nécessaire
            throw new SQLException("Erreur lors de la suppression du medicament. Cause : " + e.getMessage(), e);
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

    //Pour pouvoir mettre a jour medicament
    public static boolean updateMedicament(Medicament medicament) throws SQLException {
        String requete = "UPDATE medicament SET nom_medicament = ?, famille = ?, ordonnance = ?, prix_vente = ?, qt_stock = ?, qt_min = ?, qt_max = ?, seuil_commande = ?, unite = ?, id_fournisseur = ? WHERE id_medicament = ?;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
                // Définir les valeurs des paramètres dans la requête préparée
                preparedStatement.setString(1, medicament.getNom_medicament());
                preparedStatement.setString(2, medicament.getFamille());
                preparedStatement.setBoolean(3, medicament.getOrdonnance());
                preparedStatement.setDouble(4, medicament.getPrix_vente());
                preparedStatement.setInt(5, medicament.getQt_stock());
                preparedStatement.setInt(6, medicament.getQt_min());
                preparedStatement.setInt(7, medicament.getQt_max());
                preparedStatement.setInt(8, medicament.getSeuil_commande());
                preparedStatement.setString(9, medicament.getUnite());
                preparedStatement.setInt(10, medicament.getFournisseur_habituel().getId_fournisseur());
                preparedStatement.setInt(11, medicament.getId_medicament());

                // Exécuter la requête de mise à jour
                int rowCount = preparedStatement.executeUpdate();

                // Vérifier si au moins une ligne a été mise à jour
                return rowCount > 0;
            }
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur SQL
            throw new SQLException("Erreur lors de la mise à jour du medicament. Cause : " + e.getMessage(), e);
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

    private static Medicament mapmedicament(ResultSet resultSet) throws SQLException {

        return new Medicament(
                resultSet.getInt("id_medicament"),
                resultSet.getString("nom_medicament"),
                resultSet.getString("famille"),
                resultSet.getBoolean("ordonnance"),
                resultSet.getDouble("prix_vente"),
                resultSet.getInt("qt_stock"),
                resultSet.getInt("qt_min"),
                resultSet.getInt("qt_max"),
                resultSet.getInt("seuil_commande"),
                resultSet.getString("unite"),
                FournisseurDAO.getFournisseursById(resultSet.getInt("id_fournisseur"))
        );

    }

}
