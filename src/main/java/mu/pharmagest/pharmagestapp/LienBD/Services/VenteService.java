package mu.pharmagest.pharmagestapp.LienBD.Services;

import mu.pharmagest.pharmagestapp.LienBD.ConnectionBD;
import mu.pharmagest.pharmagestapp.LienBD.DAO.LigneVenteDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.PrescriptionDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.VenteDAO;
import mu.pharmagest.pharmagestapp.Modele.LigneVente;
import mu.pharmagest.pharmagestapp.Modele.Prescrition;
import mu.pharmagest.pharmagestapp.Modele.Vente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VenteService {



    public VenteService() {
    }

    public static Integer getNumPrescriSuivant() throws SQLException {

        Integer nbr = PrescriptionDAO.getallprescription().get(
                PrescriptionDAO.getallprescription().size() - 1
        ).getId_prescription();
        nbr += 1;
        return nbr;
    }

    public static Integer getNumVenteSuivant() throws SQLException {

        Integer nbr = VenteDAO.getallvente().get(
                VenteDAO.getallvente().size() - 1
        ).getId_vente();
        nbr += 1;
        return nbr;
    }

    public static boolean addventePrescription(Prescrition prescrition, Vente vente, List<LigneVente> ligneVentes) throws SQLException {
        boolean add = false;

        // Ajoutez la prescription
        if (PrescriptionDAO.addPrescription(prescrition)) {
            // Ajoutez la vente
            if (VenteDAO.addVentePrescription(vente)) {
                for (LigneVente ligneVente : ligneVentes) {
                    if (LigneVenteDAO.addLigneVente(ligneVente)) {
                        add = true;
                    }
                }
            }
        }

        return add;
    }

    public static boolean addvente(Vente vente, List<LigneVente> ligneVentes) throws SQLException {

        boolean add = false;

        // Ajoutez la vente
        if (VenteDAO.addVente(vente)) {
            for (LigneVente ligneVente : ligneVentes) {
                if (LigneVenteDAO.addLigneVente(ligneVente)) {
                    add = true;
                }
            }
        }

        return add;
    }
}
