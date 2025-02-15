# 📌 Modèle Relationnel Révisé

## 1️⃣ Utilisateur
| Champ         | Type           | Contraintes  |
|--------------|--------------|-------------|
| id          | INT | PRIMARY KEY, AUTO_INCREMENT |
| nom        | VARCHAR(50) | NOT NULL |
| prenom     | VARCHAR(50) | NOT NULL |
| identifiant | VARCHAR(50) | UNIQUE, NOT NULL |
| mot_de_passe | VARCHAR(255) | NOT NULL |
| role       | ENUM('ADMIN', 'CONTROLEUR') | NOT NULL |

## 2️⃣ Train
| Champ         | Type           | Contraintes  |
|--------------|--------------|-------------|
| id          | INT | PRIMARY KEY, AUTO_INCREMENT |
| nombre_wagons | INT | NOT NULL |
| type_train | ENUM('NIMBUS 4000', 'POUDLARD EXPRESS', 'POUDLARD EXPRESS 2.0') | NOT NULL |

## 3️⃣ Arret
| Champ         | Type           | Contraintes  |
|--------------|--------------|-------------|
| id          | INT | PRIMARY KEY, AUTO_INCREMENT |
| nom        | VARCHAR(100) | NOT NULL |
| type_arret | ENUM('DEPART', 'TERMINUS') | NOT NULL |

## 4️⃣ Trajet
| Champ         | Type           | Contraintes  |
|--------------|--------------|-------------|
| id          | INT | PRIMARY KEY, AUTO_INCREMENT |
| horaire_depart | DATETIME | NOT NULL |
| horaire_arrivee | DATETIME | NOT NULL |
| train_id    | INT | FOREIGN KEY → Train(id) |
| arret_depart_id | INT | FOREIGN KEY → Arret(id) |
| arret_arrivee_id | INT | FOREIGN KEY → Arret(id) |

## 5️⃣ Eleve
| Champ         | Type           | Contraintes  |
|--------------|--------------|-------------|
| id          | INT | PRIMARY KEY, AUTO_INCREMENT |
| nom        | VARCHAR(50) | NOT NULL |
| prenom     | VARCHAR(50) | NOT NULL |
| maison     | ENUM('SERDAIGLE', 'GRYFFONDOR', 'SERPENTARD', 'POUFSOUFFLE') | NOT NULL |

## 🔗 Clés Étrangères & Contraintes
- **Trajet** : Associe un train à un arrêt de départ et un arrêt d'arrivée.
- **Utilisateur** : Peut être **ADMIN** ou **CONTROLEUR** (ENUM `role`).
- **Élève** : Appartient à une **maison** (ENUM `maison`).
- **Train** : Classé selon différents **types de train**.

---

📌 **Ce modèle est maintenant parfaitement aligné avec ta BDD !** 🚀  
Tu peux l’intégrer dans la documentation du projet GitHub/GitLab.  

Dis-moi si tu veux des modifications ou si on passe à la suite ! 🔥
