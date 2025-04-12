package com.nimbus_railway.controllers;

import com.nimbus_railway.dao.EleveDAO;
import com.nimbus_railway.models.Eleve;
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

public class ControleurElevesController {

    @FXML private TableView<Eleve> tableEleves;
    @FXML private TableColumn<Eleve, Integer> colId;
    @FXML private TableColumn<Eleve, String> colNom;
    @FXML private TableColumn<Eleve, String> colPrenom;
    @FXML private TableColumn<Eleve, String> colMaison;

    private Utilisateur utilisateur;

    private final EleveDAO eleveDAO = new EleveDAO();

    public void initData(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        loadEleves();
    }

    private void loadEleves() {
        ObservableList<Eleve> data = FXCollections.observableArrayList(eleveDAO.getAll());
        tableEleves.setItems(data);
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colMaison.setCellValueFactory(new PropertyValueFactory<>("maison"));
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
