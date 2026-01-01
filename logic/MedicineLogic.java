package logic;

import model.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineLogic 
{
    private List<Medicine> medicines = new ArrayList<>();

    public void addMedicine(Medicine medicine) 
    {
        medicines.add(medicine);
    }

    public void markMedicineTaken(Medicine medicine)
    {
        medicine.setTaken(true);
    }
}
