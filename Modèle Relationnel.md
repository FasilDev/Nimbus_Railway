# Modèle Relationnel

## Tables et Relations

### Utilisateur
- **id** (INT, PRIMARY KEY, AUTO_INCREMENT)
- **nom** (VARCHAR)
- **prenom** (VARCHAR)
- **identifiant** (VARCHAR, UNIQUE)
- **mot_de_passe** (VARCHAR)
- **role** (ENUM: 'ADMIN', 'CONTROLEUR')

### Trajet
- **id** (INT, PRIMARY KEY, AUTO_INCREMENT)
- **horaire_depart** (DATETIME)
- **horaire_arrivee** (DATETIME)
- **train_id** (INT, FOREIGN KEY → Train(id))
- **arret_depart_id** (INT, FOREIGN KEY → Arret(id))
- **arret_arrivee_id** (INT, FOREIGN KEY → Arret(id))

### Train
- **id** (INT, PRIMARY KEY, AUTO_INCREMENT)
- **nombre_wagons** (INT)
- **type_train** (ENUM: 'NIMBUS 4000', 'POUDLARD EXPRESS', 'POUDLARD EXPRESS 2.0')

### Arret
- **id** (INT, PRIMARY KEY, AUTO_INCREMENT)
- **nom** (VARCHAR)
- **type_arret** (ENUM: 'DEPART', 'TERMINUS')

### Eleve
- **id** (INT, PRIMARY KEY, AUTO_INCREMENT)
- **nom** (VARCHAR)
- **prenom** (VARCHAR)
- **maison** (ENUM: 'SERDAIGLE', 'GRYFFONDOR', 'SERPENTARD', 'POUFSOUFFLE')

## Clés Étrangères & Contraintes
- **Trajet** : Relie un train à un arrêt de départ et un arrêt d'arrivée.
- **Utilisateur** : Les utilisateurs peuvent être des **Admins** ou des **Contrôleurs** via l'énumération `role`.
- **Élève** : Chaque élève appartient à une **maison** (Gryffondor, etc.).
- **Train** : Classé selon différents **types de train**.
