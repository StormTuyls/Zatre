package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.cj.protocol.Message;

import domein.Bord;
import domein.DomeinController;
import domein.Pot;
import domein.Spel;
import domein.Speler;
import domein.Steen;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import resourceBundle.TaalKiezer;

public class SpelScherm extends BorderPane {

	private TaalKiezer taalKiezer = new TaalKiezer();
	private ToggleButton btnVak, btnSteentje, selectedSteen, vakSelected;
	private Bord bord;
	private int beurt = 0, spelerAanBeurt = 0, kolom10 = 0, kolom11 = 0, kolom12 = 0, somHorizontaal, somVerticaal;
	private GridPane bordPane, scorebladPane;
	private HBox menuBox, steentjesBox;
	private Spel spel;
	private Pot pot;
	private Label scoreblad = new Label(), lblmessage;
	private List<Speler> spelers;
	private List<ToggleButton> gelegdeSteentjes, steentjesOpBord, btnSteentjesInHand, stenenInPot;
	private ToggleButton[][] spelbord;
	private Button btnEndTurn, btnLegInPot, btnNeemSteentjesTerug;
	private List<Steen> steentjes;
	private boolean eindValidatie = true, verticaleSomAanwezig = false, horizontaleSomAanwezig = false,
			verdubbelingPunten = false, isTopOccupied, isBottomOccupied, isLeftOccupied, isRightOccupied;;
	private List<Boolean> steenValidaties;

