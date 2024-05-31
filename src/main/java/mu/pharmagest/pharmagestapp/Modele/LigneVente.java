package mu.pharmagest.pharmagestapp.Modele;

public class LigneVente {
    private Vente vente;
    private Medicament medicament;
    private Integer qt;

//    constructeurs

    public LigneVente() {
    }

    public LigneVente(Vente vente, Medicament medicament, Integer qt) {
        this.vente = vente;
        this.medicament = medicament;
        this.qt = qt;
    }

    //    getters et setters


    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Integer getQt() {
        return qt;
    }

    public void setQt(Integer qt) {
        this.qt = qt;
    }
}
