package ui;

import javafx.scene.*;
import logic.MedicineLogic;

/*
Main Controller for the Medicine Manager application
Handles interactions between the UI and the logic layer
*/
public class MainController 
{
    private MedicineLogic medicineLogic;
    private Scene mainScene;

    public MainController(MedicineLogic medicineLogic) 
    {
        this.medicineLogic = medicineLogic;
    }

    public Scene createMainScene()
    {
        // Implementation for creating the main scene
        // This method will be responsible for setting up the main UI scene

        mainScene = new Scene(new Group(), 400, 400); // Placeholder implementation
        return mainScene;
    }
}