	/**
	 * Het SpelScherm wordt aangemaakt met de nodige events
	 */
	public SpelScherm(StartScherm terug, DomeinController dc) {
		this.getStylesheets().add(getClass().getResource("/main/application.css").toExternalForm());
		this.setPadding(new Insets(10));

		dc.startSpel();
		spel = dc.getSpel();
		bord = spel.getBord();
		pot = spel.getPot();
		spelers = dc.getGeselecteerdeSpelers();
		bordPane = new GridPane();
		scorebladPane = new GridPane();
		menuBox = new HBox();
		steentjesBox = new HBox();
		btnSteentjesInHand = new ArrayList<>();
		gelegdeSteentjes = new ArrayList<>();
		steentjesOpBord = new ArrayList<>();
		spelbord = new ToggleButton[15][15];
		stenenInPot = new ArrayList<>();

		// gepaste meldingen
		lblmessage = new Label();
		lblmessage.setTextFill(Color.RED);
		lblmessage.setStyle("-fx-font-size: 25px");
		this.setTop(lblmessage);

		GridPane.setValignment(lblmessage, VPos.TOP);
		lblmessage.setVisible(false);

		/** buttons voor bord aanmaken */
		for (int rij = 0; rij < 15; rij++) {
			for (int kolom = 0; kolom < 15; kolom++) {
				btnVak = new ToggleButton();
				btnVak.setMinWidth(30);
				btnVak.setMinHeight(30);
				if (bord.getVak(rij, kolom).getKleur() == 0) {

					btnVak.getStyleClass().addAll("btnVakWit");

				} else {
					if (bord.getVak(rij, kolom).getKleur() != 2) {
						btnVak.getStyleClass().addAll("btnVakGrijs");
					}
				}
				if (bord.getVak(rij, kolom).getKleur() != 2) {
					bordPane.add(btnVak, rij, kolom);
					btnVak.getStyleClass().addAll("btnVak", "vakSelected", "vakDisabled");
				}
				spelbord[rij][kolom] = btnVak;

				/** Vak Events */
				btnVak.setOnMouseClicked(selectVak -> {
					/** Een Vak wordt geselecteerd en bijgehouden. */
					vakSelected = (ToggleButton) selectVak.getSource();

					if (vakSelected.isSelected()) {
						vakSelected.setSelected(true);
						/**
						 * Indien er geen steen geselecteerd is, wordt er een gepaste melding getoond.
						 */
						if (selectedSteen == null) {
							lblmessage.setText(taalKiezer.getTaalBundle().getString("geenSteenGeselecteerd"));
							lblmessage.setVisible(true);
							vakSelected.setSelected(false);
							vakSelected = null;
						} else // bezet vak geselecteerd
						/**
						 * Indien het geselecteerde vak al een steen heeft, wordt er een gepaste melding
						 * getoond.
						 */
						if (!vakSelected.getText().isBlank()) {
							lblmessage.setText(taalKiezer.getTaalBundle().getString("vakBezet"));
							lblmessage.setVisible(true);
							vakSelected.setSelected(false);
							vakSelected = null;
						} else
						/** Bij de eerste beurt moet de eerste steen in het centrum gelegd worden. */
						if (beurt == 1 && spelbord[7][7].getText().isBlank()) {
							if (GridPane.getColumnIndex(vakSelected) == 7 && GridPane.getRowIndex(vakSelected) == 7) {
								/**
								 * De steen wordt in het centrum gelegd. De steentjes die in eenzelfde beurt
								 * zijn gelegd worden bijgehouden in gelegdeSteentjes. De gelegde steen wordt
								 * uit de hand 'verwijderd'.
								 */
								vakSelected.setText(selectedSteen.getText());
								selectedSteen.setDisable(true);
								selectedSteen.setSelected(false);
								vakSelected.setSelected(false);
								bord.getVak(GridPane.getRowIndex(vakSelected), GridPane.getColumnIndex(vakSelected))
										.setHeeftSteen(Integer.parseInt(selectedSteen.getText()));
								gelegdeSteentjes.add(vakSelected);
								btnSteentjesInHand.remove(selectedSteen);
								vakSelected = null;
								selectedSteen = null;
								lblmessage.setVisible(false);
								btnLegInPot.setVisible(false);
								btnNeemSteentjesTerug.setVisible(true);

							} else {
								/**
								 * Indien de eerste steen niet in het centrum gelegd wordt, wordt er een gepaste
								 * melding getoond.
								 */
								lblmessage.setText(taalKiezer.getTaalBundle().getString("eersteSteen"));
								lblmessage.setVisible(true);
								vakSelected.setSelected(false);
								vakSelected = null;
							}

						} else {
							/**
							 * De steen wordt in het geselecteerde vak gelegd. De steentjes die in eenzelfde
							 * beurt zijn gelegd worden bijgehouden in gelegdeSteentjes. De gelegde steen
							 * wordt uit de hand 'verwijderd'.
							 */
							vakSelected.setText(selectedSteen.getText());
							selectedSteen.setDisable(true);
							selectedSteen.setSelected(false);
							vakSelected.setSelected(false);
							bord.getVak(GridPane.getRowIndex(vakSelected), GridPane.getColumnIndex(vakSelected))
									.setHeeftSteen(Integer.parseInt(selectedSteen.getText()));
							gelegdeSteentjes.add(vakSelected);
							btnSteentjesInHand.remove(selectedSteen);
							vakSelected = null;
							selectedSteen = null;
							lblmessage.setVisible(false);
							btnLegInPot.setVisible(false);
							btnNeemSteentjesTerug.setVisible(true);

						}
					}

				}

				);
			}
		}

		bordPane.setAlignment(Pos.CENTER);
		this.setCenter(bordPane);

		/** quit knop */
		Button stop = new Button(taalKiezer.getTaalBundle().getString("back_Titel"));
		stop.setMinWidth(175);
		stop.setStyle("-fx-background-radius: 20;" + "-fx-background-color: red;");
		stop.setCursor(Cursor.HAND);
		stop.setFont(Font.font("", FontWeight.BOLD, 20));
		stop.setTextFill(Color.WHITE);

		/** End Turn knop */
		btnEndTurn = new Button(taalKiezer.getTaalBundle().getString("startSpel"));
		btnEndTurn.getStyleClass().add("btnEndTurnStartSpel");
		btnEndTurn.setPrefSize(150, 150);
		btnEndTurn.setStyle("-fx-background-radius: 20;");
		btnEndTurn.setCursor(Cursor.HAND);
		btnEndTurn.setFont(Font.font("impact", FontWeight.BOLD, 20));

		/** Terug in Pot knop */
		btnLegInPot = new Button(taalKiezer.getTaalBundle().getString("legInPot"));
		btnLegInPot.getStyleClass().add("btnEndTurnDefault");
		btnLegInPot.setMinSize(150, 40);
		btnLegInPot.setVisible(false);
		btnLegInPot.setCursor(Cursor.HAND);

		/** Undo knop */
		btnNeemSteentjesTerug = new Button(taalKiezer.getTaalBundle().getString("neemSteentjesTerug"));
		btnNeemSteentjesTerug.getStyleClass().add("btnEndTurnDefault");
		btnNeemSteentjesTerug.setMinSize(150, 40);
		btnNeemSteentjesTerug.setVisible(false);
		btnNeemSteentjesTerug.setCursor(Cursor.HAND);

		// behoudt afstand tussen buttons in menuBox
		Region region1 = new Region();
		Region region2 = new Region();
		Region region3 = new Region();
		Region region4 = new Region();

		HBox.setHgrow(region2, Priority.ALWAYS);
		HBox.setHgrow(region1, Priority.ALWAYS);
		HBox.setHgrow(region3, Priority.ALWAYS);
		HBox.setHgrow(region4, Priority.ALWAYS);

		HBox.setHgrow(btnEndTurn, Priority.NEVER);
		VBox.setVgrow(stop, Priority.NEVER);
		HBox.setHgrow(btnLegInPot, Priority.ALWAYS);
		HBox.setHgrow(btnNeemSteentjesTerug, Priority.ALWAYS);

		menuBox.setAlignment(Pos.CENTER_LEFT);
		menuBox.getChildren().addAll(btnEndTurn, region1, steentjesBox, region3, btnLegInPot, region2,
				btnNeemSteentjesTerug, region4, stop);
		this.setBottom(menuBox);

		/** Scorebord aanmaken */
		scoreblad.setFont(new Font("Courier New", 11));
//	scoreblad.getStyleClass().add("scorebordLay");
		scorebladPane.add(scoreblad, 1, 5);

		this.setRight(scorebladPane);

		/** Label voor de speler die aan beurt is. */
		Label lblSpeler = new Label();
		lblSpeler.getStyleClass().add("lblScorebladKolomTitels");
		scorebladPane.add(lblSpeler, 1, 0, 2, 1);
		lblSpeler.setVisible(false);

		/** Label voor de beurt */
		Label lblBeurt = new Label();
		lblBeurt.getStyleClass().add("lblScorebladKolomTitels");
		scorebladPane.add(lblBeurt, 0, 0);
		lblBeurt.setVisible(false);
		
		/** spelers speelkansen aanpassen in database */
		

		/** Next Turn */
		btnEndTurn.setOnMouseClicked(nextTurn -> {
			/** Als alle 121 stenen gelegd zijn, wordt het spel beëindigt. */
			if (spel.isEindeSpel()) {
				eindScene(terug,dc);
			}
			else {
			
			

			/**
			 * Als er nog steentjes zijn die gelegd kunnen worden en men probeert naar de
			 * volgende beurt te gaan wordt er een gepaste melding getoond. Validatie wordt
			 * dan op 'false' gezet.
			 */
			if (!btnSteentjesInHand.isEmpty()) {
				lblmessage.setText(taalKiezer.getTaalBundle().getString("steentjesNietLeeg"));
				lblmessage.setVisible(true);
				eindValidatie = false;
			} else {
				/**
				 * Als alle steentjes, die een speler heeft, gelegd zijn, worden de stenen
				 * gecontroleerd of ze volgens de spelregels juist liggen. Elke steen die gelegd
				 * is, wordt nagekeken.
				 */
				lblmessage.setVisible(false);
				steenValidaties = new ArrayList<>();

				btnNeemSteentjesTerug.setVisible(false);

				horizontaleSomAanwezig = false;
				verticaleSomAanwezig = false;

				for (ToggleButton gelegdSteentje : gelegdeSteentjes) {
					somHorizontaal = Integer.parseInt(gelegdSteentje.getText());

					somVerticaal = Integer.parseInt(gelegdSteentje.getText());

					isTopOccupied = false;
					isBottomOccupied = false;
					isLeftOccupied = false;
					isRightOccupied = false;

					/**
					 * Beide de verticale en horizontale som worden bepaald. De booleans
					 * verticaleSomAanwezig en horizontaleSomAanwezig zorgen er bij de berekening
					 * van de score voor dat er geen scores dubbel gerekend worden
					 */
					somHorizontaal = somHorizontaal(gelegdSteentje, somHorizontaal);
					somVerticaal = somVerticaal(gelegdSteentje, somVerticaal);

					System.out.printf("%d, %d\n", somVerticaal, somHorizontaal);
					/**
					 * De gelegde steen wordt gecontrolleerd op zijn ligging tegenover de andere
					 * stenen. Deze booleans worden vastgezet in somVerticaal en somHorizontaal.
					 */

					if (isLeftOccupied || isRightOccupied || isBottomOccupied || isTopOccupied) {
						eindValidatie = true;
					} else {
						eindValidatie = false;
					}

					/** De sommen worden gecontrolleerd dat ze 12 niet overschrijden */
					if (somVerticaal > 12 || somHorizontaal > 12) {
						eindValidatie = false;
					}

					/** De gelegde steen wordt gecontrolleerd indien die op een grijs vak ligt. */
					if (beurt != 1 && bord
							.getVak(GridPane.getColumnIndex(gelegdSteentje), GridPane.getRowIndex(gelegdSteentje))
							.getKleur() == 1) {
						verdubbelingPunten = true;
						eindValidatie = false;
						if (((somHorizontaal >= 10 && somHorizontaal <= 12)
								|| (somVerticaal >= 10 && somVerticaal <= 12))) {
							eindValidatie = true;
						}
						else 
							{
								lblmessage.setVisible(true);
								lblmessage.setText(taalKiezer.getTaalBundle().getString("validatieGefaald"));
							}

					}
					/**
					 * Indien een van de sommen binnen het bereik [10, 12] ligt wordt de score
					 * berekent voor het scorebord.
					 */
					if (((somHorizontaal >= 10 && somHorizontaal <= 12)
							|| (somVerticaal >= 10 && somVerticaal <= 12))) {
						berekenScore(somHorizontaal, somVerticaal);
					}
					/** De validaties van de stenen worden 'gemapped' naar een boolean */
					steenValidaties.add(eindValidatie);
				}
				/**
				 * Indien een van de stenen verkeerd ligt, wordt de uiteindelijke validatie
				 * 'false'. Dit zou dan validatieGefaald() oproepen.
				 */
				if (steenValidaties.contains(false)) {
					eindValidatie = false;
				} else {
					eindValidatie = true;
				}

			}

			/**
			 * Als de validatie klopt wordt er overgegaan naar de volgende beurt en speler.
			 */
			if (eindValidatie) {
				if (beurt != 0)
					updateScoreblad();
				beurt++;

				steentjes = spelers.get(spelerAanBeurt).geefSteentjesUitPot(pot, beurt);
				spelerAanBeurt++;

				/**
				 * Als de laatste speler aan beurt is, wordt er vanaf de volgende beurt
				 * herbegonnen met speler 1.
				 */
				if (spelerAanBeurt == spelers.size()) {
					spelerAanBeurt = 0;

				}
				scorebladToLabel();
				clearScoreblad();

				if (beurt == 1) {
					btnEndTurn.setText(taalKiezer.getTaalBundle().getString("End_Turn"));
					btnEndTurn.setPrefSize(150, 40);
					btnEndTurn.getStyleClass().add("btnEndTurnDefault");

				} else {
					steentjesOpBord.addAll(gelegdeSteentjes);
					gelegdeSteentjes.forEach(steen -> {
						steen.getStyleClass().clear();
						steen.getStyleClass().add("steenOud");
					});
					gelegdeSteentjes.clear();
					stenenInPot.clear();
				}
				steentjesBox.getChildren().clear();

				lblSpeler.setText(spelers.get(spelerAanBeurt).getNaam());
				lblBeurt.setText(String.format("%d", beurt));
				lblSpeler.setVisible(true);
				lblBeurt.setVisible(true);
			} else {
				/** Als de validatie niet klopt, worden de stenen teruggegeven. */
				validatieGefaald();
			}

			for (int i = 0; i < steentjes.size(); i++) {
				if (eindValidatie) {
					/**
					 * Voor elk steentje dat een speler krijgt, wordt er een ToggleButton
					 * aangemaakt.
					 */

					btnSteentje = new ToggleButton(String.format("%d", steentjes.get(i).getWaarde()));
					btnSteentje.setPrefSize(100, 100);
					btnSteentje.setPrefSize(100, 100);
					btnSteentje.getStyleClass().addAll("steenDefault", "steenSelected");
					btnSteentjesInHand.add(btnSteentje);

					steentjesBox.getChildren().addAll(btnSteentje);
				}

				/** Steen Event */
				btnSteentje.setOnMouseClicked(selectSteen -> {
					/** De geselecteerde steen wordt bijgehouden */
					selectedSteen = ((ToggleButton) selectSteen.getSource());
					/** De steen wordt geselecteerd. */
					if (selectedSteen.isSelected()) {
						steenReset();
						selectedSteen.setSelected(true);
						btnLegInPot.setVisible(true);
					} else {
						/** De steen wordt niet meer geselecteerd. */
						steenReset();
						selectedSteen = null;
						btnLegInPot.setVisible(false);
					}

				}

				);

			}
			}
		});

		/** Leg in pot Event */
		btnLegInPot.setOnMouseClicked(inPot ->

		{
			/**
			 * Indien er een steen geselecteerd is, wordt er een bevestiging gevraagd aan de
			 * speler. Indien de speler bevestigt, wordt de steen terug in de pot gestoken
			 * en uit de hand verwijderd. Bij annulatie wordt er gewoon verder gespeeld.
			 */
			if (selectedSteen != null) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(taalKiezer.getTaalBundle().getString("legInPot"));
				alert.setContentText(taalKiezer.getTaalBundle().getString("legInPotConfirm"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

					pot.setSteen(Integer.parseInt(selectedSteen.getText()));
					selectedSteen.setDisable(true);
					selectedSteen.setSelected(false);
					btnSteentjesInHand.remove(selectedSteen);
					btnNeemSteentjesTerug.setVisible(true);
					stenenInPot.add(selectedSteen);
					selectedSteen = null;

				} else { // cancel
					inPot.consume();
				}

				btnLegInPot.setVisible(false);
			}

		});

		/** Undo Event */
		btnNeemSteentjesTerug.setOnMouseClicked(evt -> {
			/** De waarden van de vakken, waar pas een steen werd gelegd, worden gereset. */
			for (ToggleButton vak : gelegdeSteentjes) {
				vak.setText("");
				bord.getVak(GridPane.getColumnIndex(vak), GridPane.getRowIndex(vak)).setHeeftSteen(0);

			}
			/**
			 * Elk steentje die de speler had in het begin van de beurt, wordt teruggegeven.
			 */
			btnSteentjesInHand.clear();
			for (Node steen : steentjesBox.getChildren()) {
				steen.setDisable(false);
				btnSteentjesInHand.add((ToggleButton) steen);

			}
			/** De stenen die in de pot werden gestoken, worden terug uit de pot gehaald. */
			if (!stenenInPot.isEmpty()) {
				for (ToggleButton btnSteen : btnSteentjesInHand) {
					pot.tweedeRemoveSteen(Integer.parseInt(btnSteen.getText()));
				}
			}
			gelegdeSteentjes.clear();
			stenenInPot.clear();
			btnNeemSteentjesTerug.setVisible(false);

		});

		/** Spel Stoppen Event */
		stop.setOnMouseClicked(evt -> {
			/**
			 * Indien de speler het spel wilt beëindigen, wordt er een bevestiging gevraagd.
			 * Als de speler bevestigt, worden de scorebladen van elke speler getoond. De
			 * winnaar wordt aangeduid. Als de speler annuleert, wordt het spel verder
			 * gespeeld.
			 */
			if(!(beurt < spelers.size()))
			{
				
			
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(taalKiezer.getTaalBundle().getString("stopSpel_Titel"));
				alert.setContentText(taalKiezer.getTaalBundle().getString("stopSpel_Bevestig"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					eindScene(terug,dc);
	
				} else { // cancel
					evt.consume();
				}
			}
			else 
			{
				Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
				alert2.setTitle(taalKiezer.getTaalBundle().getString("back_Titel"));
				alert2.setContentText(taalKiezer.getTaalBundle().getString("back_bevestig"));
				Optional<ButtonType> result2 = alert2.showAndWait();
				if (result2.get() == ButtonType.OK) {
					// System.out.println("We sluiten het venster en dus ook de applicatie")
	
					Stage stage1 = (Stage) this.getScene().getWindow();
					stage1.setScene(terug.getScene());

			} else { // cancel
				evt.consume();
			}
			}

		});

	}

	// functies

	/** Alle stenen worden niet meer geselecteerd. Dit reset de css waarden. */
	private void steenReset() {

		for (ToggleButton steen : btnSteentjesInHand) {
			steen.setSelected(false);
		}
	}

	/** De steentjes worden teruggegeven aan de speler. */
	private void validatieGefaald() {
		/**
		 * Als de steentjes allemaal zijn gebruikt en de validatie is gefaald, wordt er
		 * een andere gepast melding getoond.
		 */
		if (lblmessage.getText() != taalKiezer.getTaalBundle().getString("steentjesNietLeeg")) {

			lblmessage.setText(taalKiezer.getTaalBundle().getString("validatieGefaald"));
			lblmessage.setVisible(true);
		}
		if (btnSteentjesInHand.isEmpty()) {
			/** De waarden van de vakken, waar pas een steen werd gelegd, worden gereset. */
			for (ToggleButton vak : gelegdeSteentjes) {
				vak.setText("");
				bord.getVak(GridPane.getColumnIndex(vak), GridPane.getRowIndex(vak)).setHeeftSteen(0);

			}
			/**
			 * Elk steentje die de speler had in het begin van de beurt, wordt teruggegeven.
			 */
			for (Node steen : steentjesBox.getChildren()) {
				steen.setDisable(false);
				btnSteentjesInHand.add((ToggleButton) steen);

			}
			/** De stenen die in de pot werden gestoken, worden terug uit de pot gehaald. */
			if (!stenenInPot.isEmpty()) {
				for (ToggleButton btnSteen : btnSteentjesInHand) {
					pot.tweedeRemoveSteen(Integer.parseInt(btnSteen.getText()));
				}
			}
			gelegdeSteentjes.clear();
			stenenInPot.clear();
			btnNeemSteentjesTerug.setVisible(false);
			clearScoreblad();
		}
	}

	/**
	 * De score van de speler wordt berekend op basis van somHorizontaal en
	 * somVerticaal. Door middel van de booleans verticaleSomAanwezig en
	 * horizontaleSomAanwezig wordt er vermeden dat de score dubbel wordt geteld.
	 */
	private void berekenScore(int somHorizontaal, int somVerticaal) {

		switch (somHorizontaal) {
		case 10: {
			kolom10++;
			break;
		}
		case 11: {
			kolom11++;
			break;
		}
		case 12: {
			kolom12++;
			break;
		}
		}

		switch (somVerticaal) {
		case 10: {
			kolom10++;
			break;
		}
		case 11: {
			kolom11++;
			break;
		}
		case 12: {
			kolom12++;
			break;
		}
		}

		if (verticaleSomAanwezig) {
			switch (somVerticaal) {
			case 10: {
				kolom10--;
				break;
			}
			case 11: {
				kolom11--;
				break;
			}
			case 12: {
				kolom12--;
				break;
			}
			}

		}
		if (horizontaleSomAanwezig) {
			switch (somHorizontaal) {
			case 10: {
				kolom10--;
				break;
			}
			case 11: {
				kolom11--;
				break;
			}
			case 12: {
				kolom12--;
				break;
			}
			}

		}
	}

	/** Het scoreblad van de speler die aan beurt is wordt geüpdatet. */
	private void updateScoreblad() {
		if (beurt != 0)
			spelers.get(spelerAanBeurt).getScoreblad().UpdateScoreblad(verdubbelingPunten, kolom10, kolom11, kolom12,
					beurt, spelers.size());
	}

	/** Het scoreblad van de speler die aan beurt is wordt geprint. */
	private void scorebladToLabel() {
		scoreblad.setText(spelers.get(spelerAanBeurt).getScoreblad().tussenRondeToString());
		System.out.println(spelers.get(spelerAanBeurt).getNaam());
		System.out.println(spelers.get(spelerAanBeurt).getScoreblad().toString());
	}

	/** De waarden van de kolommen van het scoreblad worden per beurt gereset. */
	private void clearScoreblad() {
		verdubbelingPunten = false;
		kolom10 = 0;
		kolom11 = 0;
		kolom12 = 0;
	}

	/** De horizontale som van het gelegd steentje wordt berekend. */
	private int somHorizontaal(ToggleButton gelegdSteentje, int somHorizontaal) {
		/**
		 * In beurt 1: als het gelegde steentje grenst aan een ander steentje langs de
		 * linker- of rechterkant, wordt de waarde van het grenzend steentje bij het
		 * gelegd steentje geteld. De bijbehorende boolean wordt dan ook ingesteld op
		 * 'true'. Dit wordt dan nagetrokken bij de validatie.
		 */
		if (beurt == 1) {
			ToggleButton leftVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) - 1][GridPane
					.getRowIndex(gelegdSteentje)];
			ToggleButton rightVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) + 1][GridPane
					.getRowIndex(gelegdSteentje)];
			if (gelegdeSteentjes.contains(leftVak)) {
				isLeftOccupied = true;
				if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
					horizontaleSomAanwezig = true;
				}

				while (isLeftOccupied) {
					if (gelegdeSteentjes.contains(leftVak)) {
						somHorizontaal += Integer.parseInt(leftVak.getText());
						leftVak = spelbord[GridPane.getColumnIndex(leftVak) - 1][GridPane.getRowIndex(leftVak)];
						isLeftOccupied = true;

					} else {
						isLeftOccupied = false;
					}
				}
				isLeftOccupied = true;
			}
			if (gelegdeSteentjes.contains(rightVak)) {
				isRightOccupied = true;
				if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
					horizontaleSomAanwezig = true;
				}
				while (isRightOccupied) {
					if (gelegdeSteentjes.contains(rightVak)) {
						somHorizontaal += Integer.parseInt(rightVak.getText());
						rightVak = spelbord[GridPane.getColumnIndex(rightVak) + 1][GridPane.getRowIndex(rightVak)];
						isRightOccupied = true;

					} else {
						isRightOccupied = false;
					}
				}
				isRightOccupied = true;
			}

		} else {
			/**
			 * Bij elke andere beurt wordt er gecontrolleerd of het gelegde steentje grenst
			 * aan een steentje die al op bord lag voor de speler aan beurt was. Als dit het
			 * geval is, wordt de waarde van de grenzende steen bij de som opgeteld en de
			 * bijbehorende boolean op 'true' gezet. Daarna wordt er in dezelfde richting
			 * gecontrolleerd of er aan het grenzende steentje een ander steentje geld.
			 * Indien dit klopt wordt die waarde dan weer bij de som opgeteld. Dit blijft
			 * doorgaan tot er geen grenzende steen meer gevonden wordt in dezelfde richting
			 */
			if (GridPane.getColumnIndex(gelegdSteentje) - 1 >= 0) {
				ToggleButton leftVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) - 1][GridPane
						.getRowIndex(gelegdSteentje)];
				if (steentjesOpBord.contains(leftVak)) {
					isLeftOccupied = true;
					while (isLeftOccupied) {
						if (steentjesOpBord.contains(leftVak) || gelegdeSteentjes.contains(leftVak)) {
							somHorizontaal += Integer.parseInt(leftVak.getText());
							if (gelegdeSteentjes.contains(leftVak) && (gelegdeSteentjes.indexOf(gelegdSteentje) != 0)) {
								horizontaleSomAanwezig = true;
							}
							if (GridPane.getColumnIndex(leftVak) - 1 >= 0) {
								leftVak = spelbord[GridPane.getColumnIndex(leftVak) - 1][GridPane.getRowIndex(leftVak)];
								isLeftOccupied = true;
							}

						} else {
							isLeftOccupied = false;
						}
					}
					isLeftOccupied = true;
				}
			}

			if (GridPane.getColumnIndex(gelegdSteentje) + 1 < 15) {
				ToggleButton rightVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) + 1][GridPane
						.getRowIndex(gelegdSteentje)];
				if (steentjesOpBord.contains(rightVak)) {
					isRightOccupied = true;
					while (isRightOccupied) {
						if (steentjesOpBord.contains(rightVak) || gelegdeSteentjes.contains(rightVak)) {
							somHorizontaal += Integer.parseInt(rightVak.getText());
							if (gelegdeSteentjes.contains(rightVak)
									&& (gelegdeSteentjes.indexOf(gelegdSteentje) != 0)) {
								horizontaleSomAanwezig = true;
							}
							if (GridPane.getColumnIndex(rightVak) + 1 < 15) {
								rightVak = spelbord[GridPane.getColumnIndex(rightVak) + 1][GridPane
										.getRowIndex(rightVak)];
								isRightOccupied = true;
							}

						} else {
							isRightOccupied = false;
						}
					}
					isRightOccupied = true;
				}
			}
			if (GridPane.getRowIndex(gelegdSteentje) - 1 >= 0 && (isLeftOccupied || isRightOccupied)) {
				ToggleButton topVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
						.getRowIndex(gelegdSteentje) - 1];
				if (gelegdeSteentjes.contains(topVak)) {
					somVerticaal += Integer.parseInt(topVak.getText());
					if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
						verticaleSomAanwezig = true;
					}
					boolean isTop = true;
					if (GridPane.getRowIndex(topVak) - 1 >= 0) {
						topVak = spelbord[GridPane.getColumnIndex(topVak)][GridPane.getRowIndex(topVak) - 1];
					}
					while (isTop) {
						if (steentjesOpBord.contains(topVak)) {
							somVerticaal += Integer.parseInt(topVak.getText());
							if (GridPane.getRowIndex(topVak) - 1 >= 0) {
								topVak = spelbord[GridPane.getColumnIndex(topVak)][GridPane.getRowIndex(topVak) - 1];
								isTop = true;
							}
						} else {
							isTop = false;

						}

					}
				}
				if (GridPane.getRowIndex(gelegdSteentje) + 1 < 15 && (isLeftOccupied || isRightOccupied)) {
					ToggleButton bottomVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
							.getRowIndex(gelegdSteentje) + 1];
					if (gelegdeSteentjes.contains(bottomVak)) {
						somVerticaal += Integer.parseInt(bottomVak.getText());
						if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
							verticaleSomAanwezig = true;
						}
						boolean isBottom = true;
						if (GridPane.getRowIndex(bottomVak) + 1 < 15) {
							bottomVak = spelbord[GridPane.getColumnIndex(bottomVak)][GridPane.getRowIndex(bottomVak)
									+ 1];
						}
						while (isBottom) {
							if (steentjesOpBord.contains(bottomVak)) {
								somVerticaal += Integer.parseInt(bottomVak.getText());
								if (GridPane.getRowIndex(bottomVak) + 1 < 15) {
									bottomVak = spelbord[GridPane.getColumnIndex(bottomVak)][GridPane
											.getRowIndex(bottomVak) + 1];
									isBottom = true;
								}
							} else {
								isBottom = false;
							}
						}
					}

				}
				if (GridPane.getColumnIndex(gelegdSteentje) + 1 < 15 && isLeftOccupied) {
					ToggleButton rightVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) + 1][GridPane
							.getRowIndex(gelegdSteentje)];
					if (gelegdeSteentjes.contains(rightVak)) {
						somHorizontaal += Integer.parseInt(rightVak.getText());
						if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
							horizontaleSomAanwezig = true;
						}
						boolean isRight = true;
						if (GridPane.getColumnIndex(rightVak) + 1 < 15) {
							rightVak = spelbord[GridPane.getColumnIndex(rightVak) + 1][GridPane.getRowIndex(rightVak)];
						}
						while (isRight) {
							if (steentjesOpBord.contains(rightVak)) {
								somHorizontaal += Integer.parseInt(rightVak.getText());
								if (GridPane.getColumnIndex(rightVak) + 1 < 15) {
									rightVak = spelbord[GridPane.getColumnIndex(rightVak) + 1][GridPane
											.getRowIndex(rightVak)];
									isRight = true;
								}

							} else {
								isRight = false;
							}
						}

					}
				}
				if (GridPane.getColumnIndex(gelegdSteentje) - 1 >= 0 && isRightOccupied) {
					ToggleButton leftVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) - 1][GridPane
							.getRowIndex(gelegdSteentje)];
					if (gelegdeSteentjes.contains(leftVak)) {
						somHorizontaal += Integer.parseInt(leftVak.getText());
						if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
							horizontaleSomAanwezig = true;
						}
						boolean isLeft = true;
						if (GridPane.getColumnIndex(leftVak) - 1 >= 0) {
							leftVak = spelbord[GridPane.getColumnIndex(leftVak) - 1][GridPane.getRowIndex(leftVak)];
						}
						while (isLeft) {
							if (steentjesOpBord.contains(leftVak)) {
								somHorizontaal += Integer.parseInt(leftVak.getText());
								if (GridPane.getColumnIndex(leftVak) - 1 >= 0) {
									leftVak = spelbord[GridPane.getColumnIndex(leftVak) - 1][GridPane
											.getRowIndex(leftVak)];
									isLeft = true;
								}

							} else {
								isLeft = false;
							}
						}

					}
				}
			}
		}
		return somHorizontaal;
	}

	private int somVerticaal(ToggleButton gelegdSteentje, int somVerticaal) {
		if (beurt == 1) {
			/**
			 * In beurt 1: als het gelegde steentje grenst aan een ander steentje langs de
			 * boven- of onderkant, wordt de waarde van het grenzend steentje bij het gelegd
			 * steentje geteld. De bijbehorende boolean wordt dan ook ingesteld op 'true'.
			 * Dit wordt dan nagetrokken bij de validatie.
			 */
			ToggleButton topVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane.getRowIndex(gelegdSteentje)
					- 1];
			ToggleButton bottomVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
					.getRowIndex(gelegdSteentje) + 1];

			if (gelegdeSteentjes.contains(topVak)) {
				isTopOccupied = true;
				if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
					verticaleSomAanwezig = true;
				}

				while (isTopOccupied) {
					if (gelegdeSteentjes.contains(topVak)) {
						somVerticaal += Integer.parseInt(topVak.getText());
						topVak = spelbord[GridPane.getColumnIndex(topVak)][GridPane.getRowIndex(topVak) - 1];
						isTopOccupied = true;

					} else {
						isTopOccupied = false;
					}
				}
				isTopOccupied = true;
			}
			if (gelegdeSteentjes.contains(bottomVak)) {
				isBottomOccupied = true;
				if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
					verticaleSomAanwezig = true;
				}
				while (isBottomOccupied) {
					if (gelegdeSteentjes.contains(bottomVak)) {
						somVerticaal += Integer.parseInt(bottomVak.getText());
						bottomVak = spelbord[GridPane.getColumnIndex(bottomVak)][GridPane.getRowIndex(bottomVak) + 1];
						isBottomOccupied = true;

					} else {
						isBottomOccupied = false;
					}
				}
				isBottomOccupied = true;
			}

		} else {
			/**
			 * Bij elke andere beurt wordt er gecontrolleerd of het gelegde steentje grenst
			 * aan een steentje die al op bord lag voor de speler aan beurt was. Als dit het
			 * geval is, wordt de waarde van de grenzende steen bij de som opgeteld en de
			 * bijbehorende boolean op 'true' gezet. Daarna wordt er in dezelfde richting
			 * gecontrolleerd of er aan het grenzende steentje een ander steentje geld.
			 * Indien dit klopt wordt die waarde dan weer bij de som opgeteld. Dit blijft
			 * doorgaan tot er geen grenzende steen meer gevonden wordt in dezelfde richting
			 */
			if (GridPane.getRowIndex(gelegdSteentje) + 1 < 15) {
				ToggleButton bottomVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
						.getRowIndex(gelegdSteentje) + 1];
				if (steentjesOpBord.contains(bottomVak)) {
					isBottomOccupied = true;
					while (isBottomOccupied) {
						if (steentjesOpBord.contains(bottomVak) || gelegdeSteentjes.contains(bottomVak)) {
							if (gelegdeSteentjes.contains(bottomVak)
									&& (gelegdeSteentjes.indexOf(gelegdSteentje) != 0)) {
								verticaleSomAanwezig = true;
							}
							somVerticaal += Integer.parseInt(bottomVak.getText());
							if (GridPane.getRowIndex(bottomVak) + 1 < 15) {
								bottomVak = spelbord[GridPane.getColumnIndex(bottomVak)][GridPane.getRowIndex(bottomVak)
										+ 1];
								isBottomOccupied = true;
							}

						} else {
							isBottomOccupied = false;
						}
					}

					isBottomOccupied = true;
				}
			}

			if (GridPane.getRowIndex(gelegdSteentje) - 1 >= 0) {
				ToggleButton topVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
						.getRowIndex(gelegdSteentje) - 1];
				if (steentjesOpBord.contains(topVak)) {
					isTopOccupied = true;
					while (isTopOccupied) {
						if (steentjesOpBord.contains(topVak) || gelegdeSteentjes.contains(topVak)) {
							if (gelegdeSteentjes.contains(topVak) && (gelegdeSteentjes.indexOf(gelegdSteentje) != 0)) {
								verticaleSomAanwezig = true;
							}
							somVerticaal += Integer.parseInt(topVak.getText());
							if (GridPane.getRowIndex(topVak) - 1 >= 0) {
								topVak = spelbord[GridPane.getColumnIndex(topVak)][GridPane.getRowIndex(topVak) - 1];
								isTopOccupied = true;
							}

						} else {
							isTopOccupied = false;
						}
					}

					isTopOccupied = true;
				}
			}
			if (GridPane.getColumnIndex(gelegdSteentje) - 1 >= 0 && (isTopOccupied || isBottomOccupied)) {
				ToggleButton leftVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) - 1][GridPane
						.getRowIndex(gelegdSteentje)];

				if (gelegdeSteentjes.contains(leftVak)) {
					somHorizontaal += Integer.parseInt(leftVak.getText());
					if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
						horizontaleSomAanwezig = true;
					}
					boolean isLeft = true;
					if (GridPane.getColumnIndex(leftVak) - 1 >= 0) {
						leftVak = spelbord[GridPane.getColumnIndex(leftVak) - 1][GridPane.getRowIndex(leftVak)];
					}
					while (isLeft) {
						if (steentjesOpBord.contains(leftVak)) {
							somHorizontaal += Integer.parseInt(leftVak.getText());
							if (GridPane.getColumnIndex(leftVak) - 1 >= 0) {
								leftVak = spelbord[GridPane.getColumnIndex(leftVak) - 1][GridPane.getRowIndex(leftVak)];
								isLeft = true;
							}
						} else {
							isLeft = false;
						}
					}

				}
			}
			if (GridPane.getColumnIndex(gelegdSteentje) + 1 < 15 && (isTopOccupied || isBottomOccupied)) {
				ToggleButton rightVak = spelbord[GridPane.getColumnIndex(gelegdSteentje) + 1][GridPane
						.getRowIndex(gelegdSteentje)];
				if (gelegdeSteentjes.contains(rightVak)) {
					somHorizontaal += Integer.parseInt(rightVak.getText());
					if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
						horizontaleSomAanwezig = true;
					}
					boolean isRight = true;
					if (GridPane.getColumnIndex(rightVak) + 1 < 15) {
						rightVak = spelbord[GridPane.getColumnIndex(rightVak) + 1][GridPane.getRowIndex(rightVak)];
					}
					while (isRight) {
						if (steentjesOpBord.contains(rightVak)) {
							somHorizontaal += Integer.parseInt(rightVak.getText());
							if (GridPane.getColumnIndex(rightVak) + 1 < 15) {
								rightVak = spelbord[GridPane.getColumnIndex(rightVak) + 1][GridPane
										.getRowIndex(rightVak)];
								isRight = true;
							}
						} else {
							isRight = false;
						}
					}

				}
			}

			if (GridPane.getRowIndex(gelegdSteentje) + 1 < 15 && isTopOccupied) {
				ToggleButton bottomVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
						.getRowIndex(gelegdSteentje) + 1];
				if (gelegdeSteentjes.contains(bottomVak)) {
					somVerticaal += Integer.parseInt(bottomVak.getText());
					if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
						verticaleSomAanwezig = true;
					}
					boolean isBottom = true;
					if (GridPane.getRowIndex(bottomVak) + 1 < 15) {
						bottomVak = spelbord[GridPane.getColumnIndex(bottomVak)][GridPane.getRowIndex(bottomVak) + 1];
					}
					while (isBottom) {
						if (steentjesOpBord.contains(bottomVak)) {
							somVerticaal += Integer.parseInt(bottomVak.getText());
							if (GridPane.getRowIndex(bottomVak) + 1 < 15) {
								bottomVak = spelbord[GridPane.getColumnIndex(bottomVak)][GridPane.getRowIndex(bottomVak)
										+ 1];
								isBottom = true;
							}

						} else {
							isBottom = false;
						}
					}

				}
			}

			if (GridPane.getRowIndex(gelegdSteentje) - 1 >= 0 && isBottomOccupied) {
				ToggleButton topVak = spelbord[GridPane.getColumnIndex(gelegdSteentje)][GridPane
						.getRowIndex(gelegdSteentje) - 1];
				if (gelegdeSteentjes.contains(topVak)) {
					somVerticaal += Integer.parseInt(topVak.getText());
					if (gelegdeSteentjes.indexOf(gelegdSteentje) != 0) {
						verticaleSomAanwezig = true;
					}
					boolean isTop = true;
					if (GridPane.getRowIndex(topVak) - 1 >= 0) {
						topVak = spelbord[GridPane.getColumnIndex(topVak)][GridPane.getRowIndex(topVak) - 1];
					}
					while (isTop) {
						if (steentjesOpBord.contains(topVak)) {
							somVerticaal += Integer.parseInt(topVak.getText());
							if (GridPane.getRowIndex(topVak) - 1 >= 0) {
								topVak = spelbord[GridPane.getColumnIndex(topVak)][GridPane.getRowIndex(topVak) - 1];
								isTop = true;
							}

						} else {
							isTop = false;
						}
					}
				}
			}
		}

		return somVerticaal;
	}

	/**
	 * Op het einde van het spel worden alle scorebladen getoond en de winnaar
	 * aangeduid.
	 */
	private void eindScene(StartScherm terug, DomeinController dc) {
		/**
		 * Als alle spelers hetzelfde aantal beurten hebben gespeeld kan het spel
		 * beëindigt worden. Anders wordt er een gepaste melding getoond.
		 */
			
			Speler winnaar = spel.getSpelerMetHoogsteScore();
			winnaar.pasSpeelKansenAan(2);
			List<Label> scorebladen = new ArrayList<>();
			Button quit = new Button(taalKiezer.getTaalBundle().getString("back_Titel"));

			quit.setPrefSize(150, 20);

			bordPane.getChildren().clear();
			scorebladPane.getChildren().clear();
			menuBox.getChildren().clear();

			menuBox.getChildren().add(quit);
			menuBox.setAlignment(Pos.CENTER);

			/**
			 * De scorebladen van elke speler worden naast elkaar afgedrukt.
			 */
			for (int i = 0; i < spelers.size(); i++) {
				int j = 0;
				scorebladen.add(i, new Label(spelers.get(i).getScoreblad().toString()));
				scorebladen.get(i).setFont(new Font("Courier New", 11));
				bordPane.add(scorebladen.get(i), 1 + i + j, 1);
				bordPane.add(new Label(String.format("%s  , aantal Speelkansen: %d",spelers.get(i).getNaam() , spelers.get(i).getAantalSpeelKansen())) ,1+ i+ j,0);
				j += 2;
			}
			
			/** De winnaar van het spel wordt aangeduid. */
			scorebladen.get(spelers.indexOf(winnaar))
					.setStyle("-fx-border-color: Red; -fx-border-width: 10; -fx-border-radius: 20");

			lblmessage.setText(String.format(taalKiezer.getTaalBundle().getString("spelerGewonnen"),
					spelers.get(spelers.indexOf(winnaar)).getNaam()));
			lblmessage.setVisible(true);
			BorderPane.setAlignment(lblmessage, Pos.CENTER);
			for (Speler speler : dc.getGeselecteerdeSpelers()) {
				dc.pasSpeelkansenAan(speler);
			}
			/** Back To Main Menu */
			quit.setOnAction(evt -> {
				/** Als de speler bevestigt wordt er teruggegaan naar het StartScherm. */
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(taalKiezer.getTaalBundle().getString("back_Titel"));
				alert.setContentText(taalKiezer.getTaalBundle().getString("back_bevestig"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					// System.out.println("We sluiten het venster en dus ook de applicatie")

					Stage stage1 = (Stage) this.getScene().getWindow();
					stage1.setScene(terug.getScene());

				} else { // cancel
					evt.consume();
				}

			});
		

	}
}