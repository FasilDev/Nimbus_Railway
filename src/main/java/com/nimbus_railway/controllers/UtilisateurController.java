package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.UtilisateurDAO;
import com.nimbus_railway.models.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class UtilisateurController {

    @FXML private TextField txtNom, txtPrenom, txtIdentifiant;
    @FXML private PasswordField txtMotDePasse;
    @FXML private ComboBox<String> cboRole;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer, btnRetour;
    @FXML private TableView<Utilisateur> tableUtilisateurs;
    @FXML private TableColumn<Utilisateur, Integer> colId;
    @FXML private TableColumn<Utilisateur, String> colNom, colPrenom, colIdentifiant, colRole;
    @FXML private Label lblMessage;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private final ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colIdentifiant.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        colRole.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getRole().toString()));

        cboRole.getItems().addAll("ADMIN", "CONTROLEUR");

        tableUtilisateurs.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtNom.setText(newSel.getNom());
                txtPrenom.setText(newSel.getPrenom());
                txtIdentifiant.setText(newSel.getIdentifiant());
                txtMotDePasse.setText(newSel.getMotDePasse());
                cboRole.setValue(newSel.getRole().toString());
            }
        });

        loadUtilisateurs();
    }

    private void loadUtilisateurs() {
        utilisateurs.setAll(utilisateurDAO.getTousUtilisateurs());
        tableUtilisateurs.setItems(utilisateurs);
    }

    @FXML
    void handleAjouter() {
        try {
            Utilisateur utilisateur = new Utilisateur(
                    txtIdentifiant.getText(),
                    txtMotDePasse.getText(),
                    txtNom.getText(),
                    txtPrenom.getText(),
                    Utilisateur.Role.valueOf(cboRole.getValue())
            );
            if (utilisateurDAO.ajouterUtilisateur(utilisateur)) {
                utilisateurs.add(utilisateur);
                lblMessage.setText("Utilisateur ajouté.");
                clearFields();
            } else {
                lblMessage.setText("Erreur d'ajout.");
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur : " + e.getMessage());
        }
    }

    @FXML
    void handleModifier() {
        Utilisateur selected = tableUtilisateurs.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setIdentifiant(txtIdentifiant.getText());
            selected.setMotDePasse(txtMotDePasse.getText());
            selected.setRole(Utilisateur.Role.valueOf(cboRole.getValue()));

            if (utilisateurDAO.modifierUtilisateur(selected)) {
                tableUtilisateurs.refresh();
                lblMessage.setText("Modifié.");
                clearFields();
            } else {
                lblMessage.setText("Erreur.");
            }
        }
    }

    @FXML
    void handleSupprimer() {
        Utilisateur selected = tableUtilisateurs.getSelectionModel().getSelectedItem();
        if (selected != null && utilisateurDAO.supprimerUtilisateur(selected.getId())) {
            utilisateurs.remove(selected);
            lblMessage.setText("Supprimé.");
            clearFields();
        } else {
            lblMessage.setText("Sélection invalide.");
        }
    }

    @FXML
    void handleRetour() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin_dashboard.fxml"));
            Stage stage = (Stage) btnRetour.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            lblMessage.setText("Erreur retour");
        }
    }

    private void clearFields() {
        txtNom.clear();
        txtPrenom.clear();
        txtIdentifiant.clear();
        txtMotDePasse.clear();
        cboRole.getSelectionModel().clearSelection();
        tableUtilisateurs.getSelectionModel().clearSelection();
    }
}
