package logic;

import java.util.ArrayList;
import java.util.List;
import model.Medicine;

public class MedicineLogic 
{
    private List<Medicine> medicationList = new ArrayList<>();

    public void addMedicine(Medicine medicine) 
    {
        medicationList.add(medicine);
    }

    public void markMedicineTaken(Medicine medicine)
    {
        medicine.setTaken(true);
    }
}
