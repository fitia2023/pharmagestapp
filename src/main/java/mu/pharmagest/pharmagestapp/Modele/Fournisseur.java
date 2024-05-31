package mu.pharmagest.pharmagestapp.Modele;


/**
 * Classe pour fournisseur
 */
public class Fournisseur {

   private Integer id_fournisseur;
   private String Nom_fournisseur;
   private String adresse_fournisseur;
   private Integer Tel_fournisseur;

   //Constructeurs

    public Fournisseur(Integer id_fournisseur, String nom_fournisseur, String adresse_fournisseur, Integer tel_fournisseur) {
        this.id_fournisseur = id_fournisseur;
        Nom_fournisseur = nom_fournisseur;
        this.adresse_fournisseur = adresse_fournisseur;
        Tel_fournisseur = tel_fournisseur;
    }

    public Fournisseur() {
    }

    //---Getters and setters

    public Integer getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(Integer id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getNom_fournisseur() {
        return Nom_fournisseur;
    }

    public void setNom_fournisseur(String nom_fournisseur) {
        Nom_fournisseur = nom_fournisseur;
    }

    public String getAdresse_fournisseur() {
        return adresse_fournisseur;
    }

    public void setAdresse_fournisseur(String adresse_fournisseur) {
        this.adresse_fournisseur = adresse_fournisseur;
    }

    public Integer getTel_fournisseur() {
        return Tel_fournisseur;
    }

    public void setTel_fournisseur(Integer tel_fournisseur) {
        Tel_fournisseur = tel_fournisseur;
    }
}
