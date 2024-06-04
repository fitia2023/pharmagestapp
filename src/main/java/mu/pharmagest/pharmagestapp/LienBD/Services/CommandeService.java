package mu.pharmagest.pharmagestapp.LienBD.Services;


import mu.pharmagest.pharmagestapp.LienBD.DAO.*;
import mu.pharmagest.pharmagestapp.Modele.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeService {

    public CommandeService() {
    }

    //Ajout de la commande avec ligne
    public static Boolean addLCommande(LigneCommande ligneCommande) throws SQLException {
        Boolean reponse = false;
        Commande commande = ligneCommande.getCommande();

        // Vérifier si une commande avec le même ID existe déjà
        if (commandeExists(commande.getId_commande())) {
            if (LigneCommandeDAO.addLigneCommande(ligneCommande)) {
                reponse = true;
            }
        } else {
            // Ajouter la commande
            if (CommandeDAO.addCommande(ligneCommande.getCommande())) {
                // Ajouter la ligne de commande
                if (LigneCommandeDAO.addLigneCommande(ligneCommande)) {
                    reponse = true;
                }
            }
        }

        return reponse;
    }

    // Méthode pour vérifier si une commande avec le même ID existe déjà
    private static boolean commandeExists(int idCommande) throws SQLException {
        for (Commande commande : CommandeDAO.getallcommande()) {
            if (commande.getId_commande() == idCommande) {
                return true;
            }
        }
        return false;
    }


    //Avoir la liste des medicaments en dessous du seuil
    public static List<Medicament> suggestioncommande() throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        for (Medicament medicament : MedicamentDAO.getallmedicament()) {
            if (medicament.getQt_stock() < medicament.getSeuil_commande()) {
                medicaments.add(medicament);
            }
        }
        return medicaments;
    }

    //Obtenir le dernier commande
    public static Integer getLastIdCommande() throws SQLException {
        return CommandeDAO.getLastIdCommande();
    }

    //Tous les midicaments
    public static List<Medicament> getallmedicament() throws SQLException {
        return MedicamentDAO.getallmedicament();
    }

    //Tous les fournisseurs
    public static List<Fournisseur> getallfournisseur() throws SQLException {
        return FournisseurDAO.getallfournisseurs();
    }

    //Liste fournisseur par un medicament
    public static List<ListePrix> getallFournisseurbyid_medicament(Integer id_medicament) throws SQLException {
        return ListePrixDAO.listefournisseurparidmedicament(id_medicament);
    }

    //Liste medicament par nom
    public static List<Medicament> getallmedicmanetbyname(String nom_medicament) throws SQLException {
        return MedicamentDAO.getMedicamentsByName(nom_medicament);
    }

    //ListePrix par id medic et fournisseur
    public static ListePrix getListPrixbyid(Integer id_medicament, Integer id_fournisseur) throws SQLException {
        return ListePrixDAO.getListPrixbyid(id_medicament, id_fournisseur);
    }

    //Obtenir fournisseur par id fournisseur
    public static Fournisseur getfournisseurbyid(Integer id_fournisseur) throws SQLException {
        return FournisseurDAO.getFournisseursById(id_fournisseur);
    }

    //Obtenir fournisseur par nom
    public static List<Fournisseur> getallfournisseurbyname(String nom_fournisseur) throws SQLException {
        return FournisseurDAO.getFournisseursByName(nom_fournisseur);
    }
}
