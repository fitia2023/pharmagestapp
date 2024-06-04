package mu.pharmagest.pharmagestapp.LienBD.DAO;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.Modele.Fournisseur;
import mu.pharmagest.pharmagestapp.Modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe gère l'accès aux données utilisateur dans la base de données.
 * Elle fournit des méthodes pour ajouter, récupérer, mettre à jour et supprimer des commandes.
 */
public class UtilisateurDAO {

    /****************
     *              *
     * Constructeur *
     *              *
     ****************/
    public UtilisateurDAO() {
    }

    //Obtenir tous les utilisateurs
    public static List<Utilisateur> getallutilisateurs() throws SQLException {

        String requete = "SELECT * FROM utilisateur";

        List<Utilisateur> utilisateurs = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                utilisateurs.add(mapUtilisateur(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la liste utilisateurs. Cause : " + e.getMessage(), e);
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

        return utilisateurs;
    }

    /**
     * Vérifie existence de l'utilisateur
     *
     * @param identifiant L'identification de l'utilisateur.
     * @param motDePasse  Mot de passe de l'utilisateur correspondant à l'identifiant.
     * @return true si trouver, ou false s'il n'existe pas.
     * @throws SQLException     Si une erreur survient lors de l'interaction avec la base de données.
     * @throws RuntimeException Si une erreur survient lors de l'authentification.
     */
    public static boolean sAuthentifier(String identifiant, String motDePasse) {

        final String requete_authentification = "SELECT COUNT(*) FROM Utilisateur WHERE identifiant = ? AND mot_de_passe = ? AND bloquer = false AND actif = true;";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_authentification)) {
                preparedStatement.setString(1, identifiant);
                preparedStatement.setString(2, motDePasse);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count == 1;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors sur l'authentification utilisateur:", e);
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
        return false;
    }

    //    Information sur la connexion
    public static void enregistrerConnexion(String identifiant, boolean reussi) {
        final String sql = "call enregistrer_connexion(?, ?)";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, identifiant);
                preparedStatement.setBoolean(2, reussi);

                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors sur l'authentification utilisateur:", e);
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

    //    Recherche utilisateur par nom
    public static List<Utilisateur> getallutilisateursbyname(String nom) throws SQLException {
        String requete = "SELECT * FROM utilisateur WHERE identifiant LIKE ?;";

        List<Utilisateur> utilisateurs = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, "%" + nom + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                utilisateurs.add(mapUtilisateur(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la liste utilisateurs par nom. Cause : " + e.getMessage(), e);
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

        return utilisateurs;
    }

    /**
     * Vérifie création de l'utilisateur
     *
     * @param identifiant L'identification de l'utilisateur.
     * @param motDePasse  Mot de passe de l'utilisateur correspondant à l'identifiant.
     * @return true si trouver, ou false s'il n'existe pas.
     * @throws SQLException     Si une erreur survient lors de l'interaction avec la base de données.
     * @throws RuntimeException Si une erreur survient lors de l'authentification.
     */
    public static boolean inscription(String identifiant, String motDePasse) {

        final String requete_authentification = "SELECT COUNT(*) FROM Utilisateur WHERE identifiant = ? AND mot_de_passe = ? AND bloquer = false AND actif = false;";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_authentification)) {
                preparedStatement.setString(1, identifiant);
                preparedStatement.setString(2, motDePasse);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count == 1;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors sur l'authentification utilisateur:", e);
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
        return false;
    }


    /**
     * Récupère un utilisateur
     *
     * @param identifiant L'identification de l'utilisateur.
     * @param motDePasse  Mot de passe de l'utilisateur correspondant à l'identifiant.
     * @return un utilisateur si trouvé, ou null s'il n'existe pas.
     * @throws SQLException     Si une erreur survient lors de l'interaction avec la base de données.
     * @throws RuntimeException Si une erreur survient lors de l'authentification.
     */
    public static Utilisateur getUtilisateurConnecter(String identifiant, String motDePasse) {
        String requete_getutilisateur = "SELECT * FROM Utilisateur WHERE identifiant = ? AND mot_de_passe = ? AND bloquer = false;";
        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_getutilisateur)) {
                preparedStatement.setString(1, identifiant);
                preparedStatement.setString(2, motDePasse);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapUtilisateur(resultSet);
                    } else {
                        return null;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la récuperation utilisateur: " + e.getMessage(), e);
            }
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
     * Création d'utilisateur avec son mot de passe dans la base de données.
     *
     * @param utilisateur L'utilisateur à créer
     * @return true si l'utilisateur a été créé avec succès, sinon false.
     * @throws RuntimeException Si une erreur survient lors de l'ajout de l'utilisateur.
     */
    public static boolean createUtilisateur(Utilisateur utilisateur) {
        final String requete_create = "UPDATE utilisateur SET mot_de_passe = ?, actif = ? WHERE id_utilisateur = ?";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_create)) {
                preparedStatement.setString(1, utilisateur.getMot_de_passe());
                preparedStatement.setBoolean(2, true);
                preparedStatement.setInt(3, utilisateur.getId_utilisateur());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur:", e);
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
     * Crée un nouvel utilisateur dans la base de données par admin pharmacien.
     *
     * @param utilisateur L'utilisateur à créer
     * @return true si l'utilisateur a été créé avec succès, sinon false.
     * @throws RuntimeException Si une erreur survient lors de l'ajout de l'utilisateur.
     */

    public static boolean createUtilisateurbypharmacien(Utilisateur utilisateur) {
        final String requete_create = "INSERT INTO utilisateur(nom_utilisateur, prenom_utilisateur, annif_utilisateur, adresse_utilisateur, tel_utilisateur, identifiant, mot_de_passe, role, actif, bloquer) VALUES(?,?,?,?,?,?,?,?,?,?)";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_create)) {
                // Paramètres de la requête
                preparedStatement.setString(1, utilisateur.getNom_utilisateur());
                preparedStatement.setString(2, utilisateur.getPrenom_utilisateur());
                preparedStatement.setDate(3, new Date(utilisateur.getAnnif_utilisateur().getTime()));
                preparedStatement.setString(4, utilisateur.getAdresse_utilisateur());
                preparedStatement.setInt(5, utilisateur.getTel_utilisateur());
                preparedStatement.setString(6, utilisateur.getIdentifiant());
                preparedStatement.setString(7, utilisateur.getMot_de_passe());
                preparedStatement.setString(8, utilisateur.getRole().toString());
                preparedStatement.setBoolean(9, false);
                preparedStatement.setBoolean(10, false);

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur:", e);
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
     * Désactive utilisateur dans la base de données.
     *
     * @param utilisateur L'utilisateur à désactiver
     * @return true si l'utilisateur a été désactiver avec succès, sinon false.
     * @throws RuntimeException Si une erreur survient lors désactive de l'utilisateur.
     */
    public static boolean descativeUtilisateur(Utilisateur utilisateur) {
        final String requete_desactive = "UPDATE utilisateur SET bloquer = ? WHERE id_utilisateur = ?";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_desactive)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, utilisateur.getId_utilisateur());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors lors désactive de l'utilisateur:", e);
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
     * Activer utilisateur dans la base de données.
     *
     * @param utilisateur L'utilisateur à désactiver
     * @return true si l'utilisateur a été désactiver avec succès, sinon false.
     * @throws RuntimeException Si une erreur survient lors désactive de l'utilisateur.
     */
    public static boolean activeUtilisateur(Utilisateur utilisateur) {
        final String requete_desactive = "UPDATE utilisateur SET bloquer = ? WHERE id_utilisateur = ?";

        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_desactive)) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setInt(2, utilisateur.getId_utilisateur());

                int rowCount = preparedStatement.executeUpdate();

                return rowCount > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors lors activation de l'utilisateur:", e);
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
     * Mettre à jour un utilisateur dans la base de données.
     *
     * @param utilisateur L'utilisateur à mettre à jour
     * @return true si l'utilisateur mis à jour avec succès, sinon false.
     * @throws RuntimeException Si une erreur survient lors de l'ajout de l'utilisateur.
     */

    public static boolean upUtilisateur(Utilisateur utilisateur) {
        final String requete_up = "UPDATE utilisateur set nom_utilisateur=?, prenom_utilisateur=?, annif_utilisateur=?, adresse_utilisateur=?, tel_utilisateur=? , mot_de_passe=? WHERE id_utilisateur=? ;";


        Connection connection = null;

        try {
            connection = ConnectionBD.getConnexion();
            try (PreparedStatement preparedStatement = connection.prepareStatement(requete_up)) {
                // Paramètres de la requête
                preparedStatement.setString(1, utilisateur.getNom_utilisateur());
                preparedStatement.setString(2, utilisateur.getPrenom_utilisateur());
                preparedStatement.setDate(3, new Date(utilisateur.getAnnif_utilisateur().getTime()));
                preparedStatement.setString(4, utilisateur.getAdresse_utilisateur());
                preparedStatement.setInt(5, utilisateur.getTel_utilisateur());
                preparedStatement.setString(6, utilisateur.getMot_de_passe());
                preparedStatement.setInt(7, utilisateur.getId_utilisateur());

                int rowCount = preparedStatement.executeUpdate();
                return rowCount > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de mis à jour de l'utilisateur:", e);
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
     * Mappe les données d'un ResultSet sur un objet Utilisateur.
     *
     * @param resultSet Le ResultSet contenant les données de l'utilisateur à mapper.
     * @return Un objet Utilisateur contenant les données extraites du ResultSet.
     * @throws SQLException Si une erreur survient lors de l'extraction des données à partir du ResultSet.
     */
    private static Utilisateur mapUtilisateur(ResultSet resultSet) throws SQLException {
        return new Utilisateur(
                resultSet.getInt("id_utilisateur"),
                resultSet.getString("nom_utilisateur"),
                resultSet.getString("prenom_utilisateur"),
                resultSet.getDate("annif_utilisateur"),
                resultSet.getString("adresse_utilisateur"),
                resultSet.getInt("tel_utilisateur"),
                resultSet.getString("identifiant"),
                resultSet.getString("mot_de_passe"),
                Utilisateur.Role.valueOf(resultSet.getString("role")),
                resultSet.getBoolean("actif"),
                resultSet.getBoolean("bloquer")
        );
    }


}
