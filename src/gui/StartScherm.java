package gui;

import java.util.Optional;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import resourceBundle.TaalKiezer;

public class StartScherm extends GridPane {
	private final DomeinController dc;
	private RegistratieScherm rs;
	private TaalKiezer taalKiezer = new TaalKiezer();

	public StartScherm(DomeinController dc) {
		this.dc = dc;
		buildGui();
	}

	private void buildGui() {

		this.setPadding(new Insets(20));
		this.setHgap(10);
		this.setVgap(10);

		/**
		 * achtergrond krijgt een resolute van 1920 op 1080 en word centraal op het
		 * scherm gezet.
		 */
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage tiles = new BackgroundImage(
				new Image(getClass().getResourceAsStream("/Images/background.jpg"), 1920, 1080, false, false),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setHgrow(Priority.ALWAYS);
		ColumnConstraints col2 = new ColumnConstraints();
		this.getColumnConstraints().addAll(col1, col2);

		/**
		 * aanmaken van de foto die later gebruikt zal worden als knop voor het verlaten
		 * van de applicatie.
		 */
		Image exitlogo = new Image(getClass().getResourceAsStream("/Images/exitWHITE.png"));
		ImageView out = new ImageView(exitlogo);
		out.setFitHeight(50);
		out.setFitWidth(50);

		/**
		 * aanmaken van de foto voor het veranderen van de taal naar het Nederlands.
		 */
		Image taalNL = new Image(getClass().getResourceAsStream("/Images/Nl_icon.png"));
		ImageView nl = new ImageView(taalNL);
		nl.setFitHeight(50);
		nl.setFitWidth(50);

		/**
		 * aanmaken van de foto voor het veranderen van de taal naar het Engels.
		 */
		Image taalENG = new Image(getClass().getResourceAsStream("/Images/UK_icon.png"));
		ImageView eng = new ImageView(taalENG);
		eng.setFitHeight(50);
		eng.setFitWidth(50);

		/**
		 * Er wordt een label aangemaakt die de titel van het spel toont. De label wordt
		 * geplaatst in de linkerbovenhoek. Het krijgt het lettertype Impact en een
		 * lettergrootte van 117.
		 */
		Label titel = new Label("ZATRE");
		titel.setFont(Font.font("Impact", FontWeight.NORMAL, 117));
		titel.setTextFill(Color.WHITE);
		Glow glow = new Glow();
		glow.setLevel(1);
		titel.setEffect(glow);
		titel.setAlignment(Pos.TOP_LEFT);
		this.add(titel, 0, 0, 2, 1);

		/**
		 * Dit is een knop die u brengt naar het registatie scherm, dit event komt later
		 * aan bod. De knop krijgt een min hoogte van 40 en een min breedte van 40. De
		 * achtergrond wordt een beetje transparant gemaakt en krijgt de kleur zwart.
		 * Het letterype is Impact met een grootte 25.
		 */
		Button RegistreerSpeler = new Button(taalKiezer.getTaalBundle().getString("Registreer"));
		RegistreerSpeler.setMinWidth(60);
		RegistreerSpeler.setMinHeight(40);
		RegistreerSpeler.setStyle("-fx-background-radius: 15;" + "-fx-background-color: rgba(0,0,0,0.8);");
		RegistreerSpeler.setCursor(Cursor.HAND);
		RegistreerSpeler.setTextFill(Color.WHITE);
		RegistreerSpeler.setFont(Font.font("Impact", FontWeight.NORMAL, 25));
		this.add(RegistreerSpeler, 0, 4);
		GridPane.setHalignment(RegistreerSpeler, HPos.CENTER);

		/**
		 * Dit is een knop die u brengt naar het speel scherm. De knop krijgt een min
		 * hoogte van 40 en een min breedte van 40. De achtergrond wordt een beetje
		 * transparant gemaakt en krijgt de kleur zwart. Het letterype is Impact met een
		 * grootte 25. Het event maakt een nieuw scherm aan met een grootte van 1280 op
		 * 720.
		 */
		Button SpeelSpel = new Button(taalKiezer.getTaalBundle().getString("Speel"));
		SpeelSpel.setMinWidth(100);
		SpeelSpel.setMinHeight(40);
		SpeelSpel.setStyle("-fx-background-radius: 30;" + "-fx-background-color: rgba(0,0,0,0.8);");
		SpeelSpel.setCursor(Cursor.HAND);
		SpeelSpel.setTextFill(Color.WHITE);
		SpeelSpel.setFont(Font.font("Impact", FontWeight.NORMAL, 75));
		this.add(SpeelSpel, 0, 2);
		GridPane.setHalignment(SpeelSpel, HPos.CENTER);
		GridPane.setValignment(SpeelSpel, VPos.CENTER);
		SpeelSpel.setOnAction(EventTarget -> {
			SelecteerScherm rs = new SelecteerScherm(this, dc);
			Scene scene2 = new Scene(rs, 1280, 720);
			Stage stage = (Stage) StartScherm.this.getScene().getWindow();
			stage.setScene(scene2);
			stage.show();
		});

		/**
		 * Een knop die ervoor zorgt dat je de applicatie kan sluiten. Hierbij wordt de
		 * eerder aangemaakte foto gebruikt.
		 */
		Button exit = new Button();
		exit.setPrefSize(5, 5);
		exit.setStyle("-fx-background-color: transparent;");
		exit.setGraphic(out);
//		exit.setAlignment(Pos.TOP_RIGHT);
		exit.setCursor(Cursor.HAND);
		GridPane.setHalignment(exit, HPos.RIGHT);
		this.add(exit, 1, 0);
//		GridPane.setHalignment(exit, HPos.RIGHT);

		/**
		 * knop voor het wijzigen van de taal naar het nederlands
		 */
		Button taalButton = new Button();
		taalButton.setGraphic(nl);
		taalButton.setStyle("-fx-background-color: transparent;");
		taalButton.setCursor(Cursor.HAND);
		this.add(taalButton, 0, 19);
		GridPane.setHalignment(taalButton, HPos.LEFT);
		GridPane.setValignment(taalButton, VPos.BOTTOM);
		taalButton.setOnAction(evt -> {
			taalKiezer.setTaal(1);

			RegistreerSpeler.setText(taalKiezer.getTaalBundle().getString("Registreer"));
			SpeelSpel.setText(taalKiezer.getTaalBundle().getString("Speel"));
			exit.setText(taalKiezer.getTaalBundle().getString("Quit"));
		});

		/**
		 * knop voor het wijzigen van de taal naar het engels
		 */
		Button taalButton2 = new Button();
		taalButton2.setGraphic(eng);
		taalButton2.setStyle("-fx-background-color: transparent;");
		taalButton2.setCursor(Cursor.HAND);
		this.add(taalButton2, 0, 20);
		GridPane.setHalignment(taalButton2, HPos.LEFT);
		GridPane.setValignment(taalButton2, VPos.BOTTOM);
		taalButton2.setOnAction(evt2 -> {
			taalKiezer.setTaal(2);

			RegistreerSpeler.setText(taalKiezer.getTaalBundle().getString("Registreer"));
			SpeelSpel.setText(taalKiezer.getTaalBundle().getString("Speel"));
			exit.setText(taalKiezer.getTaalBundle().getString("Quit"));
		});

		exit.setOnAction(this::quit);
		RegistreerSpeler.setOnAction(this::naarRegistratieScherm);
		// SpeelSpel.setOnAction(this::naarSpeelScherm);

		this.setBackground(new Background(tiles));
//		this.getChildren().addAll(logo);

	}

	/**
	 * event voor het sluiten van de applicatie. Eens gedrukt komt er een popup
	 * tevoorschijn die vraagt dit te bevestigen.
	 */
	public void quit(Event event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(taalKiezer.getTaalBundle().getString("quit_Titel"));
		alert.setContentText(taalKiezer.getTaalBundle().getString("quit_bevestig"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// System.out.println("We sluiten het venster en dus ook de applicatie")
			Platform.exit();
		} else { // cancel
			event.consume();
		}

	}

	/**
	 * het registratiescherm wordt hier aangemaakt, het krijgt een grootte van 1280
	 * op 720.
	 */
	public void naarRegistratieScherm(Event event) {
		RegistratieScherm rs = new RegistratieScherm(this, dc);
		Scene scene2 = new Scene(rs, 1280, 720);
		Stage stage = (Stage) StartScherm.this.getScene().getWindow();
		stage.setScene(scene2);
		stage.show();
	}

}
