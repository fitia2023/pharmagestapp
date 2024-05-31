package mu.pharmagest.pharmagestapp.Modele;

public class Medicament {

    private Integer id_medicament;

    private String nom_medicament;

    private String famille;

    private Boolean ordonnance;

    private Double prix_vente;

    private Integer qt_stock;

    private Integer qt_min;

    private Integer qt_max;

    private Integer seuil_commande;

    private String unite;

    private Fournisseur fournisseur_habituel;

    //Constructeurs
    public Medicament(Integer id_medicament, String nom_medicament, String famille, Boolean ordonnance, Double prix_vente, Integer qt_stock, Integer qt_min, Integer qt_max, Integer seuil_commande, String unite, Fournisseur fournisseur_habituel) {
        this.id_medicament = id_medicament;
        this.nom_medicament = nom_medicament;
        this.famille = famille;
        this.ordonnance = ordonnance;
        this.prix_vente = prix_vente;
        this.qt_stock = qt_stock;
        this.qt_min = qt_min;
        this.qt_max = qt_max;
        this.seuil_commande = seuil_commande;
        this.unite = unite;
        this.fournisseur_habituel = fournisseur_habituel;
    }

    public Medicament() {
    }


    //---Getters and Setters

    public Integer getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(Integer id_medicament) {
        this.id_medicament = id_medicament;
    }

    public String getNom_medicament() {
        return nom_medicament;
    }

    public void setNom_medicament(String nom_medicament) {
        this.nom_medicament = nom_medicament;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public Boolean getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Boolean ordonnance) {
        this.ordonnance = ordonnance;
    }

    public Double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(Double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public Integer getQt_stock() {
        return qt_stock;
    }

    public void setQt_stock(Integer qt_stock) {
        this.qt_stock = qt_stock;
    }

    public Integer getQt_min() {
        return qt_min;
    }

    public void setQt_min(Integer qt_min) {
        this.qt_min = qt_min;
    }

    public Integer getQt_max() {
        return qt_max;
    }

    public void setQt_max(Integer qt_max) {
        this.qt_max = qt_max;
    }

    public Integer getSeuil_commande() {
        return seuil_commande;
    }

    public void setSeuil_commande(Integer seuil_commande) {
        this.seuil_commande = seuil_commande;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Fournisseur getFournisseur_habituel() {
        return fournisseur_habituel;
    }

    public void setFournisseur_habituel(Fournisseur fournisseur_habituel) {
        this.fournisseur_habituel = fournisseur_habituel;
    }
}
