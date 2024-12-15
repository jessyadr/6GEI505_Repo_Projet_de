-- Table Role
CREATE TABLE IF NOT EXISTS Role (
    idRole INTEGER PRIMARY KEY AUTOINCREMENT,
    nomRole TEXT NOT NULL UNIQUE,
    descRole TEXT
);

-- Table Droit
CREATE TABLE IF NOT EXISTS Droit (
    idDroit INTEGER PRIMARY KEY AUTOINCREMENT,
    specification TEXT NOT NULL
);

-- Table RoleDroit (Relation n:n entre Role et Droit)
CREATE TABLE IF NOT EXISTS RoleDroit (
    idRole INTEGER NOT NULL,
    idDroit INTEGER NOT NULL,
    FOREIGN KEY (idRole) REFERENCES Role(idRole) ON DELETE CASCADE,
    FOREIGN KEY (idDroit) REFERENCES Droit(idDroit) ON DELETE CASCADE
);

-- Table Departement
CREATE TABLE IF NOT EXISTS Departement (
    idDepartement INTEGER PRIMARY KEY AUTOINCREMENT,
    nomDepartement TEXT NOT NULL
);

-- Table Employee
CREATE TABLE IF NOT EXISTS Employee (
    idEmployee INTEGER PRIMARY KEY AUTOINCREMENT,
    nomEmployee TEXT NOT NULL,
    prenomEmployee TEXT NOT NULL,
    motDePasse TEXT NOT NULL,
    idRole INTEGER NOT NULL,
    idDepartement INTEGER,
    FOREIGN KEY (idRole) REFERENCES Role(idRole),
    FOREIGN KEY (idDepartement) REFERENCES Departement(idDepartement)
);

-- Table Equipe
CREATE TABLE IF NOT EXISTS Equipe (
    idEquipe INTEGER PRIMARY KEY AUTOINCREMENT,
    idProjet INTEGER,
    etatEquipe TEXT,
    FOREIGN KEY (idProjet) REFERENCES ProjetSpecifique(idProjet)
);

-- Table EmployeeEquipe (Relation n:n entre Employee et Equipe)
CREATE TABLE IF NOT EXISTS EmployeeEquipe (
    idEmployee INTEGER NOT NULL,
    idEquipe INTEGER NOT NULL,
    FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee) ON DELETE CASCADE,
    FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe) ON DELETE CASCADE
);

-- Table Client
CREATE TABLE IF NOT EXISTS Client (
    idClient INTEGER PRIMARY KEY AUTOINCREMENT,
    nomClient TEXT NOT NULL,
    statusClient TEXT
);

-- Table ProjetSpecifique
CREATE TABLE IF NOT EXISTS ProjetSpecifique (
    idProjet INTEGER PRIMARY KEY AUTOINCREMENT,
    etatProjet TEXT NOT NULL,
    idClient INTEGER,
    nomProjet TEXT NOT NULL,
    coutProjet REAL,
    isTemplate BOOLEAN NOT NULL DEFAULT 0,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

-- Table Tache
CREATE TABLE IF NOT EXISTS Tache (
    idTache INTEGER PRIMARY KEY AUTOINCREMENT,
    idProjet INTEGER NOT NULL,
    etatTache TEXT NOT NULL,
    nomTache TEXT NOT NULL,
    descTache TEXT,
    tempsTache REAL,
    FOREIGN KEY (idProjet) REFERENCES ProjetSpecifique(idProjet)
);

-- Table SousTache
CREATE TABLE IF NOT EXISTS SousTache (
    idSousTache INTEGER PRIMARY KEY AUTOINCREMENT,
    idTache INTEGER NOT NULL,
    idTacheParent INTEGER,
    etatSousTache TEXT NOT NULL,
    nomSousTache TEXT NOT NULL,
    descSousTache TEXT,
    tempsSousTache REAL,
    FOREIGN KEY (idTache) REFERENCES Tache(idTache),
    FOREIGN KEY (idTacheParent) REFERENCES SousTache(idSousTache)
);

-- Table Etat
CREATE TABLE IF NOT EXISTS Etat (
    idEtat INTEGER PRIMARY KEY AUTOINCREMENT,
    nomEtat TEXT NOT NULL,
    dateDebutPrevue DATE,
    dateFinPrevue DATE,
    dateDebutReelle DATE,
    dateFinReelle DATE
);

-- Table Temps
CREATE TABLE IF NOT EXISTS Temps (
    idTemps INTEGER PRIMARY KEY AUTOINCREMENT,
    idEmployee INTEGER NOT NULL,
    idTache INTEGER NOT NULL,
    dateSaisie DATE NOT NULL,
    heuresSaisies REAL NOT NULL,
    FOREIGN KEY (idEmployee) REFERENCES Employee(idEmployee),
    FOREIGN KEY (idTache) REFERENCES Tache(idTache)
);


