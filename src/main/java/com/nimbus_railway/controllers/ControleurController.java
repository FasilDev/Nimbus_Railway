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

public class ControleurController {

    @FXML
    private Label lblUserInfo;

    @FXML
    private Button btnConsulterTrajets;

    @FXML
    private Button btnVerifierEleves;

    @FXML
    private Button btnDeconnexion;

    private Utilisateur utilisateurConnecte;

    public void initData(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
        lblUserInfo.setText("Bienvenue, " + utilisateur.getPrenom() + " " + utilisateur.getNom() + " (" + utilisateur.getRole() + ")");
    }

    @FXML
    void handleConsulterTrajets(ActionEvent event) {
        loadScreen(event, "/fxml/trajets_consultation.fxml", "Consultation des Trajets");
    }

    @FXML
    void handleVerifierEleves(ActionEvent event) {
        loadScreen(event, "/fxml/eleves_verification.fxml", "Vérification des Élèves");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
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