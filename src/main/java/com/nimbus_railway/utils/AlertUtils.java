package com.nimbus_railway.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Classe utilitaire pour gérer les boîtes de dialogue d'alerte
 */
public class AlertUtils {

    /**
     * Affiche une boîte de dialogue d'information
     * @param title Titre de la boîte de dialogue
     * @param header En-tête de la boîte de dialogue
     * @param content Contenu de la boîte de dialogue
     */
    public static void showInformation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue d'erreur
     * @param title Titre de la boîte de dialogue
     * @param header En-tête de la boîte de dialogue
     * @param content Contenu de la boîte de dialogue
     */
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue d'avertissement
     * @param title Titre de la boîte de dialogue
     * @param header En-tête de la boîte de dialogue
     * @param content Contenu de la boîte de dialogue
     */
    public static void showWarning(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue de confirmation
     * @param title Titre de la boîte de dialogue
     * @param header En-tête de la boîte de dialogue
     * @param content Contenu de la boîte de dialogue
     * @return true si l'utilisateur a confirmé, false sinon
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}