package com.telecom;

import com.telecom.controller.DashboardController;
import com.telecom.repository.DataStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        DataStore.seed();
        DashboardController controller = new DashboardController(stage);
        Scene scene = new Scene(controller.getView(), 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Telecom SIM Activation & Billing System");
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
