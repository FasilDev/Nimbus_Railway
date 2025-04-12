package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.TrainDAO;
import com.nimbus_railway.models.Train;
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

public class TrainController {

    @FXML
    private Button btnRetour;

    @FXML
    private ComboBox<String> cboTypeTrain;

    @FXML
    private TextField txtNombreWagons;

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
    private TableView<Train> tableTrain;

    @FXML
    private TableColumn<Train, Integer> colId;

    @FXML
    private TableColumn<Train, String> colTypeTrain;

    @FXML
    private TableColumn<Train, Integer> colNombreWagons;

    private TrainDAO trainDAO;
    private ObservableList<Train> trainsList = FXCollections.observableArrayList();
    private FilteredList<Train> filteredTrains;

    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            // Validation des entrées
            if (cboTypeTrain.getSelectionModel().isEmpty()) {
                lblMessage.setText("Veuillez sélectionner un type de train");
                return;
            }

            String nombreWagonsText = txtNombreWagons.getText().trim();
            if (nombreWagonsText.isEmpty()) {
                lblMessage.setText("Veuillez entrer le nombre de wagons");
                return;
            }

            int nombreWagons;
            try {
                nombreWagons = Integer.parseInt(nombreWagonsText);
                if (nombreWagons <= 0) {
                    lblMessage.setText("Le nombre de wagons doit être supérieur à 0");
                    return;
                }
            } catch (NumberFormatException e) {
                lblMessage.setText("Le nombre de wagons doit être un nombre entier");
                return;
            }

            // Création du train
            String typeTrainStr = cboTypeTrain.getValue();
            Train.TypeTrain typeTrain = Train.TypeTrain.fromDisplayName(typeTrainStr);
            Train nouveauTrain = new Train(nombreWagons, typeTrain);

            // Ajout en base de données
            if (trainDAO.ajouterTrain(nouveauTrain)) {
                lblMessage.setText("Train ajouté avec succès");

                // Mise à jour de la liste des trains
                trainsList.add(nouveauTrain);

                // Réinitialisation des champs
                cboTypeTrain.getSelectionModel().clearSelection();
                txtNombreWagons.clear();
            } else {
                lblMessage.setText("Erreur lors de l'ajout du train");
            }

        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        try {
            Train selectedTrain = tableTrain.getSelectionModel().getSelectedItem();
            if (selectedTrain == null) {
                lblMessage.setText("Veuillez sélectionner un train à modifier");
                return;
            }

            // Validation des entrées (comme pour l'ajout)
            if (cboTypeTrain.getSelectionModel().isEmpty()) {
                lblMessage.setText("Veuillez sélectionner un type de train");
                return;
            }

            String nombreWagonsText = txtNombreWagons.getText().trim();
            if (nombreWagonsText.isEmpty()) {
                lblMessage.setText("Veuillez entrer le nombre de wagons");
                return;
            }

            int nombreWagons;
            try {
                nombreWagons = Integer.parseInt(nombreWagonsText);
                if (nombreWagons <= 0) {
                    lblMessage.setText("Le nombre de wagons doit être supérieur à 0");
                    return;
                }
            } catch (NumberFormatException e) {
                lblMessage.setText("Le nombre de wagons doit être un nombre entier");
                return;
            }

            // Modification du train
            String typeTrainStr = cboTypeTrain.getValue();
            Train.TypeTrain typeTrain = Train.TypeTrain.fromDisplayName(typeTrainStr);
            selectedTrain.setTypeTrain(typeTrain);
            selectedTrain.setNombreWagons(nombreWagons);

            // Mise à jour en base de données
            if (trainDAO.modifierTrain(selectedTrain)) {
                lblMessage.setText("Train modifié avec succès");

                // Mise à jour de la liste des trains
                int index = trainsList.indexOf(selectedTrain);
                if (index >= 0) {
                    trainsList.set(index, selectedTrain);
                }

                // Réinitialisation des champs
                cboTypeTrain.getSelectionModel().clearSelection();
                txtNombreWagons.clear();
                tableTrain.getSelectionModel().clearSelection();
            } else {
                lblMessage.setText("Erreur lors de la modification du train");
            }

        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        try {
            Train selectedTrain = tableTrain.getSelectionModel().getSelectedItem();
            if (selectedTrain == null) {
                lblMessage.setText("Veuillez sélectionner un train à supprimer");
                return;
            }

            // Confirmation de suppression
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer le train");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce train ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                // Suppression en base de données
                if (trainDAO.supprimerTrain(selectedTrain.getId())) {
                    lblMessage.setText("Train supprimé avec succès");

                    // Mise à jour de la liste des trains
                    trainsList.remove(selectedTrain);

                    // Réinitialisation des champs
                    cboTypeTrain.getSelectionModel().clearSelection();
                    txtNombreWagons.clear();
                    tableTrain.getSelectionModel().clearSelection();
                } else {
                    lblMessage.setText("Erreur lors de la suppression du train");
                }
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

    @FXML
    public void initialize() {
        // Initialisation du DAO
        trainDAO = new TrainDAO();

        // Configuration des colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTypeTrain.setCellValueFactory(cellData -> {
            Train train = cellData.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(
                    () -> train.getTypeTrain().getDisplayName()
            );
        });
        colNombreWagons.setCellValueFactory(new PropertyValueFactory<>("nombreWagons"));

        // Chargement des données
        loadTrains();

        // Initialisation du combobox des types de train
        cboTypeTrain.getItems().addAll(
                Arrays.stream(Train.TypeTrain.values())
                        .map(Train.TypeTrain::getDisplayName)
                        .collect(Collectors.toList())
        );

        // Configuration de la sélection dans le tableau
        tableTrain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cboTypeTrain.setValue(newSelection.getTypeTrain().getDisplayName());
                txtNombreWagons.setText(String.valueOf(newSelection.getNombreWagons()));
            }
        });

        // Configuration de la recherche
        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTrains.setPredicate(train -> {
                // Si le champ de recherche est vide, afficher tous les trains
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Recherche par type de train
                if (train.getTypeTrain().getDisplayName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                // Recherche par nombre de wagons
                if (String.valueOf(train.getNombreWagons()).contains(lowerCaseFilter)) {
                    return true;
                }

                // Recherche par ID
                return String.valueOf(train.getId()).contains(lowerCaseFilter);
            });
        });
    }

    private void loadTrains() {
        try {
            // Récupération des trains
            trainsList.clear();
            trainsList.addAll(trainDAO.getTousTrains());

            // Configuration de la liste filtrée
            filteredTrains = new FilteredList<>(trainsList, p -> true);

            // Liaison de la liste filtrée avec le tableau
            tableTrain.setItems(filteredTrains);

            if (trainsList.isEmpty()) {
                lblMessage.setText("Aucun train trouvé");
            } else {
                lblMessage.setText("");
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur lors du chargement des trains: " + e.getMessage());
            e.printStackTrace();
        }
    }
}