package com.nimbus_railway.dao;

import com.nimbus_railway.models.Eleve;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EleveDAO {
    private Connection connection;

    public EleveDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Eleve> getTousEleves() {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT * FROM Eleve";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String maisonStr = rs.getString("maison");
                Eleve.Maison maison = Eleve.Maison.fromDisplayName(maisonStr);

                eleves.add(new Eleve(id, nom, prenom, maison));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des élèves: " + e.getMessage());
            e.printStackTrace();
        }

        return eleves;
    }

    public Eleve getEleveById(int id) {
        String query = "SELECT * FROM Eleve WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String maisonStr = rs.getString("maison");
                Eleve.Maison maison = Eleve.Maison.fromDisplayName(maisonStr);

                return new Eleve(id, nom, prenom, maison);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'élève: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean ajouterEleve(Eleve eleve) {
        String query = "INSERT INTO Eleve (nom, prenom, maison) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, eleve.getNom());
            stmt.setString(2, eleve.getPrenom());
            stmt.setString(3, eleve.getMaison().getDisplayName());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    eleve.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un élève: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierEleve(Eleve eleve) {
        String query = "UPDATE Eleve SET nom = ?, prenom = ?, maison = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, eleve.getNom());
            stmt.setString(2, eleve.getPrenom());
            stmt.setString(3, eleve.getMaison().getDisplayName());
            stmt.setInt(4, eleve.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'un élève: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerEleve(int id) {
        String query = "DELETE FROM Eleve WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'un élève: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    public List<Eleve> getAll() {
        return getTousEleves();
    }

    public List<Eleve> getElevesByMaison(Eleve.Maison maison) {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT * FROM Eleve WHERE maison = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maison.getDisplayName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");

                eleves.add(new Eleve(id, nom, prenom, maison));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des élèves par maison: " + e.getMessage());
            e.printStackTrace();
        }

        return eleves;
    }
}