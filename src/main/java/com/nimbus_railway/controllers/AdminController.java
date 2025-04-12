package com.nimbus_railway.controllers;

import com.nimbus_railway.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    private Utilisateur utilisateur;

    public void initData(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        System.out.println("Connecté en tant que : " + utilisateur.getNom());
    }

    @FXML
    private void handleGestionTrains(ActionEvent event) {
        loadScreen(event, "/fxml/trains_management.fxml");
    }

    @FXML
    private void handleGestionTrajets(ActionEvent event) {
        loadScreen(event, "/fxml/trajets_management.fxml");
    }

    @FXML
    private void handleGestionUtilisateurs(ActionEvent event) {
        loadScreen(event, "/fxml/utilisateurs_management.fxml");
    }

    @FXML
    private void handleGestionEleves(ActionEvent event) {
        loadScreen(event, "/fxml/eleves_management.fxml");
    }

    private void loadScreen(ActionEvent event, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion - Nimbus Railway");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'écran : " + path);
        }
    }
}
