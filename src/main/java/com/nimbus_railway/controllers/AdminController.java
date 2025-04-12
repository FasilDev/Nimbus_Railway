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

    @FXML private void handleGestionEleves(ActionEvent e) {
        loadScreen(e, "/fxml/eleves_management.fxml");
    }

    @FXML private void handleGestionUtilisateurs(ActionEvent e) {
        loadScreen(e, "/fxml/utilisateur_management.fxml");
    }

    @FXML private void handleGestionTrajets(ActionEvent e) {
        loadScreen(e, "/fxml/trajets_management.fxml");
    }

    @FXML private void handleGestionTrains(ActionEvent e) {
        loadScreen(e, "/fxml/trains_management.fxml");
    }


    @FXML
    private void loadScreen(ActionEvent event, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Nimbus Railway - Gestion");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Ajoute cette ligne aussi pour être sûr de voir le problème :
            System.err.println("Impossible de charger : " + path);
        }
    }

    @FXML
    private void handleRetourLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
