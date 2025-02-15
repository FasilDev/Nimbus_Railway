-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : sam. 15 fév. 2025 à 19:51
-- Version du serveur : 5.7.39
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `Nimbus_Railway`
--

-- --------------------------------------------------------

--
-- Structure de la table `Arret`
--

CREATE TABLE `Arret` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `type_arret` enum('DEPART','TERMINUS') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Arret`
--

INSERT INTO `Arret` (`id`, `nom`, `type_arret`) VALUES
(1, 'Gare de Londres', 'DEPART'),
(2, 'Poudlard', 'TERMINUS');

-- --------------------------------------------------------

--
-- Structure de la table `Eleve`
--

CREATE TABLE `Eleve` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `maison` enum('SERDAIGLE','GRYFFONDOR','SERPENTARD','POUFSOUFFLE') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Eleve`
--

INSERT INTO `Eleve` (`id`, `nom`, `prenom`, `maison`) VALUES
(1, 'Potter', 'Harry', 'GRYFFONDOR'),
(2, 'Malfoy', 'Draco', 'SERPENTARD');

-- --------------------------------------------------------

--
-- Structure de la table `Train`
--

CREATE TABLE `Train` (
  `id` int(11) NOT NULL,
  `nombre_wagons` int(11) NOT NULL,
  `type_train` enum('NIMBUS 4000','POUDLARD EXPRESS','POUDLARD EXPRESS 2.0') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Train`
--

INSERT INTO `Train` (`id`, `nombre_wagons`, `type_train`) VALUES
(1, 8, 'POUDLARD EXPRESS'),
(2, 6, 'NIMBUS 4000');

-- --------------------------------------------------------

--
-- Structure de la table `Trajet`
--

CREATE TABLE `Trajet` (
  `id` int(11) NOT NULL,
  `horaire_depart` datetime NOT NULL,
  `horaire_arrivee` datetime NOT NULL,
  `train_id` int(11) NOT NULL,
  `arret_depart_id` int(11) NOT NULL,
  `arret_arrivee_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Trajet`
--

INSERT INTO `Trajet` (`id`, `horaire_depart`, `horaire_arrivee`, `train_id`, `arret_depart_id`, `arret_arrivee_id`) VALUES
(1, '2024-09-01 09:00:00', '2024-09-01 12:00:00', 1, 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `identifiant` varchar(255) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role` enum('ADMIN','CONTROLEUR') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`id`, `nom`, `prenom`, `identifiant`, `mot_de_passe`, `role`) VALUES
(1, 'Doe', 'John', 'jdoe', 'password123', 'ADMIN'),
(2, 'Smith', 'Alice', 'asmith', 'securepass', 'CONTROLEUR');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Arret`
--
ALTER TABLE `Arret`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Eleve`
--
ALTER TABLE `Eleve`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Train`
--
ALTER TABLE `Train`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Trajet`
--
ALTER TABLE `Trajet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `train_id` (`train_id`),
  ADD KEY `arret_depart_id` (`arret_depart_id`),
  ADD KEY `arret_arrivee_id` (`arret_arrivee_id`);

--
-- Index pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `identifiant` (`identifiant`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Arret`
--
ALTER TABLE `Arret`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Eleve`
--
ALTER TABLE `Eleve`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Train`
--
ALTER TABLE `Train`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Trajet`
--
ALTER TABLE `Trajet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Trajet`
--
ALTER TABLE `Trajet`
  ADD CONSTRAINT `trajet_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `Train` (`id`),
  ADD CONSTRAINT `trajet_ibfk_2` FOREIGN KEY (`arret_depart_id`) REFERENCES `Arret` (`id`),
  ADD CONSTRAINT `trajet_ibfk_3` FOREIGN KEY (`arret_arrivee_id`) REFERENCES `Arret` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
