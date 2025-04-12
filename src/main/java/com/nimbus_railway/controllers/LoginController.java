package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.UtilisateurDAO;
import com.nimbus_railway.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtIdentifiant;
    @FXML private PasswordField txtMotDePasse;
    @FXML private Label lblMessage;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    void handleConnexion(ActionEvent event) {
        String identifiant = txtIdentifiant.getText();
        String motDePasse = txtMotDePasse.getText();

        Utilisateur utilisateur = utilisateurDAO.authentifier(identifiant, motDePasse);

        if (utilisateur != null) {
            lblMessage.setText("Connexion réussie");

            try {
                FXMLLoader loader;
                if (utilisateur.getRole() == Utilisateur.Role.ADMIN) {
                    loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/fxml/controleur_dashboard.fxml"));
                }

                Parent root = loader.load();

                // Passage de l'utilisateur au contrôleur
                Object controller = loader.getController();
                if (controller instanceof AdminController adminCtrl) {
                    adminCtrl.initData(utilisateur);
                } else if (controller instanceof ControleurController ctrlCtrl) {
                    ctrlCtrl.initData(utilisateur);
                }

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard - Nimbus Railway");
                stage.show();

            } catch (IOException e) {
                lblMessage.setText("Erreur chargement interface : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            lblMessage.setText("Identifiants incorrects");
        }
    }
}
