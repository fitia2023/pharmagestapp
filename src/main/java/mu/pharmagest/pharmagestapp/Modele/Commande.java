package mu.pharmagest.pharmagestapp.Modele;

import java.util.Date;

/**
 * Class pour pouvoir faire passer la commande de medicament dans
 */
public class Commande {

    private Integer id_commande;

    private Date date_commande;

    private Double prix_total;

    private Fournisseur fournisseur;

    private Double prix_payer;

    private String statut;


    //------Constructeur-----
    public Commande() {
    }

    public Commande(Integer id_commande, Date date_commande, Double prix_total, Fournisseur fournisseur, Double prix_payer, String statut) {
        this.id_commande = id_commande;
        this.date_commande = date_commande;
        this.prix_total = prix_total;
        this.fournisseur = fournisseur;
        this.prix_payer = prix_payer;
        this.statut = statut;
    }


    //------Getters and Setters ------

    public Integer getId_commande() {
        return id_commande;
    }

    public void setId_commande(Integer id_commande) {
        this.id_commande = id_commande;
    }

    public Date getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(Date date_commande) {
        this.date_commande = date_commande;
    }

    public Double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(Double prix_total) {
        this.prix_total = prix_total;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Double getPrix_payer() {
        return prix_payer;
    }

    public void setPrix_payer(Double prix_payer) {
        this.prix_payer = prix_payer;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
