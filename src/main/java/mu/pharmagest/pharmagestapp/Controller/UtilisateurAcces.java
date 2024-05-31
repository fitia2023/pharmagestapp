package mu.pharmagest.pharmagestapp.Controller;


import mu.pharmagest.pharmagestapp.Modele.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurAcces {

    public static List<String> getAutorisations(Utilisateur.Role role){
        List<String> autorisations = new ArrayList<>();
        switch (role){
            case pharmacien:
                autorisations.add("appro");
                autorisations.add("maintenance");
                autorisations.add("approvisionnement");
                autorisations.add("fournisseur");
                autorisations.add("medicament");
                autorisations.add("inventaire");
                autorisations.add("caisse");
                autorisations.add("vente");
                break;
            case  vendeur:
                autorisations.add("vente");
                break;
            case caissier:
                autorisations.add("caisse");
                break;
        }
        return autorisations;
    }

}
