package main;

import domein.DomeinController;
import gui.StartScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class startUp2 extends Application {

	@Override
	public void start(Stage primaryStage) {
		DomeinController dc = new DomeinController();
		StartScherm border = new StartScherm(dc);
		Scene scene = new Scene(border, 1280, 720);
		scene.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Zatre");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
