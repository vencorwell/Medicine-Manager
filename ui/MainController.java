package ui;

import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private MedicineLogic medicineLogic;
    private Scene mainScene;
    private TableView<Medicine> medicineTable;
    private TableColumn<Medicine, String> nameColumn;
    private TableColumn<Medicine, String> dosageColumn;
    private TableColumn<Medicine, LocalTime> timeColumn;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;

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
        // Center section with TableView
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

        root.setCenter(medicineTable);

        // ----------------------------------------------------------------------------------------------
        // Bottom section with Buttons
        addButton = new Button("Add");
        editButton = new Button("Edit");
        editButton.setDisable(true); // Initially disabled
        deleteButton = new Button("Delete");
        deleteButton.setDisable(true); // Initially disabled

        HBox bottomSection = new HBox(10, addButton,editButton, deleteButton);
        bottomSection.setPadding(new Insets(10));

        // Push buttons apart
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomSection.getChildren().add(1, spacer);
        root.setBottom(bottomSection);

        // ----------------------------------------------------------------------------------------------
        // Set button actions
        addButton.setOnAction(e -> handleAddButton());
        deleteButton.setOnAction(e -> handleDeleteButton());
        editButton.setOnAction(e -> handleEditButton());


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
        medicineTable.setEditable(true);

        Medicine newMedicine = new Medicine("", "", null);
        medicineLogic.addMedicine(newMedicine);

        int row =  medicineTable.getItems().size() - 1;
        medicineTable.getSelectionModel().select(row);
        medicineTable.edit(row, nameColumn);
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
}
