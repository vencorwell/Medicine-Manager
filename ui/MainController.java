package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
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
        TableColumn<Medicine, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Medicine, String> dosageColumn = new TableColumn<>("Dosage");
        TableColumn<Medicine, String> timeColumn = new TableColumn<>("Time to Take");
        medicineTable.getColumns().addAll(nameColumn, dosageColumn, timeColumn);
        medicineTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
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
        // TO DO: Implement logic to handle adding a new medicine
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
