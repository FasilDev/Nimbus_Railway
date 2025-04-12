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

    @FXML
    private Button btnRetour;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private ComboBox<String> cboMaison;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txtRecherche;

    @FXML
    private TableView<Eleve> tableEleve;

    @FXML
    private TableColumn<Eleve, Integer> colId;

    @FXML
    private TableColumn<Eleve, String> colNom;

    @FXML
    private TableColumn<Eleve, String> colPrenom;

    @FXML
    private TableColumn<Eleve, String> colMaison;

    private EleveDAO eleveDAO;
    private ObservableList<Eleve> elevesList = FXCollections.observableArrayList();
    private FilteredList<Eleve> filteredEleves;

    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            // Validation des entrées
            if (txtNom.getText().trim().isEmpty()) {
                lblMessage.setText("Veuillez entrer un nom");
                return;
            }

            if (txtPrenom.getText().trim().isEmpty()) {
                lblMessage.setText("Veuillez entrer un prénom");
                return;
            }

            if (cboMaison.getSelectionModel().isEmpty()) {
                lblMessage.setText("Veuillez sélectionner une maison");
                return;
            }

            // Création de l'élève
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String maisonStr = cboMaison.getValue();
            Eleve.Maison maison = Eleve.Maison.fromDisplayName(maisonStr);

            Eleve nouvelEleve = new Eleve(nom, prenom, maison);

            // Ajout en base de données
            if (eleveDAO.ajouterEleve(nouvelEleve)) {
                lblMessage.setText("Élève ajouté avec succès");

                // Mise à jour de la liste des élèves
                elevesList.add(nouvelEleve);

                // Réinitialisation des champs
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
            Eleve selectedEleve = tableEleve.getSelectionModel().getSelectedItem();
            if (selectedEleve == null) {
                lblMessage.setText("Veuillez sélectionner un élève à modifier");
                return;
            }

            // Validation des entrées
            if (txtNom.getText().trim().isEmpty()) {
                lblMessage.setText("Veuillez entrer un nom");
                return;
            }

            if (txtPrenom.getText().trim().isEmpty()) {
                lblMessage.setText("Veuillez entrer un prénom");
                return;
            }

            if (cboMaison.getSelectionModel().isEmpty()) {
                lblMessage.setText("Veuillez sélectionner une maison");
                return;
            }

            // Modification de l'élève
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String maisonStr = cboMaison.getValue();
            Eleve.Maison maison = Eleve.Maison.fromDisplayName(maisonStr);

            selectedEleve.setNom(nom);
            selectedEleve.setPrenom(prenom);
            selectedEleve.setMaison(maison);

            // Mise à jour en base de données
            if (eleveDAO.modifierEleve(selectedEleve)) {
                lblMessage.setText("Élève modifié avec succès");

                // Mise à jour de la liste des élèves
                int index = elevesList.indexOf(selectedEleve);
                if (index >= 0) {
                    elevesList.set(index, selectedEleve);
                }

                // Réinitialisation des champs
                clearFields();
                tableEleve.getSelectionModel().clearSelection();
            } else {
                lblMessage.setText("Erreur lors de la modification de l'élève");
            }

        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        try {
            Eleve selectedEleve = tableEleve.getSelectionModel().getSelectedItem();
            if (selectedEleve == null) {
                lblMessage.setText("Veuillez sélectionner un élève à supprimer");
                return;
            }

            // Confirmation de suppression
            boolean confirm = AlertUtils.showConfirmation(
                    "Confirmation de suppression",
                    "Supprimer l'élève",
                    "Êtes-vous sûr de vouloir supprimer l'élève " + selectedEleve.getPrenom() + " " + selectedEleve.getNom() + " ?"
            );

            if (!confirm) {
                return;
            }

            // Suppression en base de données
            if (eleveDAO.supprimerEleve(selectedEleve.getId())) {
                lblMessage.setText("Élève supprimé avec succès");

                // Mise à jour de la liste des élèves
                elevesList.remove(selectedEleve);

                // Réinitialisation des champs
                clearFields();
                tableEleve.getSelectionModel().clearSelection();
            } else {
                lblMessage.setText("Erreur lors de la suppression de l'élève");
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
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Nimbus Railway - Administration");
            stage.show();
        } catch (IOException e) {
            lblMessage.setText("Erreur lors du chargement du dashboard");
            System.err.println("Erreur: " + e.getMessage());
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
        // Initialisation du DAO
        eleveDAO = new EleveDAO();

        // Configuration des colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colMaison.setCellValueFactory(cellData -> {
            Eleve eleve = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                    () -> eleve.getMaison().getDisplayName()
            );
        });

        // Chargement des données
        loadEleves();

        // Initialisation du combobox des maisons
        cboMaison.getItems().addAll(
                Arrays.stream(Eleve.Maison.values())
                        .map(Eleve.Maison::getDisplayName)
                        .collect(Collectors.toList())
        );

        // Configuration de la sélection dans le tableau
        tableEleve.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNom.setText(newSelection.getNom());
                txtPrenom.setText(newSelection.getPrenom());
                cboMaison.setValue(newSelection.getMaison().getDisplayName());
            }
        });

        // Configuration de la recherche
        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEleves.setPredicate(eleve -> {
                // Si le champ de recherche est vide, afficher tous les élèves
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Recherche par nom
                if (eleve.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                // Recherche par prénom
                if (eleve.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                // Recherche par maison
                if (eleve.getMaison().getDisplayName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                // Recherche par ID
                return String.valueOf(eleve.getId()).contains(lowerCaseFilter);
            });
        });
    }

    private void loadEleves() {
        try {
            // Récupération des élèves
            elevesList.clear();
            elevesList.addAll(eleveDAO.getTousEleves());

            // Configuration de la liste filtrée
            filteredEleves = new FilteredList<>(elevesList, p -> true);

            // Liaison de la liste filtrée avec le tableau
            tableEleve.setItems(filteredEleves);

            if (elevesList.isEmpty()) {
                lblMessage.setText("Aucun élève trouvé");
            } else {
                lblMessage.setText("");
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur lors du chargement des élèves: " + e.getMessage());
            e.printStackTrace();
        }
    }
}