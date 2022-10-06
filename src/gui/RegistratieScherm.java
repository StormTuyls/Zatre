package gui;

import domein.DomeinController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import resourceBundle.TaalKiezer;

public class RegistratieScherm extends GridPane {
	private StartScherm terug;
	private TaalKiezer taalKiezer = new TaalKiezer();

	public RegistratieScherm(StartScherm terug, DomeinController dc) {
		this.terug = terug;

		this.setPadding(new Insets(10));
		this.setHgap(10);
		this.setVgap(10);

		/**
		 * stelt de gekozen foto in als achtegrond
		 */
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage tiles = new BackgroundImage(
				new Image(getClass().getResourceAsStream("/Images/background.jpg"), 1920, 1080, false, false),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
		this.setBackground(new Background(tiles));

		/**
		 * maakt een label aan voor de titel van de pagina
		 */
		Label lblTitle = new Label(taalKiezer.getTaalBundle().getString("Registreer"));
		lblTitle.setFont(Font.font("Impact", FontWeight.NORMAL, 75));
		lblTitle.setTextFill(Color.WHITE);
		this.add(lblTitle, 0, 0, 2, 1);

		/**
		 * maakt een label en textfield aan voor de username van de nieuwe speler. De
		 * kleur wordt wit ingesteld met een bold gewicht en een grootte van 30 voor de
		 * label
		 * 
		 * het textfield krijgt een grootte van 15 en een normal gewicht
		 */
		Label lblUserName = new Label(taalKiezer.getTaalBundle().getString("Username"));
		lblUserName.setTextFill(Color.WHITE);
		lblUserName.setFont(Font.font("", FontWeight.BOLD, 30));
		this.add(lblUserName, 0, 2);
		TextField txfUser = new TextField(taalKiezer.getTaalBundle().getString("Username"));
		txfUser.setFont(Font.font("", FontWeight.NORMAL, 15));
		this.add(txfUser, 1, 2);
		this.setAlignment(Pos.CENTER);

		/**
		 * maakt een label en textfield aan voor de geboortedatum van de nieuwe speler
		 * De kleur wordt wit ingesteld met een grootte van 30 voor het label
		 * 
		 * het textfield krijgt een grootte van 15.
		 * 
		 */

		Label lblDate = new Label(taalKiezer.getTaalBundle().getString("Geboortedatum"));
		lblDate.setTextFill(Color.WHITE);
		lblDate.setFont(Font.font("", FontWeight.BOLD, 30));
		this.add(lblDate, 0, 3);
		TextField txfDate = new TextField(taalKiezer.getTaalBundle().getString("Geboortedatum"));
		txfDate.setFont(Font.font("", FontWeight.NORMAL, 15));
		this.add(txfDate, 1, 3);

		/**
		 * een knop voor het toevoegen van de nieuwe speler. De border heeft een radius
		 * van 20 en een zwarte achtergrond. De text in de knop krijgt een witte
		 * lettertype en een grootte van 20.
		 * 
		 */
		Button VoegToe = new Button(taalKiezer.getTaalBundle().getString("VoegToe"));
		VoegToe.setStyle("-fx-background-radius: 20;" + "-fx-background-color: black;");
		VoegToe.setFont(Font.font("", FontWeight.BOLD, 20));
		VoegToe.setTextFill(Color.WHITE);
		VoegToe.setCursor(Cursor.HAND);
		setHalignment(VoegToe, HPos.RIGHT);
		this.add(VoegToe, 1, 4);

		/**
		 * een knop voor het terugkeren naar het startscherm . De border heeft een
		 * radius van 20 en een zwarte achtergrond. De text in de knop krijgt een witte
		 * lettertype en een grootte van 20.
		 */
		Button BtnTerug = new Button(taalKiezer.getTaalBundle().getString("Terug"));
		BtnTerug.setCursor(Cursor.HAND);
		BtnTerug.setStyle("-fx-background-radius: 20;" + "-fx-background-color: black;");
		BtnTerug.setFont(Font.font("", FontWeight.BOLD, 20));
		BtnTerug.setTextFill(Color.WHITE);
		this.add(BtnTerug, 0, 4);
		GridPane.setHalignment(BtnTerug, HPos.LEFT);

		/**
		 * label voor de errors en het succesvol toevoegen van een nieuwe speler, deze
		 * wordt onzichtbaar gezet, en bij het drukken op de toevoeg knop zichtbaar
		 * gezet.
		 * 
		 */
		Label lblmessage = new Label();
		this.add(lblmessage, 0, 5, 2, 1);
		lblmessage.setVisible(false);
		lblmessage.setStyle(
				"-fx-background-radius: 10;" + "-fx-background-color: rgba(255,255,255,0.5);" + "-fx-padding:8px");
		GridPane.setHalignment(lblmessage, HPos.CENTER);

		/**
		 * Voegt een nieuwe speler toe aan de database of geeft een geldige error. Als
		 * dit succesvol gebeurt krijgt het label een groene kleur, bij een fout wordt
		 * dit rood.
		 */
		VoegToe.setOnAction(evt -> {
			try {

				String naam = txfUser.getText();
				String geboortejaar = txfDate.getText();

				int date = Integer.parseInt(geboortejaar.trim());
				dc.registreerNieuweSpeler(naam.trim(), date);
				String textje = String.format(taalKiezer.getTaalBundle().getString("registratie_succesvol"), naam,
						date);
				lblmessage.setFont(Font.font("", FontWeight.BOLD, 20));
				lblmessage.setTextFill(Color.GREEN);
				lblmessage.setText(textje);
				lblmessage.setVisible(true);

			} catch (NumberFormatException nf) {
				String textje = taalKiezer.getTaalBundle().getString("errorGetal");
				lblmessage.setFont(Font.font("", FontWeight.BOLD, 20));
				lblmessage.setTextFill(Color.RED);
				lblmessage.setText(textje);
				lblmessage.setVisible(true);

			} catch (IllegalArgumentException ae) {
				String textje = ae.getMessage();
				lblmessage.setFont(Font.font("", FontWeight.BOLD, 20));
				lblmessage.setTextFill(Color.RED);
				lblmessage.setText(textje);
				lblmessage.setVisible(true);

			} catch (RuntimeException ru) {
				String textje = taalKiezer.getTaalBundle().getString("errorBestaat");
				lblmessage.setFont(Font.font("", FontWeight.BOLD, 20));
				lblmessage.setTextFill(Color.RED);
				lblmessage.setText(textje);
				lblmessage.setVisible(true);

			}

		});
		/**
		 * event om terug naar startscherm te gaan.
		 */
		BtnTerug.setOnAction(event -> {
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(terug.getScene());
		});

	}
}