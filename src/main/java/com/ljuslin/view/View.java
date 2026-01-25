package com.ljuslin.view;

import javafx.scene.control.Alert;

/**
 * For all views to extend, has alert-methods
 *
 * @author Tina Ljuslin
 */
public abstract class View {
    public void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }
}
