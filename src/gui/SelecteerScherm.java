package gui;

import java.util.Iterator;

import domein.DomeinController;
import domein.Speler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class SelecteerScherm extends GridPane {
	TaalKiezer taalKiezer = new TaalKiezer();
	Label error = new Label();

	public SelecteerScherm(StartScherm terug, DomeinController dc) {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		dc.refreshSpelers();
		ObservableList<String> spelerList = FXCollections.observableArrayList(dc.alleSpelersToString());

		error.setVisible(false);

		/**
		 * achtergrond object wordt aangemaakt en ingesteld
		 */
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage tiles = new BackgroundImage(
				new Image(getClass().getResourceAsStream("/Images/background.jpg"), 1920, 1080, false, false),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
		this.setBackground(new Background(tiles));

		/**
		 * titel van het scherm wordt aangemaakt
		 */
		Label lblTitle = new Label(taalKiezer.getTaalBundle().getString("kiesSpeler"));
		lblTitle.setFont(Font.font("Impact", FontWeight.NORMAL, 75));
		lblTitle.setTextFill(Color.WHITE);
		this.add(lblTitle, 0, 0, 2, 1);

		/**
		 * er worden 4 comboboxen aangemaakt om het gewenste aantal spelers te kunnen
		 * selecteren (max 4)
		 */

		// label speler 1
		Label Speler1 = new Label(taalKiezer.getTaalBundle().getString("speler1") + " 1");
		Speler1.setTextFill(Color.WHITE);
		Speler1.setFont(Font.font("Impact", FontWeight.NORMAL, 20));
//		setHalignment(Speler1, HPos.CENTER);
		this.add(Speler1, 0, 2);
		final ComboBox comboBox = new ComboBox(spelerList);
		setHalignment(comboBox, HPos.LEFT);
		this.add(comboBox, 1, 2);
		this.setAlignment(Pos.CENTER);

		// label speler 2
		Label Speler2 = new Label(taalKiezer.getTaalBundle().getString("speler1") + " 2");
		Speler2.setTextFill(Color.WHITE);
		Speler2.setFont(Font.font("Impact", FontWeight.NORMAL, 20));
		this.add(Speler2, 0, 3);
		final ComboBox comboBox2 = new ComboBox(spelerList);
		this.add(comboBox2, 1, 3);

		// label speler 3
		Label Speler3 = new Label(taalKiezer.getTaalBundle().getString("speler1") + " 3");
		Speler3.setFont(Font.font("Impact", FontWeight.NORMAL, 20));
		Speler3.setTextFill(Color.WHITE);
		this.add(Speler3, 0, 4);
		final ComboBox comboBox3 = new ComboBox(spelerList);
		this.add(comboBox3, 1, 4);

		// label speler 4
		Label Speler4 = new Label(taalKiezer.getTaalBundle().getString("speler1") + " 4");
		Speler4.setFont(Font.font("Impact", FontWeight.NORMAL, 20));
		Speler4.setTextFill(Color.WHITE);
		this.add(Speler4, 0, 5);
		final ComboBox comboBox4 = new ComboBox(spelerList);
		this.add(comboBox4, 1, 5);

		// button
		Button Selecteer = new Button(taalKiezer.getTaalBundle().getString("Speel"));
		Selecteer.setStyle("-fx-background-radius: 20;" + "-fx-background-color: black;");
		Selecteer.setCursor(Cursor.HAND);
		Selecteer.setFont(Font.font("", FontWeight.BOLD, 20));
		Selecteer.setTextFill(Color.WHITE);
		setHalignment(Selecteer, HPos.CENTER);
		this.add(Selecteer, 1, 6);

		// actions
		Selecteer.setOnAction(EventTarget -> {
			dc.clearGeselecteerdeSpelers();

			String speler1 = (String) comboBox.getSelectionModel().getSelectedItem();
			String speler2 = (String) comboBox2.getSelectionModel().getSelectedItem();
			String speler3 = (String) comboBox3.getSelectionModel().getSelectedItem();
			String speler4 = (String) comboBox4.getSelectionModel().getSelectedItem();

			if (String.valueOf(speler1).equals(speler2) || String.valueOf(speler2).equals(speler3)
					|| String.valueOf(speler3).equals(speler4) || String.valueOf(speler2).equals(speler4)
					|| String.valueOf(speler1).equals(speler3) || String.valueOf(speler1).equals(speler4)) {
				error.setText(taalKiezer.getTaalBundle().getString("errorAlGeselecteerd"));
				error.setVisible(true);
				
			} else {
				String[] s1 = null, s2 = null, s3 = null, s4 = null;
				int g1 = 0, g2 = 0, g3 = 0, g4 = 0;

				try {
					if (speler1 != null)
						s1 = speler1.split(",");
					else {
						error.setText(taalKiezer.getTaalBundle().getString("errorspeler1"));
						error.setVisible(true);
					}
					if (speler2 != null)
						s2 = speler2.split(",");
					else {
						error.setText(taalKiezer.getTaalBundle().getString("errorspeler2"));
						error.setVisible(true);
					}
					if (speler3 != null)
						s3 = speler3.split(",");
					if (speler4 != null)
						s4 = speler4.split(",");

					if (speler1 != null)
						g1 = Integer.parseInt(s1[1].substring(1));
					if (speler2 != null)
						g2 = Integer.parseInt(s2[1].substring(1));
					if (speler3 != null)
						g3 = Integer.parseInt(s3[1].substring(1));
					if (speler4 != null)
						g4 = Integer.parseInt(s4[1].substring(1));

					if (speler1 != null)
						selecteerSpeler(s1[0], g1, dc);
					if (speler2 != null)
						selecteerSpeler(s2[0], g2, dc);
					if (speler3 != null)
						selecteerSpeler(s3[0], g3, dc);
					if (speler4 != null)
						selecteerSpeler(s4[0], g4, dc);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				if (dc.getGeselecteerdeSpelers().size() >= 2) {
					SpelScherm rs = new SpelScherm(terug, dc);
					Scene scene3 = new Scene(rs, 1280, 720);
					Stage stage1 = (Stage) SelecteerScherm.this.getScene().getWindow();
					stage1.setScene(scene3);
					stage1.show();
				}
				else {
					error.setText(taalKiezer.getTaalBundle().getString("erroraantalSpelers"));
					error.setVisible(true);
				}
				try {
					
				
				if(Integer.parseInt(String.valueOf(s1[2].trim())) == 0 || Integer.parseInt(String.valueOf(s2[2].trim())) == 0 || Integer.parseInt(String.valueOf(s3[2].trim()))==0 ||  Integer.parseInt(String.valueOf(s4[2].trim())) == 0 )
					error.setText(taalKiezer.getTaalBundle().getString("errorGeenSpeelkansen"));
				} catch (Exception e) {
					
				}
			}

		});

		// terugknop
		Button BtnTerug = new Button(taalKiezer.getTaalBundle().getString("Terug"));
		BtnTerug.setStyle("-fx-background-radius: 20;" + "-fx-background-color: black;");
		BtnTerug.setCursor(Cursor.HAND);
		BtnTerug.setFont(Font.font("", FontWeight.BOLD, 20));
		BtnTerug.setTextFill(Color.WHITE);
		this.add(BtnTerug, 0, 6);
		GridPane.setHalignment(BtnTerug, HPos.LEFT);

		BtnTerug.setOnAction(event -> {
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(terug.getScene());
		});
		error.setTextFill(Color.RED);
		error.setStyle(
				"-fx-background-radius: 10;" + "-fx-background-color: rgba(255,255,255,0.5);" + "-fx-padding:8px");
		error.setFont(Font.font("", FontWeight.BOLD, 20));
		this.add(error, 0, 7, 3, 1);
	}

	// functies
	private void selecteerSpeler(String naam, int geboortejaar, DomeinController dc) {
		error.setStyle(
				"-fx-background-radius: 10;" + "-fx-background-color: rgba(255,255,255,0.5);" + "-fx-padding:8px");
		error.setFont(Font.font("", FontWeight.BOLD, 20));
		if (naam != null && !naam.isBlank()) {
			System.out.println(naam + geboortejaar);
			try {
				dc.selecteerSpeler(naam.trim(), geboortejaar);
			} catch (IllegalArgumentException e) {
				error.setText(naam + " : " + e.getMessage());
				error.setVisible(true);
			}

		} else {
			error.setText(taalKiezer.getTaalBundle().getString("errorBestaat"));
			error.setVisible(true);
		}
		error.setStyle(
				"-fx-background-radius: 10;" + "-fx-background-color: rgba(255,255,255,0.5);" + "-fx-padding:8px");
		error.setFont(Font.font("", FontWeight.BOLD, 20));

	}
}
