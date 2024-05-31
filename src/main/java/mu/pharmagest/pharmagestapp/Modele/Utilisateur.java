package mu.pharmagest.pharmagestapp.Modele;

import java.util.Date;

/**
 * Cette classe est une table dans la base de données.
 * Elle fournit des attributs.
 */
public class Utilisateur {


    //Les users roles accessible
    public enum Role{
        pharmacien,
        vendeur,
        caissier
    }

    private Integer id_utilisateur;
    private String Nom_utilisateur;
    private String Prenom_utilisateur;
    private Date annif_utilisateur;
    private String adresse_utilisateur;
    private Integer tel_utilisateur;
    private String Identifiant;
    private String mot_de_passe;
    private Role role;
    private Boolean actif;
    private Boolean bloquer;


    //----Constructeurs
    public Utilisateur() {
    }

    public Utilisateur(Integer id_utilisateur, String nom_utilisateur, String prenom_utilisateur, Date annif_utilisateur, String adresse_utilisateur, Integer tel_utilisateur, String identifiant, String mot_de_passe, Role role, Boolean actif, Boolean bloquer) {
        this.id_utilisateur = id_utilisateur;
        this.Nom_utilisateur = nom_utilisateur;
        this.Prenom_utilisateur = prenom_utilisateur;
        this.annif_utilisateur = annif_utilisateur;
        this.adresse_utilisateur = adresse_utilisateur;
        this.tel_utilisateur = tel_utilisateur;
        this.Identifiant = identifiant;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.actif = actif;
        this.bloquer = bloquer;
    }

    //---Getters et Setters
    public Integer getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Integer id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom_utilisateur() {
        return Nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        Nom_utilisateur = nom_utilisateur;
    }

    public String getPrenom_utilisateur() {
        return Prenom_utilisateur;
    }

    public void setPrenom_utilisateur(String prenom_utilisateur) {
        Prenom_utilisateur = prenom_utilisateur;
    }

    public Date getAnnif_utilisateur() {
        return annif_utilisateur;
    }

    public void setAnnif_utilisateur(Date annif_utilisateur) {
        this.annif_utilisateur = annif_utilisateur;
    }

    public String getAdresse_utilisateur() {
        return adresse_utilisateur;
    }

    public void setAdresse_utilisateur(String adresse_utilisateur) {
        this.adresse_utilisateur = adresse_utilisateur;
    }

    public Integer getTel_utilisateur() {
        return tel_utilisateur;
    }

    public void setTel_utilisateur(Integer tel_utilisateur) {
        this.tel_utilisateur = tel_utilisateur;
    }

    public String getIdentifiant() {
        return Identifiant;
    }

    public void setIdentifiant(String identifiant) {
        Identifiant = identifiant;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Boolean getBloquer() {
        return bloquer;
    }

    public void setBloquer(Boolean bloquer) {
        this.bloquer = bloquer;
    }
}
