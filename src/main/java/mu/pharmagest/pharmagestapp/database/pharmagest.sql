-- Définir le schéma public comme schéma par défaut
SET search_path TO public;

CREATE TABLE IF NOT EXISTS Utilisateur (
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

CREATE TABLE IF NOT EXISTS Fournisseur (
    id_fournisseur SERIAL PRIMARY KEY,
    Nom_fournisseur VARCHAR,
    adresse_fournisseur VARCHAR,
    Tel_fournisseur INT
);

CREATE TABLE IF NOT EXISTS Medicament (
    id_medicament SERIAL PRIMARY KEY,
    nom_medicament VARCHAR,
    Famille VARCHAR,
    ordonnance BOOLEAN,
    prix_vente DOUBLE PRECISION,
    qt_stock INT,
    qt_min INT,
    qt_max INT,
    seuil_commande INT,
    unite VARCHAR,
    id_fournisseur INT REFERENCES Fournisseur(id_fournisseur)
);

CREATE TABLE IF NOT EXISTS Prescription (
    id_prescription SERIAL PRIMARY KEY,
    Nom_medecin VARCHAR,
    date_prescription DATE,
    Nom_patient VARCHAR
);

CREATE TABLE IF NOT EXISTS Vente (
    id_vente SERIAL PRIMARY KEY,
    date_vente DATE,
    prix_total DOUBLE PRECISION,
    payer BOOLEAN,
    id_prescription INT REFERENCES Prescription(id_prescription)
);

CREATE TABLE IF NOT EXISTS LigneVente (
    id_vente INT REFERENCES Vente(id_vente),
    id_medicament INT REFERENCES Medicament(id_medicament),
    qt INT,
    PRIMARY KEY (id_vente, id_medicament)
);

CREATE TABLE IF NOT EXISTS ListePrix (
    id_fournisseur INT REFERENCES Fournisseur(id_fournisseur),
    id_medicament INT REFERENCES Medicament(id_medicament),
    prix_unitaire DOUBLE PRECISION,
    qt INT,
    prix_vente DOUBLE PRECISION,
    PRIMARY KEY (id_fournisseur, id_medicament)
);



CREATE TABLE IF NOT EXISTS Commande (
    id_commande SERIAL PRIMARY KEY,
    date_commande DATE,
    prix_total DOUBLE PRECISION,
    id_fournisseur INT REFERENCES Fournisseur(id_fournisseur)
);

CREATE TABLE IF NOT EXISTS LigneCommande (
    id_medicament INT REFERENCES Medicament(id_medicament),
    id_commande INT REFERENCES Commande(id_commande),
    qt_vente INT,
    PRIMARY KEY (id_medicament, id_commande)
);

-- Exemple pour la table Utilisateur
INSERT INTO Utilisateur (Nom_utilisateur, Prenom_utilisateur, annif_utilisateur, adresse_utilisateur, tel_utilisateur, Identifiant, mot_de_passe, role, actif, bloquer)
VALUES ('Doe', 'John', '1990-01-01', '123 Rue de la République', 123456789, 'john.doe', 'snow', 'admin', true, false),
       ('Smith', 'Jane', '1985-05-15', '456 Avenue des Fleurs', 987654321, 'jane.smith', 'pass', 'caissier', true, false),
       ('Adam', 'Snow', '1985-05-15', '567 Avenue des Saint Jeans', 987654321, 'adam.snow', 'pass', 'caissier', true, false);

-- Exemple pour la table Fournisseur
INSERT INTO Fournisseur (Nom_fournisseur, adresse_fournisseur, Tel_fournisseur)
VALUES ('Fournisseur A', '789 Rue du Commerce', 555666777),
       ('Fournisseur B', '987 Avenue des Affaires', 111222333);

-- Exemple pour la table Medicament
INSERT INTO Medicament (nom_medicament, Famille, ordonnance, prix_vente, qt_stock, qt_min, qt_max, seuil_commande, unite, id_fournisseur)
VALUES ('Paracétamol', 'Antalgique', false, 5.99, 100, 20, 150, 30, 'boîte', 1),
       ('Aspirine', 'Anticoagulant', false, 3.49, 80, 15, 120, 25, 'boîte', 2);

-- Exemple pour la table Prescription
INSERT INTO Prescription (Nom_medecin, date_prescription, Nom_patient)
VALUES ('Dr. Smith', '2024-02-07', 'Alice Johnson'),
       ('Dr. Johnson', '2024-02-08', 'Bob Smith');

-- Exemple pour la table Vente
INSERT INTO Vente (date_vente, prix_total, payer, id_prescription)
VALUES ('2024-02-08', 15.99, true, 1),
       ('2024-02-09', 28.50, false, 2);

-- Exemple pour la table LigneVente
INSERT INTO LigneVente (id_vente, id_medicament, qt)
VALUES (1, 1, 2),
       (1, 2, 1),
       (2, 2, 3);

-- Exemple pour la table ListePrix
INSERT INTO ListePrix (id_fournisseur, id_medicament, prix_unitaire, qt, prix_vente)
VALUES (1, 1, 4.50, 50, 6.99),
       (2, 2, 2.99, 30, 3.49);

-- Exemple pour la table Commande
INSERT INTO Commande (date_commande, prix_total, id_fournisseur)
VALUES ('2024-02-10', 45.50, 1),
       ('2024-02-11', 30.99, 2);

-- Exemple pour la table LigneCommande
INSERT INTO LigneCommande (id_medicament, id_commande, qt_vente)
VALUES (1, 1, 20),
       (2, 1, 30),
       (2, 2, 10);
