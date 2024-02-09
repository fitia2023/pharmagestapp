CREATE DATABASE pharmagest
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

USE pharmagest;

CREATE TABLE Utilisateur (
    id_utilisateur SERIAL PRIMARY KEY,
    Nom_utilisateur VARCHAR,
    Prenom_utilisateur VARCHAR,
    annif_utilisateur DATE,
    adresse_utilisateur VARCHAR,
    tel_utilisateur INT,
    Identifiant VARCHAR,
    mot_de_passe VARCHAR,
    role VARCHAR,
    actif BOOLEAN,
    bloquer BOOLEAN
);
CREATE TABLE Vendeur{
    -- Ceci doit extend Utilisateur
};
CREATE TABLE Caissier{
    -- Ceci doit extend Utilisateur

};
CREATE TABLE Pharmacien{
    -- Ceci doit extend Utilisateur

};

CREATE TABLE Fournisseur (
    id_fournisseur SERIAL PRIMARY KEY,
    Nom_fournisseur VARCHAR,
    adresse_fournisseur VARCHAR,
    Tel_fournisseur INT
);

CREATE TABLE Medicament (
    id_medicament SERIAL PRIMARY KEY,
    nom_medicament VARCHAR,
    Famille VARCHAR,
    ordonnance BOOLEAN,
    prix_vente DOUBLE,
    qt_stock INT,
    qt_min INT,
    qt_max INT,
    seuil_commande INT,
    unite VARCHAR,
    id_fournisseur INT,
    FOREIGN KEY (id_fournisseur) REFERENCES Fournisseur(id_fournisseur)
);
CREATE TABLE Vente (
    id_vente SERIAL PRIMARY KEY,
    date_vente DATE,
    prix_total DOUBLE,
    payer BOOLEAN,
    id_prescription INT,
    FOREIGN KEY (id_prescription) REFERENCES Prescription(id_prescription)
);

CREATE TABLE LigneVente (
    id_vente INT PRIMARY KEY,
    id_medicament INT PRIMARY KEY,
    qt INT,
    FOREIGN KEY (id_vente) REFERENCES Vente(id_vente),
    FOREIGN KEY (id_medicament) REFERENCES Medicament(id_medicament)
);

CREATE TABLE ListePrix (
    id_fournisseur INT PRIMARY KEY,
    id_medicament INT PRIMARY KEY,
    prix_unitaire DOUBLE,
    qt INT,
    prix_vente DOUBLE
    FOREIGN KEY (id_fournisseur) REFERENCES Fournisseur(id_fournisseur),
    FOREIGN KEY (id_medicament) REFERENCES Medicament(id_medicament)

);

CREATE TABLE Prescription (
    id_prescription SERIAL PRIMARY KEY,
    Nom_medecin VARCHAR,
    date_prescription DATE,
    Nom_patient VARCHAR(25)
);

CREATE TABLE Commande (
    id_commande SERIAL PRIMARY KEY,
    date_commande DATE,
    prix_total DOUBLE,
    id_fournisseur INT,
    FOREIGN KEY (id_fournisseur) REFERENCES Fournisseur(id_fournisseur)
);

CREATE TABLE LigneCommande (
    id_medicament INT PRIMARY KEY,
    id_commande INT PRIMARY KEY,
    qt_vente INT,
    FOREIGN KEY (id_medicament) REFERENCES Medicament(id_medicament),
    FOREIGN KEY (id_commande) REFERENCES Commande(id_commande)
);

