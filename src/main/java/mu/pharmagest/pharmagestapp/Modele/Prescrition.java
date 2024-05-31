package mu.pharmagest.pharmagestapp.Modele;

import java.util.Date;

public class Prescrition {

    private Integer id_prescription;
    private String nom_medecin;
    private Date date_prescription;
    private String nom_patient;

//    Constructeurs

    public Prescrition() {
    }

    public Prescrition(Integer id_prescription, String nom_medecin, Date date_prescription, String nom_patient) {
        this.id_prescription = id_prescription;
        this.nom_medecin = nom_medecin;
        this.date_prescription = date_prescription;
        this.nom_patient = nom_patient;
    }
//    getters et setters

    public Integer getId_prescription() {
        return id_prescription;
    }

    public void setId_prescription(Integer id_prescription) {
        this.id_prescription = id_prescription;
    }

    public String getNom_medecin() {
        return nom_medecin;
    }

    public void setNom_medecin(String nom_medecin) {
        this.nom_medecin = nom_medecin;
    }

    public Date getDate_prescription() {
        return date_prescription;
    }

    public void setDate_prescription(Date date_prescription) {
        this.date_prescription = date_prescription;
    }

    public String getNom_patient() {
        return nom_patient;
    }

    public void setNom_patient(String nom_patient) {
        this.nom_patient = nom_patient;
    }
}
