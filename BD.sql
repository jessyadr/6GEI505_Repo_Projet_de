CREATE DATABASE IF NOT EXISTS gestion_projet;
USE gestion_projet;

-- Table Role
CREATE TABLE IF NOT EXISTS Role (
    idRole INT PRIMARY KEY AUTO_INCREMENT,
    nomRole VARCHAR(100) NOT NULL,
    descRole TEXT
);

-- Table Departement
CREATE TABLE IF NOT EXISTS Departement (
    idDepartement INT PRIMARY KEY AUTO_INCREMENT,
    nomDepartement VARCHAR(100) NOT NULL
);

-- Table Droit
CREATE TABLE IF NOT EXISTS Droit (
    idDroit INT PRIMARY KEY AUTO_INCREMENT,
    specification VARCHAR(100),
    view BOOLEAN,
    `write` BOOLEAN,
    `delete` BOOLEAN
);

-- Table Employee
CREATE TABLE IF NOT EXISTS Employee (
    idEmployee INT PRIMARY KEY AUTO_INCREMENT,
    idRole INT,
    idDepartement INT,
    nomEmployee VARCHAR(100) NOT NULL,
    prenomEmployee VARCHAR(100) NOT NULL,
    motdePasse VARCHAR(100) NOT NULL,
    FOREIGN KEY (idRole) REFERENCES Role(idRole),
    FOREIGN KEY (idDepartement) REFERENCES Departement(idDepartement)
);

-- Table Client
CREATE TABLE IF NOT EXISTS Client (
    idClient INT PRIMARY KEY AUTO_INCREMENT,
    nomClient VARCHAR(100) NOT NULL,
    statusClient VARCHAR(50)
);

-- Table ProjetSpecifique
CREATE TABLE IF NOT EXISTS ProjetSpecifique (
    idProjet INT PRIMARY KEY AUTO_INCREMENT,
    etatProjet INT,
    idClient INT,
    tempsProjet INT,
    nomProjet VARCHAR(100),
    coutProjet DECIMAL(10, 2),
    isTemplate BOOLEAN,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

-- Table Etat
CREATE TABLE IF NOT EXISTS Etat (
    idEtat INT PRIMARY KEY AUTO_INCREMENT,
    nomEtat VARCHAR(50),
    dateDebutPrevue DATE,
    dateFinPrevue DATE,
    dateDebutReelle DATE,
    dateFinReelle DATE
);

-- Table Tache
CREATE TABLE IF NOT EXISTS Tache (
    idTache INT PRIMARY KEY AUTO_INCREMENT,
    idProjet INT,
    idEtat INT,
    membreAssigner INT,
    nomTache VARCHAR(100),
    descTache TEXT,
    tempsTache INT,
    FOREIGN KEY (idProjet) REFERENCES ProjetSpecifique(idProjet),
    FOREIGN KEY (idEtat) REFERENCES Etat(idEtat)
);

-- Table SousTache
CREATE TABLE IF NOT EXISTS SousTache (
    idSousTache INT PRIMARY KEY AUTO_INCREMENT,
    idTache INT,
    idTacheParent INT,
    etatSousTache INT,
    descSousTache TEXT,
    tempsSousTache INT,
    nomSousTache VARCHAR(100),
    FOREIGN KEY (idTache) REFERENCES Tache(idTache),
    FOREIGN KEY (idTacheParent) REFERENCES Tache(idTache)
);

-- Table Templates
CREATE TABLE IF NOT EXISTS Templates (
    idTemplate INT PRIMARY KEY AUTO_INCREMENT,
    tempsTemplate INT,
    coutsTemplate DECIMAL(10, 2),
    descTemplate TEXT,
    nomTemplate VARCHAR(100)
);

-- Table TemplateTache
CREATE TABLE IF NOT EXISTS TemplateTache (
    idTemplateTache INT PRIMARY KEY AUTO_INCREMENT,
    idTemplate INT,
    descTemplateTache TEXT,
    tempsTemplateTache INT,
    nomTemplateTache VARCHAR(100),
    FOREIGN KEY (idTemplate) REFERENCES Templates(idTemplate)
);

-- Table TemplateSousTache
CREATE TABLE IF NOT EXISTS TemplateSousTache (
    idTemplateSousTache INT PRIMARY KEY AUTO_INCREMENT,
    idTemplateTache INT,
    descTemplateSousTache TEXT,
    tempsSousTache INT,
    nomTemplateSousTache VARCHAR(100),
    FOREIGN KEY (idTemplateTache) REFERENCES TemplateTache(idTemplateTache)
);

-- Table Temps
CREATE TABLE IF NOT EXISTS Temps (
    idTemps INT PRIMARY KEY AUTO_INCREMENT,
    idEmployee INT,
    idTache INT,
    dateSaisie DATE,
    heuresSaisies INT,
    FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee),
    FOREIGN KEY (idTache) REFERENCES Tache(idTache)
);

-- Table Equipe
CREATE TABLE IF NOT EXISTS Equipe (
    idEquipe INT PRIMARY KEY AUTO_INCREMENT,
    idProjet INT,
    etatEquipe VARCHAR(50),
    FOREIGN KEY (idProjet) REFERENCES ProjetSpecifique(idProjet)
);

-- Table EmployeeEquipe
CREATE TABLE IF NOT EXISTS EmployeeEquipe (
    idEmployee INT,
    idEquipe INT,
    PRIMARY KEY (idEmployee, idEquipe),
    FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee),
    FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe)
);

-- Table RoleDroit
CREATE TABLE IF NOT EXISTS RoleDroit (
    idRole INT,
    idDroit INT,
    PRIMARY KEY (idRole, idDroit),
    FOREIGN KEY (idRole) REFERENCES Role(idRole),
    FOREIGN KEY (idDroit) REFERENCES Droit(idDroit)
);
