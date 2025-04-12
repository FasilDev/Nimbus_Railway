package com.nimbus_railway.dao;

import com.nimbus_railway.models.Arret;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArretDAO {
    private Connection connection;

    public ArretDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Arret> getTousArrets() {
        List<Arret> arrets = new ArrayList<>();
        String query = "SELECT * FROM Arret";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String typeArretStr = rs.getString("type_arret");
                Arret.TypeArret typeArret = Arret.TypeArret.fromDisplayName(typeArretStr);

                arrets.add(new Arret(id, nom, typeArret));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des arrêts: " + e.getMessage());
            e.printStackTrace();
        }

        return arrets;
    }

    public Arret getArretById(int id) {
        String query = "SELECT * FROM Arret WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String typeArretStr = rs.getString("type_arret");
                Arret.TypeArret typeArret = Arret.TypeArret.fromDisplayName(typeArretStr);

                return new Arret(id, nom, typeArret);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'arrêt: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean ajouterArret(Arret arret) {
        String query = "INSERT INTO Arret (nom, type_arret) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, arret.getNom());
            stmt.setString(2, arret.getTypeArret().getDisplayName());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    arret.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un arrêt: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierArret(Arret arret) {
        String query = "UPDATE Arret SET nom = ?, type_arret = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, arret.getNom());
            stmt.setString(2, arret.getTypeArret().getDisplayName());
            stmt.setInt(3, arret.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'un arrêt: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerArret(int id) {
        String query = "DELETE FROM Arret WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'un arrêt: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public List<Arret> getArretsByType(Arret.TypeArret typeArret) {
        List<Arret> arrets = new ArrayList<>();
        String query = "SELECT * FROM Arret WHERE type_arret = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, typeArret.getDisplayName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");

                arrets.add(new Arret(id, nom, typeArret));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des arrêts par type: " + e.getMessage());
            e.printStackTrace();
        }

        return arrets;
    }
}