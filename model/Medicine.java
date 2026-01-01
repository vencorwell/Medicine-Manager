package model;

import java.time.LocalTime;

public class Medicine 
{
    private String name;
    private String dosage;
    private LocalTime timeToTake;
    private boolean taken;

    public Medicine(String name, String dosage, LocalTime timeToTake) 
    {
        this.name = name;
        this.dosage = dosage;
        this.timeToTake = timeToTake;
    }

    public String getName() 
    {
        return name;
    }

    public String getDosage() 
    {
        return dosage;
    }

    public LocalTime getTimeToTake() 
    {
        return timeToTake;
    }

    public boolean isTaken() 
    {
        return taken;
    }

    public void setTaken(boolean taken) 
    {
        this.taken = taken;
    }
}
