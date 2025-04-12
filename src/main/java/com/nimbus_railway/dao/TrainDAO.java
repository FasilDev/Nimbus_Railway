package com.nimbus_railway.dao;

import com.nimbus_railway.models.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {
    private Connection connection;

    public TrainDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Train> getTousTrains() {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM Train";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int nombreWagons = rs.getInt("nombre_wagons");
                String typeTrainStr = rs.getString("type_train");
                Train.TypeTrain typeTrain = Train.TypeTrain.fromDisplayName(typeTrainStr);

                trains.add(new Train(id, nombreWagons, typeTrain));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trains: " + e.getMessage());
            e.printStackTrace();
        }

        return trains;
    }

    public Train getTrainById(int id) {
        String query = "SELECT * FROM Train WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int nombreWagons = rs.getInt("nombre_wagons");
                String typeTrainStr = rs.getString("type_train");
                Train.TypeTrain typeTrain = Train.TypeTrain.fromDisplayName(typeTrainStr);

                return new Train(id, nombreWagons, typeTrain);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du train: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean ajouterTrain(Train train) {
        String query = "INSERT INTO Train (nombre_wagons, type_train) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, train.getNombreWagons());
            stmt.setString(2, train.getTypeTrain().getDisplayName());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    train.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un train: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierTrain(Train train) {
        String query = "UPDATE Train SET nombre_wagons = ?, type_train = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, train.getNombreWagons());
            stmt.setString(2, train.getTypeTrain().getDisplayName());
            stmt.setInt(3, train.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'un train: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerTrain(int id) {
        String query = "DELETE FROM Train WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'un train: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}