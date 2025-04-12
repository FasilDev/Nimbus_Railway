package com.nimbus_railway.dao;

import com.nimbus_railway.models.Eleve;
import com.nimbus_railway.models.Trajet;
import com.nimbus_railway.models.TrajetEleve;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrajetEleveDAO {
    private Connection connection;
    private EleveDAO eleveDAO;
    private TrajetDAO trajetDAO;

    public TrajetEleveDAO() {
        this.connection = DatabaseConnection.getConnection();
        this.eleveDAO = new EleveDAO();
        this.trajetDAO = new TrajetDAO();
    }

    public List<TrajetEleve> getTousTrajetEleves() {
        List<TrajetEleve> trajetEleves = new ArrayList<>();
        String query = "SELECT * FROM Trajet_élève";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                TrajetEleve trajetEleve = creerTrajetEleveDepuisResultSet(rs);
                trajetEleves.add(trajetEleve);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des associations trajet-élève: " + e.getMessage());
            e.printStackTrace();
        }

        return trajetEleves;
    }

    public TrajetEleve getTrajetEleveById(int id) {
        String query = "SELECT * FROM Trajet_élève WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return creerTrajetEleveDepuisResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'association trajet-élève: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<TrajetEleve> getTrajetElevesByEleveId(int eleveId) {
        List<TrajetEleve> trajetEleves = new ArrayList<>();
        String query = "SELECT * FROM Trajet_élève WHERE id_élève = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eleveId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajetEleves.add(creerTrajetEleveDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des associations trajet-élève par élève: " + e.getMessage());
            e.printStackTrace();
        }

        return trajetEleves;
    }

    public List<TrajetEleve> getTrajetElevesByTrajetId(int trajetId) {
        List<TrajetEleve> trajetEleves = new ArrayList<>();
        String query = "SELECT * FROM Trajet_élève WHERE id_trajet = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trajetId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trajetEleves.add(creerTrajetEleveDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des associations trajet-élève par trajet: " + e.getMessage());
            e.printStackTrace();
        }

        return trajetEleves;
    }

    public List<Eleve> getElevesByTrajetId(int trajetId) {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT e.* FROM Eleve e JOIN Trajet_élève te ON e.id = te.id_élève WHERE te.id_trajet = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trajetId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String maisonStr = rs.getString("maison");
                Eleve.Maison maison = Eleve.Maison.fromDisplayName(maisonStr);

                eleves.add(new Eleve(id, nom, prenom, maison));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des élèves par trajet: " + e.getMessage());
            e.printStackTrace();
        }

        return eleves;
    }

    public List<Trajet> getTrajetsByEleveId(int eleveId) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT t.* FROM Trajet t JOIN Trajet_élève te ON t.id = te.id_trajet WHERE te.id_élève = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eleveId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp timestampDepart = rs.getTimestamp("horaire_depart");
                Timestamp timestampArrivee = rs.getTimestamp("horaire_arrivee");
                int trainId = rs.getInt("train_id");
                int arretDepartId = rs.getInt("arret_depart_id");
                int arretArriveeId = rs.getInt("arret_arrivee_id");

                Trajet trajet = new Trajet(id, timestampDepart.toLocalDateTime(), timestampArrivee.toLocalDateTime(),
                        trainId, arretDepartId, arretArriveeId);
                trajets.add(trajet);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des trajets par élève: " + e.getMessage());
            e.printStackTrace();
        }

        return trajets;
    }

    public boolean ajouterTrajetEleve(TrajetEleve trajetEleve) {
        String query = "INSERT INTO Trajet_élève (id_élève, id_trajet) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, trajetEleve.getIdEleve());
            stmt.setInt(2, trajetEleve.getIdTrajet());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    trajetEleve.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'une association trajet-élève: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerTrajetEleve(int id) {
        String query = "DELETE FROM Trajet_élève WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'une association trajet-élève: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerTrajetEleveByIds(int idEleve, int idTrajet) {
        String query = "DELETE FROM Trajet_élève WHERE id_élève = ? AND id_trajet = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEleve);
            stmt.setInt(2, idTrajet);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'une association trajet-élève par IDs: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private TrajetEleve creerTrajetEleveDepuisResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idEleve = rs.getInt("id_élève");
        int idTrajet = rs.getInt("id_trajet");

        TrajetEleve trajetEleve = new TrajetEleve(id, idEleve, idTrajet);

        // Charger les objets liés
        trajetEleve.setEleve(eleveDAO.getEleveById(idEleve));
        trajetEleve.setTrajet(trajetDAO.getTrajetById(idTrajet));

        return trajetEleve;
    }
}