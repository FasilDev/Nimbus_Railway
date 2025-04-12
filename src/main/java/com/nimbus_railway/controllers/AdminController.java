package com.nimbus_railway.controllers;

import com.nimbus_railway.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Label lblUserInfo;

    @FXML
    private Button btnGestionTrains;

    @FXML
    private Button btnGestionEleves;

    @FXML
    private Button btnGestionTrajets;

    @FXML
    private Button btnGestionUtilisateurs;

    @FXML
    private Button btnDeconnexion;

    private Utilisateur utilisateurConnecte;

    public void initData(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
        lblUserInfo.setText("Bienvenue, " + utilisateur.getPrenom() + " " + utilisateur.getNom() + " (" + utilisateur.getRole() + ")");
    }

    @FXML
    void handleGestionTrains(ActionEvent event) {
        loadScreen(event, "/fxml/trains_management.fxml", "Gestion des Trains");
    }

    @FXML
    void handleGestionEleves(ActionEvent event) {
        loadScreen(event, "/fxml/eleves_management.fxml", "Gestion des Élèves");
    }

    @FXML
    void handleGestionTrajets(ActionEvent event) {
        loadScreen(event, "/fxml/trajets_management.fxml", "Gestion des Trajets");
    }

    @FXML
    void handleGestionUtilisateurs(ActionEvent event) {
        loadScreen(event, "/fxml/utilisateurs_management.fxml", "Gestion des Utilisateurs");
    }

    @FXML
    void handleDeconnexion(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Nimbus Railway - Connexion");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'écran de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadScreen(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eleves_management.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Nimbus Railway - " + title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'écran " + title + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
    }
}