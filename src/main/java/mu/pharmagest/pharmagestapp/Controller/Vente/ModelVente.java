package mu.pharmagest.pharmagestapp.Controller.Vente;

import mu.pharmagest.pharmagestapp.Modele.Medicament;

public class ModelVente {
    private Medicament medicament;
    private Double price_vente;

    private Integer qt;
    private Double price;

    public ModelVente(Medicament medicament, Double price_vente, Integer qt, Double price) {
        this.medicament = medicament;
        this.price_vente = price_vente;
        this.qt = qt;
        this.price = price;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Double getPrice_vente() {
        return price_vente;
    }

    public void setPrice_vente(Double price_vente) {
        this.price_vente = price_vente;
    }

    public Integer getQt() {
        return qt;
    }

    public void setQt(Integer qt) {
        this.qt = qt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}