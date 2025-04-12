package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.TrajetDAO;
import com.nimbus_railway.models.Trajet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TrajetController {

    @FXML private TextField txtDepart;
    @FXML private TextField txtArrivee;
    @FXML private TextField txtHoraireDepart;
    @FXML private TextField txtHoraireArrivee;
    @FXML private TextField txtTrainId;
    @FXML private Button btnAjouter;
    @FXML private Button btnSupprimer;
    @FXML private Button btnRetour;
    @FXML private Label lblMessage;
    @FXML private TableView<Trajet> tableTrajets;
    @FXML private TableColumn<Trajet, Integer> colId;
    @FXML private TableColumn<Trajet, String> colDepart;
    @FXML private TableColumn<Trajet, String> colArrivee;
    @FXML private TableColumn<Trajet, String> colTrain;
    @FXML private TableColumn<Trajet, String> colHeureDep;
    @FXML private TableColumn<Trajet, String> colHeureArr;

    private final TrajetDAO trajetDAO = new TrajetDAO();
    private final ObservableList<Trajet> trajets = FXCollections.observableArrayList();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colDepart.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getArretDepart().getNom()));
        colArrivee.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getArretArrivee().getNom()));
        colTrain.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTrain().getTypeTrain().getDisplayName()));
        colHeureDep.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHoraireDepartFormat()));
        colHeureArr.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHoraireArriveeFormat()));

        loadTrajets();
    }

    private void loadTrajets() {
        trajets.setAll(trajetDAO.getTousTrajets());
        tableTrajets.setItems(trajets);
    }

    @FXML
    void handleAjouter(ActionEvent event) {
        try {
            LocalDateTime depart = LocalDateTime.parse(txtHoraireDepart.getText(), formatter);
            LocalDateTime arrivee = LocalDateTime.parse(txtHoraireArrivee.getText(), formatter);
            int trainId = Integer.parseInt(txtTrainId.getText());

            int idDepart = Integer.parseInt(txtDepart.getText());
            int idArrivee = Integer.parseInt(txtArrivee.getText());

            Trajet trajet = new Trajet(depart, arrivee, trainId, idDepart, idArrivee);

            if (trajetDAO.ajouterTrajet(trajet)) {
                lblMessage.setText("Trajet ajouté !");
                trajets.add(trajet);
            } else {
                lblMessage.setText("Erreur d'ajout.");
            }

        } catch (Exception e) {
            lblMessage.setText("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        Trajet selected = tableTrajets.getSelectionModel().getSelectedItem();
        if (selected != null && trajetDAO.supprimerTrajet(selected.getId())) {
            trajets.remove(selected);
            lblMessage.setText("Trajet supprimé.");
        } else {
            lblMessage.setText("Sélectionner un trajet valide.");
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
            lblMessage.setText("Erreur de retour");
        }
    }
}
