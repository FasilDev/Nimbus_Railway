-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : ven. 21 fév. 2025 à 15:47
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
  `id` int(3) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `type_arret` enum('DEPART','TERMINUS') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Eleve`
--

CREATE TABLE `Eleve` (
  `id` int(3) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `maison` enum('SERDAIGLE','GRYFFONDOR','SERPENTARD','POUFSOUFFLE') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Train`
--

CREATE TABLE `Train` (
  `id` int(3) NOT NULL,
  `nombre_wagons` int(11) NOT NULL,
  `type_train` enum('NIMBUS 4000','POUDLARD EXPRESS','POUDLARD EXPRESS 2.0') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Trajet`
--

CREATE TABLE `Trajet` (
  `id` int(3) NOT NULL,
  `horaire_depart` datetime NOT NULL,
  `horaire_arrivee` datetime NOT NULL,
  `train_id` int(3) NOT NULL,
  `arret_depart_id` int(3) NOT NULL,
  `arret_arrivee_id` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Trajet_élève`
--

CREATE TABLE `Trajet_élève` (
  `id` int(3) NOT NULL,
  `id_élève` int(3) NOT NULL,
  `id_trajet` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `id` int(3) NOT NULL,
  `identifiant` varchar(50) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `role` enum('ADMIN','CONTROLEUR') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Arret`
--
ALTER TABLE `Arret`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nom` (`nom`);

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
-- Index pour la table `Trajet_élève`
--
ALTER TABLE `Trajet_élève`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_élève` (`id_élève`),
  ADD KEY `id_trajet` (`id_trajet`);

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
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Eleve`
--
ALTER TABLE `Eleve`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Train`
--
ALTER TABLE `Train`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Trajet`
--
ALTER TABLE `Trajet`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Trajet_élève`
--
ALTER TABLE `Trajet_élève`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;

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

--
-- Contraintes pour la table `Trajet_élève`
--
ALTER TABLE `Trajet_élève`
  ADD CONSTRAINT `trajet_élève_ibfk_1` FOREIGN KEY (`id_élève`) REFERENCES `Eleve` (`id`),
  ADD CONSTRAINT `trajet_élève_ibfk_2` FOREIGN KEY (`id_trajet`) REFERENCES `Trajet` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
