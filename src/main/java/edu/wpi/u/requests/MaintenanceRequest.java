package edu.wpi.u.requests;
import java.util.ArrayList;
import java.util.Date;

//TODO: Private or protected fields?
public class MaintenanceRequest extends Request {
    private String machine;
    private int priority;

    public MaintenanceRequest(Date dateCreated, Date dateCompleted, String description,
                              ArrayList<Staff> assignees, String machine, int priority) {
        super.dateCreated = dateCreated;
        super.dateCompleted = dateCompleted;
        super.description = description;
        super.assignees = assignees;
        this.machine = machine;
        this.priority = priority;
    }

    private void blocksPath() { //TODO: void or boolean?

    }
}
