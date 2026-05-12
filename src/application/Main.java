
package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	private manb dictionary = new manb();
	private TextArea output;
	private TextField tfWord, tfEnglish, tfArabic, tfType;
	private TextArea taSentence;

	@Override
	public void start(Stage stage) {
		TabPane tabPane = new TabPane();

		// Tab 1: Dictionary Management
		Tab dictionaryTab = createDictionaryTab();

		// Tab 2: Translation
		Tab translationTab = createTranslationTab();

		// Tab 3: Sentence Generation
		Tab generationTab = createGenerationTab();

		// Tab 4: Statistics
		Tab statsTab = createStatisticsTab();

		tabPane.getTabs().addAll(dictionaryTab, translationTab, generationTab, statsTab);

		Scene scene = new Scene(tabPane, 1000, 650);
		stage.setScene(scene);
		stage.setTitle("Dictionary Management System");
		stage.show();
	}

	private Tab createDictionaryTab() {
		Tab tab = new Tab("Dictionary Management");
		tab.setClosable(false);

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15));

		// Top: Title
		Label title = new Label("Dictionary Management System");
		title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
		HBox topBox = new HBox(title);
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(0, 0, 20, 0));
		root.setTop(topBox);

		// Left: Input fields
		VBox left = new VBox(10);
		left.setPrefWidth(300);
		left.setPadding(new Insets(10));
		left.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-radius: 5;");

		Label lblInput = new Label("Word Information:");
		lblInput.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

		tfWord = new TextField();
		tfWord.setPromptText("Word (English)");

		tfEnglish = new TextField();
		tfEnglish.setPromptText("English Meaning");

		tfArabic = new TextField();
		tfArabic.setPromptText("Arabic Meaning");

		tfType = new TextField();
		tfType.setPromptText("Type (Noun, Verb, Adjective)");

		taSentence = new TextArea();
		taSentence.setPromptText("Example Sentence");
		taSentence.setPrefRowCount(3);

		left.getChildren().addAll(lblInput, tfWord, tfEnglish, tfArabic, tfType, taSentence);
		root.setLeft(left);

		// Center: Output area
		output = new TextArea();
		output.setEditable(false);
		output.setPrefRowCount(20);
		output.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
		root.setCenter(output);

		// Right: Buttons
		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10));
		buttons.setAlignment(Pos.TOP_CENTER);

		Button btnAdd = createStyledButton("Add Word", "#28a745");
		Button btnUpdate = createStyledButton("Update Word", "#17a2b8");
		Button btnDelete = createStyledButton("Delete Word", "#dc3545");
		Button btnSearchEng = createStyledButton("Search (English)", "#007bff");
		Button btnSearchAr = createStyledButton("Search (Arabic)", "#6f42c1");
		Button btnLoad = createStyledButton("Load File", "#fd7e14");
		Button btnSave = createStyledButton("Save File", "#20c997");
		Button btnClear = createStyledButton("Clear Fields", "#6c757d");

		buttons.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnSearchEng, btnSearchAr, btnLoad, btnSave,
				btnClear);
		root.setRight(buttons);

		// Button actions
		btnAdd.setOnAction(e -> addWord());
		btnUpdate.setOnAction(e -> updateWord());
		btnDelete.setOnAction(e -> deleteWord());
		btnSearchEng.setOnAction(e -> searchEnglish());
		btnSearchAr.setOnAction(e -> searchArabic());
		btnLoad.setOnAction(e -> loadFile());
		btnSave.setOnAction(e -> saveFile());
		btnClear.setOnAction(e -> clearFields());

		tab.setContent(root);
		return tab;
	}

	private Tab createTranslationTab() {
		Tab tab = new Tab("Translation");
		tab.setClosable(false);

		VBox root = new VBox(15);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.TOP_CENTER);

		Label title = new Label("Text Translation");
		title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

		// Translation options
		HBox optionsBox = new HBox(20);
		optionsBox.setAlignment(Pos.CENTER);

		Label lblDirection = new Label("Translate from:");
		ToggleGroup group = new ToggleGroup();
		RadioButton rbEngToAr = new RadioButton("English → Arabic");
		RadioButton rbArToEng = new RadioButton("Arabic → English");
		rbEngToAr.setToggleGroup(group);
		rbArToEng.setToggleGroup(group);
		rbEngToAr.setSelected(true);

		optionsBox.getChildren().addAll(lblDirection, rbEngToAr, rbArToEng);

		// Input area
		Label lblInput = new Label("Enter text to translate:");
		TextArea taInput = new TextArea();
		taInput.setPromptText("Enter text here...");
		taInput.setPrefRowCount(5);
		taInput.setPrefWidth(800);

		// Translation buttons
		HBox transButtons = new HBox(15);
		transButtons.setAlignment(Pos.CENTER);

		Button btnTranslate = createStyledButton("Translate", "#007bff");
		Button btnLoadText = createStyledButton("Load from File", "#fd7e14");
		Button btnSaveTrans = createStyledButton("Save Translation", "#20c997");
		Button btnClearTrans = createStyledButton("Clear", "#6c757d");

		transButtons.getChildren().addAll(btnTranslate, btnLoadText, btnSaveTrans, btnClearTrans);

		// Output area
		Label lblOutput = new Label("Translation Result:");
		TextArea taOutput = new TextArea();
		taOutput.setEditable(false);
		taOutput.setPrefRowCount(5);
		taOutput.setPrefWidth(800);
		taOutput.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

		btnTranslate.setOnAction(e -> {
			String text = taInput.getText().trim();
			if (text.isEmpty()) {
				taOutput.setText("Please enter text to translate.");
				return;
			}

			if (rbEngToAr.isSelected()) {
				taOutput.setText(dictionary.EnglishToArabic(text));
			} else {
				taOutput.setText(dictionary.ArabicToEnglish(text));
			}
		});

		btnLoadText.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			java.io.File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				try (java.util.Scanner sc = new java.util.Scanner(file, "UTF-8")) {
					StringBuilder content = new StringBuilder();
					while (sc.hasNextLine()) {
						content.append(sc.nextLine()).append("\n");
					}
					taInput.setText(content.toString());
				} catch (Exception ex) {
					taOutput.setText("Error loading file: " + ex.getMessage());
				}
			}
		});

		btnSaveTrans.setOnAction(e -> {
			String translation = taOutput.getText();
			if (translation.isEmpty()) {
				taOutput.setText("No translation to save.");
				return;
			}

			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName("translation.txt");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			java.io.File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				try (java.io.PrintWriter pw = new java.io.PrintWriter(file, "UTF-8")) {
					pw.println(translation);
					taOutput.setText("Translation saved to: " + file.getName());
				} catch (Exception ex) {
					taOutput.setText("Error saving file: " + ex.getMessage());
				}
			}
		});

		btnClearTrans.setOnAction(e -> {
			taInput.clear();
			taOutput.clear();
		});

		root.getChildren().addAll(title, optionsBox, lblInput, taInput, transButtons, lblOutput, taOutput);
		tab.setContent(root);
		return tab;
	}

	private Tab createGenerationTab() {
		Tab tab = new Tab("Sentence Generation");
		tab.setClosable(false);

		VBox root = new VBox(15);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.TOP_CENTER);

		Label title = new Label("Random Sentence Generator");
		title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

		// Generation options
		HBox optionsBox = new HBox(20);
		optionsBox.setAlignment(Pos.CENTER);

		Label lblLang = new Label("Generate in:");
		ToggleGroup group = new ToggleGroup();
		RadioButton rbEnglish = new RadioButton("English");
		RadioButton rbArabic = new RadioButton("Arabic");
		rbEnglish.setToggleGroup(group);
		rbArabic.setToggleGroup(group);
		rbEnglish.setSelected(true);

		optionsBox.getChildren().addAll(lblLang, rbEnglish, rbArabic);

		// Number of sentences
		HBox countBox = new HBox(15);
		countBox.setAlignment(Pos.CENTER);

		Label lblCount = new Label("Number of sentences:");
		TextField tfCount = new TextField("5");
		tfCount.setPrefWidth(60);

		countBox.getChildren().addAll(lblCount, tfCount);

		// Buttons
		HBox genButtons = new HBox(15);
		genButtons.setAlignment(Pos.CENTER);

		Button btnGenerate = createStyledButton("Generate Sentences", "#28a745");
		Button btnSaveGen = createStyledButton("Save Sentences", "#20c997");
		Button btnClearGen = createStyledButton("Clear", "#6c757d");

		genButtons.getChildren().addAll(btnGenerate, btnSaveGen, btnClearGen);

		// Output area
		Label lblOutput = new Label("Generated Sentences:");
		TextArea taOutput = new TextArea();
		taOutput.setEditable(false);
		taOutput.setPrefRowCount(10);
		taOutput.setPrefWidth(800);
		taOutput.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

		btnGenerate.setOnAction(e -> {
			try {
				int count = Integer.parseInt(tfCount.getText().trim());
				if (count <= 0) {
					taOutput.setText("Please enter a positive number.");
					return;
				}

				String language = rbEnglish.isSelected() ? "English" : "Arabic";
				String sentences = dictionary.Randomsentences(count, language);
				taOutput.setText(sentences);
			} catch (NumberFormatException ex) {
				taOutput.setText("Invalid number. Please enter a valid integer.");
			}
		});

		btnSaveGen.setOnAction(e -> {
			String sentences = taOutput.getText();
			if (sentences.isEmpty()) {
				taOutput.setText("No sentences to save.");
				return;
			}

			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName("sentences.txt");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			java.io.File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				try (java.io.PrintWriter pw = new java.io.PrintWriter(file, "UTF-8")) {
					pw.println(sentences);
					taOutput.setText("Sentences saved to: " + file.getName());
				} catch (Exception ex) {
					taOutput.setText("Error saving file: " + ex.getMessage());
				}
			}
		});

		btnClearGen.setOnAction(e -> {
			taOutput.clear();
		});

		root.getChildren().addAll(title, optionsBox, countBox, genButtons, lblOutput, taOutput);
		tab.setContent(root);
		return tab;
	}

	private Tab createStatisticsTab() {
		Tab tab = new Tab("Statistics & Reports");
		tab.setClosable(false);

		VBox root = new VBox(15);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.TOP_CENTER);

		Label title = new Label("Dictionary Statistics");
		title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

		// Buttons for different reports
		VBox reportButtons = new VBox(10);
		reportButtons.setAlignment(Pos.CENTER);
		reportButtons.setPadding(new Insets(20));

		Button btnWordsPerLetter = createStyledButton("Words Per Letter", "#007bff");
		Button btnWordTypes = createStyledButton("Word Types Count", "#28a745");
		Button btnTreeHeights = createStyledButton("AVL Tree Heights", "#6f42c1");
		Button btnPrintAll = createStyledButton("Print All Words", "#fd7e14");
		Button btnPrintLetter = createStyledButton("Print Words by Letter", "#20c997");

		reportButtons.getChildren().addAll(btnWordsPerLetter, btnWordTypes, btnTreeHeights, btnPrintAll,
				btnPrintLetter);

		// Output area
		TextArea taStats = new TextArea();
		taStats.setEditable(false);
		taStats.setPrefRowCount(15);
		taStats.setPrefWidth(800);
		taStats.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

		// Text field for letter input
		HBox letterBox = new HBox(10);
		letterBox.setAlignment(Pos.CENTER);
		Label lblLetter = new Label("Enter letter:");
		TextField tfLetter = new TextField();
		tfLetter.setPromptText("A");
		tfLetter.setPrefWidth(50);
		letterBox.getChildren().addAll(lblLetter, tfLetter);
		letterBox.setVisible(false);

		btnPrintLetter.setOnAction(e -> {
			letterBox.setVisible(true);
			tfLetter.requestFocus();
		});

		tfLetter.setOnAction(e -> {
			String letterStr = tfLetter.getText().trim().toUpperCase();
			if (letterStr.length() > 0) {
				char letter = letterStr.charAt(0);
				taStats.setText(dictionary.printWordsForoneLetter(letter));
				letterBox.setVisible(false);
				tfLetter.clear();
			}
		});

		// Button actions
		btnWordsPerLetter.setOnAction(e -> taStats.setText(dictionary.countWordsbytheLetter()));
		btnWordTypes.setOnAction(e -> taStats.setText(dictionary.countWordbyTypes()));
		btnTreeHeights.setOnAction(e -> taStats.setText(dictionary.getTreeHeights()));
		btnPrintAll.setOnAction(e -> taStats.setText(dictionary.printAllWords()));

		root.getChildren().addAll(title, reportButtons, letterBox, taStats);
		tab.setContent(root);
		return tab;
	}

	private Button createStyledButton(String text, String color) {
		Button btn = new Button(text);
		btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; "
				+ "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 5;");
		btn.setPrefWidth(200);
		return btn;
	}

	// Helper methods for dictionary operations
	private void addWord() {
		String word = tfWord.getText().trim();
		String english = tfEnglish.getText().trim();
		String arabic = tfArabic.getText().trim();
		String sentence = taSentence.getText().trim();
		String type = tfType.getText().trim();

		// Input validation
		if (word.isEmpty() || english.isEmpty() || arabic.isEmpty() || sentence.isEmpty() || type.isEmpty()) {
			output.setText("Error: All fields are required!");
			return;
		}

		if (!word.matches("[a-zA-Z]+")) {
			output.setText("Error: Word must contain only English letters!");
			return;
		}

		NewWord newWord = new NewWord(word, english, arabic, sentence, type);
		boolean success = dictionary.addword(newWord);

		if (success) {
			output.setText(" Word '" + word + "' added successfully!");
			clearFields();
		} else {
			output.setText(" Error: Word '" + word + "' already exists!");
		}
	}

	private void updateWord() {
		String word = tfWord.getText().trim();
		if (word.isEmpty()) {
			output.setText("Error: Please enter a word to update!");
			return;
		}

		String english = tfEnglish.getText().trim();
		String arabic = tfArabic.getText().trim();
		String sentence = taSentence.getText().trim();
		String type = tfType.getText().trim();

		NewWord updatedWord = new NewWord(word, english, arabic, sentence, type);
		boolean success = dictionary.updateData(word, updatedWord);

		if (success) {
			output.setText(" Word '" + word + "' updated successfully!");
			clearFields();
		} else {
			output.setText("Error: Word '" + word + "' not found!");
		}
	}

	private void deleteWord() {
		String word = tfWord.getText().trim();
		if (word.isEmpty()) {
			output.setText("Error: Please enter a word to delete!");
			return;
		}

		// Confirmation dialog
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setHeaderText("Delete Word");
		alert.setContentText("Are you sure you want to delete '" + word + "'?");

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				boolean success = dictionary.deleteWord(word);
				if (success) {
					output.setText(" Word '" + word + "' deleted successfully!");
					clearFields();
				} else {
					output.setText(" Error: Word '" + word + "' not found!");
				}
			}
		});
	}

	private void searchEnglish() {
		String word = tfWord.getText().trim();
		if (word.isEmpty()) {
			output.setText("Error: Please enter a word to search!");
			return;
		}

		NewWord result = dictionary.searchInEnglish(word);
		if (result != null) {
			output.setText(" Word found:\n" + "Word: " + result.getWord() + "\n" + "English Meaning: "
					+ result.getInenglish() + "\n" + "Arabic Meaning: " + result.getInArabic() + "\n" + "Example: "
					+ result.getSentence() + "\n" + "Type: " + result.getType());
			// Fill fields for possible update
			tfEnglish.setText(result.getInenglish());
			tfArabic.setText(result.getInArabic());
			taSentence.setText(result.getSentence());
			tfType.setText(result.getType());
		} else {
			output.setText("❌ Word not found!");
		}
	}

	private void searchArabic() {
		String arabicWord = tfArabic.getText().trim();
		if (arabicWord.isEmpty()) {
			output.setText("Error: Please enter Arabic meaning to search!");
			return;
		}

		NewWord result = dictionary.searchArabic(arabicWord);
		if (result != null) {
			output.setText(" Word found:\n" + "Word: " + result.getWord() + "\n" + "English Meaning: "
					+ result.getInenglish() + "\n" + "Arabic Meaning: " + result.getInArabic() + "\n" + "Example: "
					+ result.getSentence() + "\n" + "Type: " + result.getType());
			// Fill fields
			tfWord.setText(result.getWord());
			tfEnglish.setText(result.getInenglish());
			taSentence.setText(result.getSentence());
			tfType.setText(result.getType());
		} else {
			output.setText(" Word not found!");
		}
	}

	private void loadFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		java.io.File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			dictionary.loadFromFile(file);
			output.setText(" File loaded successfully: " + file.getName() + "\nTotal words in dictionary: "
					+ dictionary.getTotalWordCount());
		}
	}

	private void saveFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("dictionary.txt");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		java.io.File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			dictionary.saveToFile(file);
			output.setText(" Dictionary saved to: " + file.getName());
		}
	}

	private void clearFields() {
		tfWord.clear();
		tfEnglish.clear();
		tfArabic.clear();
		tfType.clear();
		taSentence.clear();
	}

	public static void main(String[] args) {
		launch(args);
	
		  
		
	}
}