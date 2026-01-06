package ui;

import java.time.LocalTime;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import logic.MedicineLogic;
import model.Medicine;

/*
Main Controller for the Medicine Manager application
Handles interactions between the UI and the logic layer
UI events will be processed here and appropriate logic methods will be called
*/
public class MainController 
{
    private final MedicineLogic medicineLogic;
    private Scene mainScene;
    private TableView<Medicine> medicineTable;
    private TableColumn<Medicine, String> nameColumn;
    private TableColumn<Medicine, String> dosageColumn;
    private TableColumn<Medicine, LocalTime> timeColumn;
    private HBox inputBox;
    private TextField nameInputField;
    private TextField dosageInputField;
    private TextField timeInputField;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;
    private Button cancelButton;

    public MainController(MedicineLogic medicineLogic) 
    {
        this.medicineLogic = medicineLogic;
    }

    /**
     * This method is responsible for setting up the main scene for the application
     * @return the main Scene object
     */
    public Scene createMainScene()
    {
        // The main layout container
        BorderPane root = new BorderPane();

        // Top section with Title
        Label titleLabel = new Label("Medications");

        HBox topSection = new HBox(10, titleLabel);
        topSection.setPadding(new Insets(10));
        topSection.setAlignment(Pos.CENTER);
        root.setTop(topSection);

        // ----------------------------------------------------------------------------------------------
        // Center section 
        medicineTable = new TableView<>();
        medicineTable.setEditable(false);

        nameColumn = new TableColumn<>("Name");
        dosageColumn = new TableColumn<>("Dosage");
        timeColumn = new TableColumn<>("Time to Take");
        medicineTable.getColumns().addAll(nameColumn, dosageColumn, timeColumn);
        medicineTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        medicineTable.setItems(medicineLogic.getAllMedicines());

        nameColumn.setCellValueFactory(cd -> cd.getValue().nameProperty());
        dosageColumn.setCellValueFactory(cd -> cd.getValue().dosageProperty());
        timeColumn.setCellValueFactory(cd -> cd.getValue().timeToTakeProperty());

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dosageColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Input fields for adding new medicine
        nameInputField = new TextField();
        nameInputField.setPromptText("Name");
        dosageInputField = new TextField();
        dosageInputField.setPromptText("Dosage");
        timeInputField = new TextField();
        timeInputField.setPromptText("Time to Take (HH:MM)");

        inputBox = new HBox(10, nameInputField, dosageInputField, timeInputField);
        inputBox.setPadding(new Insets(10));
        inputBox.setVisible(false); // Initially hidden
        inputBox.setManaged(false); // Exclude from layout calculations when hidden


        VBox centerSection = new VBox(inputBox, medicineTable);
        root.setCenter(centerSection);

        // ----------------------------------------------------------------------------------------------
        // Bottom section with Buttons
        addButton = new Button("Add");
        editButton = new Button("Edit");
        editButton.setDisable(true); // Initially disabled
        deleteButton = new Button("Delete");
        deleteButton.setDisable(true); // Initially disabled
        cancelButton = new Button("Cancel");
        cancelButton.setDisable(true); // Initially disabled
        cancelButton.setVisible(false); // Initially hidden

        HBox bottomSection = new HBox(10, addButton, cancelButton, editButton, deleteButton);
        bottomSection.setPadding(new Insets(10));

        // Push buttons apart
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomSection.getChildren().add(2, spacer);
        root.setBottom(bottomSection);

        // ----------------------------------------------------------------------------------------------
        // Set event handlers and button actions
        EventHandler<KeyEvent> enterKeyHandler = e -> {
            if(e.getCode() == KeyCode.ENTER) {
                commitNewMedicine();
            }
        };

        // When the enter key is pressed in any input field, commit the new medicine
        nameInputField.setOnKeyPressed(enterKeyHandler);
        dosageInputField.setOnKeyPressed(enterKeyHandler);
        timeInputField.setOnKeyPressed(enterKeyHandler);

        addButton.setOnAction(e -> handleAddButton());
        deleteButton.setOnAction(e -> handleDeleteButton());
        editButton.setOnAction(e -> handleEditButton());
        cancelButton.setOnAction(e -> handleCancelButton());


        // Create the scene
        mainScene = new Scene(root, 500, 400); 
        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return mainScene;
    }

    /**
     * Handles Add button logic when clicked
     */
    private void handleAddButton()
    {
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        addButton.setDisable(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);

        nameInputField.clear();
        dosageInputField.clear();
        timeInputField.clear();

        nameInputField.requestFocus();
    }

    /**
     * Handles Delete button logic when clicked
     */
    private void handleDeleteButton()
    {
        // TO DO: Implement logic to handle deleting a selected medicine
    }

    /**
     * Handles Edit button logic when clicked
     */
    private void handleEditButton()
    {
        // TO DO: Implement logic to handle editing a selected medicine
    }

    private void handleCancelButton()
    {
        inputBox.setVisible(false);
        inputBox.setManaged(false);
        addButton.setDisable(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
    }

    /**
     * Creates a new Medicine from input fields and adds it to the list
     */
    private void commitNewMedicine()
    {
        if(nameInputField.getText().isBlank()) // Name is mandatory
        {
            nameInputField.requestFocus();
            return;
        }
        else if(!nameInputField.getText().isBlank() && dosageInputField.getText().isBlank()) // Dosage is mandatory
        {
            dosageInputField.requestFocus();
            return;
        }
      

        Medicine newMedicine = new Medicine(
            nameInputField.getText().trim(),
            dosageInputField.getText().trim(),
            LocalTime.parse(timeInputField.getText().trim())
        );

        medicineLogic.addMedicine(newMedicine);

        inputBox.setVisible(false);
        inputBox.setManaged(false);
        addButton.setDisable(false);
    }
}
