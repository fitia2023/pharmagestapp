package mu.pharmagest.pharmagestapp.LienBD.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mu.pharmagest.pharmagestapp.Controller.Vente.ModelVente;
import mu.pharmagest.pharmagestapp.LienBD.DAO.LigneVenteDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.MedicamentDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.PrescriptionDAO;
import mu.pharmagest.pharmagestapp.LienBD.DAO.VenteDAO;
import mu.pharmagest.pharmagestapp.Modele.LigneVente;
import mu.pharmagest.pharmagestapp.Modele.Medicament;
import mu.pharmagest.pharmagestapp.Modele.Vente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaisseService {

    public CaisseService() {
    }

    public static List<Vente> getallventenonpayer() throws SQLException {
        List<Vente> ventes = new ArrayList<>();
        for (Vente vente: VenteDAO.getallvente()) {
            if (!vente.getPayer()){
                ventes.add(vente);
            }
        }
        return ventes;
    }

    public static List<ModelVente> getallmodelvente(Vente vente) throws SQLException {
        List<ModelVente> modelVentes = new ArrayList<>();
        int id_vente = vente.getId_vente();
        for (LigneVente lignevente: LigneVenteDAO.getLigneVentesById(id_vente) ) {
            modelVentes.add(
                    new ModelVente(
                            lignevente.getMedicament(),
                            lignevente.getMedicament().getPrix_vente(),
                            lignevente.getQt(),
                            vente.getPrix_total()
                    )
            );
        }
        return modelVentes;
    }

    public static ObservableList<Vente> getallventenonpayerId(int id_vente) throws SQLException {
        Vente vente = VenteDAO.getVenteById(id_vente);
        ObservableList<Vente> ventes = FXCollections.observableArrayList();
        if (vente != null && !vente.getPayer()) {
            ventes.add(vente);
        }
        return ventes;
    }
    public static boolean delventepharmacien(int id_vente) throws SQLException {
        if (LigneVenteDAO.deleteVenteLigne(id_vente)) {

                if (!VenteDAO.deleteVente(id_vente)) {
                    return false;
                }
            return true;
        }
        return false;
    }

    public static boolean payervente(Vente vente) throws SQLException {
        if (VenteDAO.upventepayer(vente)) {
            for (ModelVente modelVente : getallmodelvente(vente)) {
                Medicament medicament = modelVente.getMedicament();
                int qt_stock = medicament.getQt_stock();
                qt_stock -= modelVente.getQt();
                medicament.setQt_stock(qt_stock);
                if (!MedicamentDAO.updateMedicament(medicament)) {
                    return false; // Si la mise à jour du stock échoue, retourner false
                }
            }
            return true; // Si toutes les mises à jour du stock réussissent, retourner true
        }
        return false; // Si la mise à jour de la vente échoue, retourner false
    }

}
