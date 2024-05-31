package mu.pharmagest.pharmagestapp.Modele;

//Pour panier de commande par envoyer à un fournisseur
public class LigneCommande {

    private Medicament medicament;

    private Commande commande;

    private Integer qt_medicament;

    private Integer qt_recu;

    //----Constructeur----
    public LigneCommande() {
    }

    public LigneCommande(Medicament medicament, Commande commande, Integer qt_medicament, Integer qt_recu) {
        this.medicament = medicament;
        this.commande = commande;
        this.qt_medicament = qt_medicament;
        this.qt_recu = qt_recu;
    }


    //-----Getters and Setters ------
    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Integer getQt_medicament() {
        return qt_medicament;
    }

    public void setQt_medicament(Integer qt_medicament) {
        this.qt_medicament = qt_medicament;
    }

    public Integer getQt_recu() {
        return qt_recu;
    }

    public void setQt_recu(Integer qt_recu) {
        this.qt_recu = qt_recu;
    }
}
