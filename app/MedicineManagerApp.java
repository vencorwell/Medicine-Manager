package app;

import ui.MainController;
import logic.MedicineLogic;
import model.Medicine;

import javafx.application.Application;
import javafx.stage.Stage;

public class MedicineManagerApp extends Application
{
    @Override
    public void start(Stage primaryStage) 
    {
        // Initializes the logic layer
        MedicineLogic medicineLogic = new MedicineLogic();

        // Creates link to the main controller
        MainController controller = new MainController();

        // Initialize and show the main application window
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
