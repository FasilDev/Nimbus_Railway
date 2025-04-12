package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.UtilisateurDAO;
import com.nimbus_railway.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtIdentifiant;

    @FXML
    private PasswordField txtMotDePasse;

    @FXML
    private Button btnConnexion;

    @FXML
    private Label lblMessage;

    @FXML
    void handleConnexion(ActionEvent event) {
        String identifiant = txtIdentifiant.getText();
        String motDePasse = txtMotDePasse.getText();

        // Validation basique
        if (identifiant.isEmpty() || motDePasse.isEmpty()) {
            lblMessage.setText("Veuillez remplir tous les champs");
            return;
        }

        // Vérification des identifiants
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur utilisateur = utilisateurDAO.authentifier(identifiant, motDePasse);

        if (utilisateur != null) {
            try {
                // Redirection vers le dashboard approprié selon le rôle
                String fxmlFile = utilisateur.getRole() == Utilisateur.Role.ADMIN
                        ? "/fxml/admin_dashboard.fxml"
                        : "/fxml/controleur_dashboard.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Passage de l'utilisateur connecté au contrôleur de destination
                if (utilisateur.getRole() == Utilisateur.Role.ADMIN) {
                    AdminController controller = loader.getController();
                    controller.initData(utilisateur);
                } else {
                    ControleurController controller = loader.getController();
                    controller.initData(utilisateur);
                }

                // Changement de scène
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Nimbus Railway - " + (utilisateur.getRole() == Utilisateur.Role.ADMIN ? "Administration" : "Contrôleur"));
                stage.show();

            } catch (IOException e) {
                lblMessage.setText("Erreur lors du chargement du dashboard");
                System.err.println("Erreur: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            lblMessage.setText("Identifiant ou mot de passe incorrect");
        }
    }

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
        lblMessage.setText("");
    }
}