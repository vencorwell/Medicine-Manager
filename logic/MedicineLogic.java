package logic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Medicine;

public class MedicineLogic 
{
    private final ObservableList<Medicine> medicationList = FXCollections.observableArrayList();

    public ObservableList<Medicine> getAllMedicines() 
    {
        return medicationList;
    }

    public void addMedicine(Medicine medicine) 
    {
        medicationList.add(medicine);
    }

    public void markMedicineTaken(Medicine medicine)
    {
        medicine.setTaken(true);
    }
}
