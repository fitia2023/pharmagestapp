package mu.pharmagest.pharmagestapp.LienBD.Services;


import mu.pharmagest.pharmagestapp.LienBD.DAO.CommandeDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.LigneCommandeDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.ListePrixDAO;
import mu.pharmagest.pharmagestapp.Modele.Commande;
import mu.pharmagest.pharmagestapp.Modele.LigneCommande;
import mu.pharmagest.pharmagestapp.Modele.ListePrix;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Pour pouvoir mettre a jour la base de donnee de commande recu
public class ReceptionCommandeService {


    public ReceptionCommandeService() {
    }

    // Obtenir toutes les lignes commande
    public static List<LigneCommande> getallcommande() throws SQLException {
        List<LigneCommande> ligneCommandes = new ArrayList<>();

        for (LigneCommande ligneCommande : LigneCommandeDAO.getallLigneCommande()) {
            Commande commande = ligneCommande.getCommande();
            if (commande != null && "En cours".equals(commande.getStatut())) {
                ligneCommandes.add(ligneCommande);
            }
        }
        return ligneCommandes;
    }


    //Obtenir la liste selon id de la commande
    public static List<LigneCommande> getallcommandebyid(Integer id) throws SQLException {

        Commande commande = CommandeDAO.getcommandebyid(id);
        List<LigneCommande> ligneCommandes = new ArrayList<>();

        for (LigneCommande ligneCommande:LigneCommandeDAO.getallLigneCommande()){
            if (ligneCommande.getCommande().getId_commande().equals(commande.getId_commande())){
                ligneCommandes.add(ligneCommande);
            }
        }

        return ligneCommandes;

    }

    //ListePrix par id medic et fournisseur
    public static ListePrix getListPrixbyid(LigneCommande ligneCommande) throws SQLException {
        return ListePrixDAO.getListPrixbyid(ligneCommande.getMedicament().getId_medicament(), ligneCommande.getCommande().getFournisseur().getId_fournisseur());
    }


    //Pour mettre en termine la commande avec qt recu
    public static Boolean terminercommande(LigneCommande ligneCommande) throws SQLException {
        Boolean reponse = false;
        if (LigneCommandeDAO.qtrecucommande(ligneCommande)){
            if (CommandeDAO.commandetermine(ligneCommande.getCommande().getId_commande(),ligneCommande.getCommande().getPrix_payer())){
                reponse = true;
            }
        }
        return reponse;
    }
}
