package mu.pharmagest.pharmagestapp.Modele;

/**
 * La liste de prix des fournisseurs
 */
public class ListePrix {

    private Fournisseur fournisseur;

    private Medicament medicament;

    private Double prix_unitaire_achat;

    private Integer qt_min_commande;

    private Double prix_vente;

    //Constructeurs
    public ListePrix(Fournisseur fournisseur, Medicament medicament, Double prix_unitaire_achat, Integer qt_min_commande, Double prix_vente) {
        this.fournisseur = fournisseur;
        this.medicament = medicament;
        this.prix_unitaire_achat = prix_unitaire_achat;
        this.qt_min_commande = qt_min_commande;
        this.prix_vente = prix_vente;
    }

    public ListePrix() {
    }

    //--Getters and setters--

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Double getPrix_unitaire_achat() {
        return prix_unitaire_achat;
    }

    public void setPrix_unitaire_achat(Double prix_unitaire_achat) {
        this.prix_unitaire_achat = prix_unitaire_achat;
    }

    public Integer getQt_min_commande() {
        return qt_min_commande;
    }

    public void setQt_min_commande(Integer qt_min_commande) {
        this.qt_min_commande = qt_min_commande;
    }

    public Double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(Double prix_vente) {
        this.prix_vente = prix_vente;
    }
}
