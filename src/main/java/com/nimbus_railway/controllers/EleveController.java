package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.EleveDAO;
import com.nimbus_railway.models.Eleve;
import com.nimbus_railway.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EleveController {

    @FXML private Button btnRetour;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private ComboBox<String> cboMaison;
    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Label lblMessage;
    @FXML private TextField txtRecherche;

    @FXML private TableView<Eleve> tableEleves;
    @FXML private TableColumn<Eleve, Integer> colId;
    @FXML private TableColumn<Eleve, String> colNom;
    @FXML private TableColumn<Eleve, String> colPrenom;
    @FXML private TableColumn<Eleve, String> colMaison;

    private EleveDAO eleveDAO;
    private ObservableList<Eleve> elevesList = FXCollections.observableArrayList();
    private FilteredList<Eleve> filteredEleves;

    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            if (txtNom.getText().trim().isEmpty() || txtPrenom.getText().trim().isEmpty() || cboMaison.getSelectionModel().isEmpty()) {
                lblMessage.setText("Tous les champs sont obligatoires");
                return;
            }

            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            Eleve.Maison maison = Eleve.Maison.fromDisplayName(cboMaison.getValue());
            Eleve nouvelEleve = new Eleve(nom, prenom, maison);

            if (eleveDAO.ajouterEleve(nouvelEleve)) {
                lblMessage.setText("Élève ajouté avec succès");
                elevesList.add(nouvelEleve);
                clearFields();
            } else {
                lblMessage.setText("Erreur lors de l'ajout de l'élève");
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        try {
            Eleve selectedEleve = tableEleves.getSelectionModel().getSelectedItem();
            if (selectedEleve == null) {
                lblMessage.setText("Veuillez sélectionner un élève");
                return;
            }

            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            Eleve.Maison maison = Eleve.Maison.fromDisplayName(cboMaison.getValue());

            selectedEleve.setNom(nom);
            selectedEleve.setPrenom(prenom);
            selectedEleve.setMaison(maison);

            if (eleveDAO.modifierEleve(selectedEleve)) {
                lblMessage.setText("Élève modifié avec succès");
                int index = elevesList.indexOf(selectedEleve);
                if (index >= 0) elevesList.set(index, selectedEleve);
                clearFields();
                tableEleves.getSelectionModel().clearSelection();
            } else {
                lblMessage.setText("Erreur lors de la modification");
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        try {
            Eleve selectedEleve = tableEleves.getSelectionModel().getSelectedItem();
            if (selectedEleve == null) {
                lblMessage.setText("Sélectionnez un élève à supprimer");
                return;
            }

            boolean confirm = AlertUtils.showConfirmation(
                    "Confirmation", "Supprimer Élève",
                    "Confirmer la suppression de : " + selectedEleve.getPrenom() + " " + selectedEleve.getNom());

            if (!confirm) return;

            if (eleveDAO.supprimerEleve(selectedEleve.getId())) {
                elevesList.remove(selectedEleve);
                clearFields();
                tableEleves.getSelectionModel().clearSelection();
                lblMessage.setText("Élève supprimé");
            } else {
                lblMessage.setText("Erreur lors de la suppression");
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin_dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Nimbus Railway - Administration");
            stage.show();
        } catch (IOException e) {
            lblMessage.setText("Erreur chargement dashboard");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtNom.clear();
        txtPrenom.clear();
        cboMaison.getSelectionModel().clearSelection();
    }

    @FXML
    public void initialize() {
        eleveDAO = new EleveDAO();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colMaison.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getMaison().getDisplayName()
                )
        );

        loadEleves();

        cboMaison.getItems().addAll(
                Arrays.stream(Eleve.Maison.values())
                        .map(Eleve.Maison::getDisplayName)
                        .collect(Collectors.toList())
        );

        tableEleves.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtNom.setText(newVal.getNom());
                txtPrenom.setText(newVal.getPrenom());
                cboMaison.setValue(newVal.getMaison().getDisplayName());
            }
        });

        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEleves.setPredicate(eleve -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String filtre = newValue.toLowerCase();
                return eleve.getNom().toLowerCase().contains(filtre) ||
                        eleve.getPrenom().toLowerCase().contains(filtre) ||
                        eleve.getMaison().getDisplayName().toLowerCase().contains(filtre) ||
                        String.valueOf(eleve.getId()).contains(filtre);
            });
        });
    }

    private void loadEleves() {
        try {
            elevesList.clear();
            elevesList.addAll(eleveDAO.getTousEleves());
            filteredEleves = new FilteredList<>(elevesList, p -> true);
            tableEleves.setItems(filteredEleves);

            lblMessage.setText(elevesList.isEmpty() ? "Aucun élève trouvé" : "");
        } catch (Exception e) {
            lblMessage.setText("Erreur chargement élèves: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
