package com.nimbus_railway.controllers;

import com.nimbus_railway.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControleurController {

    @FXML private Label lblUserInfo;

    private Utilisateur utilisateur;

    public void initData(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        lblUserInfo.setText("Bienvenue, " + utilisateur.getPrenom() + " " + utilisateur.getNom());
    }

    @FXML
    private void handleConsulterTrajets(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/controleur_trajet.fxml"));
            Parent root = loader.load();

            ControleurTrajetsController controller = loader.getController();
            controller.initData(utilisateur);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Trajets - Contrôleur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleVerifierEleves(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/controleur_eleves.fxml"));
            Parent root = loader.load();

            ControleurElevesController controller = loader.getController();
            controller.initData(utilisateur);

            Stage stage = (Stage) lblUserInfo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Élèves - Contrôleur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeconnexion(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) lblUserInfo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
