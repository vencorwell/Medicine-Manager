package model;

import java.time.LocalTime;
import javafx.beans.property.*;

public class Medicine 
{
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty dosage = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> timeToTake = new SimpleObjectProperty<>();
    private final BooleanProperty taken = new SimpleBooleanProperty();

    public Medicine(String name, String dosage, LocalTime timeToTake) 
    {
        this.name.set(name);
        this.dosage.set(dosage);
        this.timeToTake.set(timeToTake);
    }

    public String getName() 
    {
        return name.get();
    }

    public void setName(String name) 
    {
        this.name.set(name);
    }

    public StringProperty nameProperty() 
    {
        return name;
    }

    public String getDosage() 
    {
        return dosage.get();
    }
    

    public void setDosage(String dosage) 
    {
        this.dosage.set(dosage);
    }

    public StringProperty dosageProperty() 
    {
        return dosage;
    }

    public LocalTime getTimeToTake() 
    {
        return timeToTake.get();
    }

    public void setTimeToTake(LocalTime timeToTake) 
    {
        this.timeToTake.set(timeToTake);
    }

    public ObjectProperty<LocalTime> timeToTakeProperty() 
    {
        return timeToTake;
    }

    public boolean isTaken() 
    {
        return taken.get();
    }

    public void setTaken(boolean taken) 
    {
        this.taken.set(taken);
    }

    public BooleanProperty takenProperty() 
    {
        return taken;
    }
}
