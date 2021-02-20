package edu.wpi.u.requests;
import java.util.Date;
import java.util.List;

//TODO: Private or protected fields?
public class MaintenanceRequest extends Request {
    private String machine;
    private int priority;

    public MaintenanceRequest(Date dateCreated, Date dateCompleted, String description,
                              List<Staff> assignees, String machine, int priority) {
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
