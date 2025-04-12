package com.nimbus_railway.dao;

import com.nimbus_railway.models.Arret;
import com.nimbus_railway.models.Train;
import com.nimbus_railway.models.Trajet;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrajetDAO {
    private Connection connection;
    private TrainDAO trainDAO;
    private ArretDAO arretDAO;

    public TrajetDAO() {
        this.connection = DatabaseConnection.getConnection();
        this.trainDAO = new TrainDAO();
        this.arretDAO = new ArretDAO();
    }

    public List<Trajet> getTousTrajets() {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM Trajet";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Trajet trajet = creerTrajetDepuisResultSet(rs);
                trajets.add(trajet);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trajets: " + e.getMessage());
            e.printStackTrace();
        }

        return trajets;
    }

    public Trajet getTrajetById(int id) {
        String query = "SELECT * FROM Trajet WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return creerTrajetDepuisResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du trajet: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Trajet> getTrajetsByTrainId(int trainId) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM Trajet WHERE train_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trainId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajets.add(creerTrajetDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trajets par train: " + e.getMessage());
            e.printStackTrace();
        }

        return trajets;
    }

    public List<Trajet> getTrajetsByArretDepartId(int arretId) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM Trajet WHERE arret_depart_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, arretId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajets.add(creerTrajetDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trajets par arrêt de départ: " + e.getMessage());
            e.printStackTrace();
        }

        return trajets;
    }

    public List<Trajet> getTrajetsByArretArriveeId(int arretId) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM Trajet WHERE arret_arrivee_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, arretId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajets.add(creerTrajetDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trajets par arrêt d'arrivée: " + e.getMessage());
            e.printStackTrace();
        }

        return trajets;
    }

    public List<Trajet> getTrajetsByDateDepart(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM Trajet WHERE horaire_depart BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(dateDebut));
            stmt.setTimestamp(2, Timestamp.valueOf(dateFin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajets.add(creerTrajetDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trajets par date: " + e.getMessage());
            e.printStackTrace();
        }

        return trajets;
    }

    public boolean ajouterTrajet(Trajet trajet) {
        String query = "INSERT INTO Trajet (horaire_depart, horaire_arrivee, train_id, arret_depart_id, arret_arrivee_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(trajet.getHoraireDepart()));
            stmt.setTimestamp(2, Timestamp.valueOf(trajet.getHoraireArrivee()));
            stmt.setInt(3, trajet.getTrainId());
            stmt.setInt(4, trajet.getArretDepartId());
            stmt.setInt(5, trajet.getArretArriveeId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    trajet.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un trajet: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierTrajet(Trajet trajet) {
        String query = "UPDATE Trajet SET horaire_depart = ?, horaire_arrivee = ?, train_id = ?, arret_depart_id = ?, arret_arrivee_id = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(trajet.getHoraireDepart()));
            stmt.setTimestamp(2, Timestamp.valueOf(trajet.getHoraireArrivee()));
            stmt.setInt(3, trajet.getTrainId());
            stmt.setInt(4, trajet.getArretDepartId());
            stmt.setInt(5, trajet.getArretArriveeId());
            stmt.setInt(6, trajet.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'un trajet: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerTrajet(int id) {
        String query = "DELETE FROM Trajet WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'un trajet: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    public List<Trajet> getAll() {
        return getTousTrajets();
    }

    private Trajet creerTrajetDepuisResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        LocalDateTime horaireDepart = rs.getTimestamp("horaire_depart").toLocalDateTime();
        LocalDateTime horaireArrivee = rs.getTimestamp("horaire_arrivee").toLocalDateTime();
        int trainId = rs.getInt("train_id");
        int arretDepartId = rs.getInt("arret_depart_id");
        int arretArriveeId = rs.getInt("arret_arrivee_id");

        Trajet trajet = new Trajet(id, horaireDepart, horaireArrivee, trainId, arretDepartId, arretArriveeId);

        // Charger les objets liés
        trajet.setTrain(trainDAO.getTrainById(trainId));
        trajet.setArretDepart(arretDAO.getArretById(arretDepartId));
        trajet.setArretArrivee(arretDAO.getArretById(arretArriveeId));

        return trajet;
    }
}