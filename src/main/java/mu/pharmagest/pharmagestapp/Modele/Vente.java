package mu.pharmagest.pharmagestapp.Modele;

import java.util.Date;

public class Vente {

    private Integer id_vente;

    private Date date_vente;

    private Double prix_total;

    private Boolean payer;

    private Prescrition prescrition;

    //    Contructeurs
    public Vente() {
    }

    public Vente(Integer id_vente, Date date_vente, Double prix_total, Boolean payer, Prescrition prescrition) {
        this.id_vente = id_vente;
        this.date_vente = date_vente;
        this.prix_total = prix_total;
        this.payer = payer;
        this.prescrition = prescrition;
    }

    //getters et setters
    public Integer getId_vente() {
        return id_vente;
    }

    public void setId_vente(Integer id_vente) {
        this.id_vente = id_vente;
    }

    public Date getDate_vente() {
        return date_vente;
    }

    public void setDate_vente(Date date_vente) {
        this.date_vente = date_vente;
    }

    public Double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(Double prix_total) {
        this.prix_total = prix_total;
    }

    public Boolean getPayer() {
        return payer;
    }

    public void setPayer(Boolean payer) {
        this.payer = payer;
    }

    public Prescrition getPrescrition() {
        return prescrition;
    }

    public void setPrescrition(Prescrition prescrition) {
        this.prescrition = prescrition;
    }
}
