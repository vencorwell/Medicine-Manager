package ui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
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
    private Spinner<LocalTime> timeInputField;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;
    private Button cancelButton;
    private Button saveButton;
    private boolean isInEditMode = false;

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
        
        // Spinner for time input
        timeInputField = new Spinner<>();
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<LocalTime>()
        {
            {
                setValue(LocalTime.of(0,0)); // default time
            }

            @Override
            public void decrement(int steps)
            {
                setValue(getValue().minusMinutes(steps * 1));
            }

            @Override
            public void increment(int steps)
            {
                setValue(getValue().plusMinutes(steps * 1));
            }
        };

        // Formatter for displaying time in HH:mm format when editing using the keyboard and not the spinner arrows
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        StringConverter<LocalTime> timeConverter = new StringConverter<LocalTime>()
        {
            @Override
            public String toString(LocalTime time)
            {
                return time == null ? "" : time.format(timeFormatter);
            }

            @Override
            public LocalTime fromString(String string)
            {
                if(string == null || string.isBlank())
                {
                    return null;
                }
                return LocalTime.parse(string, timeFormatter);
            }
        };

        // Apply the converter to the spinner
        timeValueFactory.setConverter(timeConverter);
        timeInputField.setValueFactory(timeValueFactory);
        timeInputField.setEditable(true);
        timeInputField.setPrefWidth(100);

        saveButton = new Button("Save");

        // HBox to hold input fields
        inputBox = new HBox(10, nameInputField, dosageInputField, timeInputField, saveButton);
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

        // Common handler for Enter key press in input fields
        EventHandler<KeyEvent> enterKeyHandler = e -> {
            if(e.getCode() == KeyCode.ENTER) 
            {
                if(addButton.isDisabled()) // Only add if in add mode
                {
                    timeInputField.increment(0); // force spinner value
                    addNewMedicine();
                }
            }
        };

        // When the enter key is pressed in any input field, add the new medicine to the table
        nameInputField.setOnKeyPressed(enterKeyHandler);
        dosageInputField.setOnKeyPressed(enterKeyHandler);
        timeInputField.getEditor().setOnKeyPressed(enterKeyHandler);

        // Table click handler to enable Edit and Delete buttons when a row is selected
        EventHandler<MouseEvent> tableClickHandler = e -> {
            if(medicineTable.getSelectionModel().getSelectedItem() != null)
            {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        };

        medicineTable.setOnMouseClicked(tableClickHandler);

        // Button actions
        addButton.setOnAction(e -> handleAddButton());
        deleteButton.setOnAction(e -> handleDeleteButton());
        editButton.setOnAction(e -> handleEditButton());
        cancelButton.setOnAction(e -> handleCancelButton());
        saveButton.setOnAction(e -> handleSaveButton());


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
        timeInputField.getValueFactory().setValue(LocalTime.of(0,0));

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
        isInEditMode = true;
        Medicine selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
        if(selectedMedicine != null)
        {
            inputBox.setVisible(true);
            inputBox.setManaged(true);
            addButton.setDisable(true);
            cancelButton.setVisible(true);
            cancelButton.setDisable(false);

            nameInputField.setText(selectedMedicine.getName());
            dosageInputField.setText(selectedMedicine.getDosage());
            timeInputField.getValueFactory().setValue(selectedMedicine.getTimeToTake());

            nameInputField.requestFocus();
        }
    }

    private void handleCancelButton()
    {
        inputBox.setVisible(false);
        inputBox.setManaged(false);
        addButton.setDisable(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
    }

    private void handleSaveButton()
    {
        if(addButton.isDisabled() && isInEditMode == false) // Saving a new medicine
        {
            timeInputField.increment(0); // force spinner value
            addNewMedicine();
        }
        else if(isInEditMode) // Saving edits to an existing medicine
        {
            // TO DO: Implement logic to save edits to the selected medicine
        }
    }

    /**
     * Creates a new Medicine from input fields and adds it to the list
     */
    private void addNewMedicine()
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
            timeInputField.getValue()
        );

        medicineLogic.addMedicine(newMedicine);

        inputBox.setVisible(false);
        inputBox.setManaged(false);
        addButton.setDisable(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
    }
}
