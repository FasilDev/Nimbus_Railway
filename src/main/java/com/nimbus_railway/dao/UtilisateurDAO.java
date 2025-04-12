package com.nimbus_railway.dao;

import com.nimbus_railway.models.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private Connection connection;

    public UtilisateurDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Utilisateur authentifier(String identifiant, String motDePasse) {
        String query = "SELECT * FROM Utilisateur WHERE identifiant = ? AND mot_de_passe = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, identifiant);
            stmt.setString(2, motDePasse); // Note: Dans une application réelle, vous devriez hasher le mot de passe

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String roleStr = rs.getString("role");
                Utilisateur.Role role = Utilisateur.Role.valueOf(roleStr);

                return new Utilisateur(id, identifiant, motDePasse, nom, prenom, role);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Authentification échouée
    }

    public List<Utilisateur> getTousUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String identifiant = rs.getString("identifiant");
                String motDePasse = rs.getString("mot_de_passe");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String roleStr = rs.getString("role");
                Utilisateur.Role role = Utilisateur.Role.valueOf(roleStr);

                utilisateurs.add(new Utilisateur(id, identifiant, motDePasse, nom, prenom, role));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            e.printStackTrace();
        }

        return utilisateurs;
    }

    public boolean ajouterUtilisateur(Utilisateur utilisateur) {
        String query = "INSERT INTO Utilisateur (identifiant, mot_de_passe, nom, prenom, role) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, utilisateur.getIdentifiant());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getNom());
            stmt.setString(4, utilisateur.getPrenom());
            stmt.setString(5, utilisateur.getRole().toString());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    utilisateur.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un utilisateur: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierUtilisateur(Utilisateur utilisateur) {
        String query = "UPDATE Utilisateur SET identifiant = ?, mot_de_passe = ?, nom = ?, prenom = ?, role = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, utilisateur.getIdentifiant());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getNom());
            stmt.setString(4, utilisateur.getPrenom());
            stmt.setString(5, utilisateur.getRole().toString());
            stmt.setInt(6, utilisateur.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'un utilisateur: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerUtilisateur(int id) {
        String query = "DELETE FROM Utilisateur WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'un utilisateur: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
