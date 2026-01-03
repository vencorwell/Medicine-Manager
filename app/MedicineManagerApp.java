package app;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.MedicineLogic;
import ui.MainController;

public class MedicineManagerApp extends Application
{
    @Override
    public void start(Stage primaryStage) 
    {
        // Initializes the logic layer
        MedicineLogic medicineLogic = new MedicineLogic();

        // Creates link to the main controller
        MainController controller = new MainController(medicineLogic);

        // Initialize and show the main application window
        primaryStage.setTitle("Medicine Manager");
        primaryStage.setScene(controller.createMainScene());
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
