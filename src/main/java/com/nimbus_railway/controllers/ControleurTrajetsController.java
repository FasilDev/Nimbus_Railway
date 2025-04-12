package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.TrajetDAO;
import com.nimbus_railway.models.Trajet;
import com.nimbus_railway.models.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ControleurTrajetsController {

    @FXML private TableView<Trajet> tableTrajets;
    @FXML private TableColumn<Trajet, Integer> colId;
    @FXML private TableColumn<Trajet, String> colDepart;
    @FXML private TableColumn<Trajet, String> colArrivee;
    @FXML private TableColumn<Trajet, String> colHeureDep;
    @FXML private TableColumn<Trajet, String> colHeureArr;
    @FXML private TableColumn<Trajet, String> colTrain;

    private Utilisateur utilisateur;

    private final TrajetDAO trajetDAO = new TrajetDAO();

    public void initData(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        loadTrajets();
    }

    private void loadTrajets() {
        ObservableList<Trajet> data = FXCollections.observableArrayList(trajetDAO.getAll());
        tableTrajets.setItems(data);
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDepart.setCellValueFactory(new PropertyValueFactory<>("depart"));
        colArrivee.setCellValueFactory(new PropertyValueFactory<>("arrivee"));
        colHeureDep.setCellValueFactory(new PropertyValueFactory<>("horaireDepart"));
        colHeureArr.setCellValueFactory(new PropertyValueFactory<>("horaireArrivee"));
        colTrain.setCellValueFactory(new PropertyValueFactory<>("train"));
    }

    @FXML
    private void handleRetour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/controleur_dashboard.fxml"));
            Parent root = loader.load();

            ControleurController controller = loader.getController();
            controller.initData(utilisateur);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard Contrôleur");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
