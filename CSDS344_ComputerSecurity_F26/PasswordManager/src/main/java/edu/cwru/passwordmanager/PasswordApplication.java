package edu.cwru.passwordmanager;

import java.io.IOException;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PasswordApplication extends Application {
    // TODO: Give your app a anme!
    final private String applicationName = "Locksmith";
    static Stage primaryStage = null;
    @Override
    public void start(Stage stage) throws IOException {
        // TODO: Select Preferred
        //  Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        FXMLLoader fxmlLoader = new FXMLLoader(PasswordApplication.class.getResource("initial-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle(applicationName);
        stage.setScene(scene);
        primaryStage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}