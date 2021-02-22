package edu.wpi.u.requests;
import java.util.ArrayList;
import java.util.Date;

//TODO: Private or protected fields?
public class MaintenanceRequest extends Request {
    private String machine;
    private int priority;

    public MaintenanceRequest(Date dateCreated, Date dateCompleted, String description, String title,
                              ArrayList<Staff> assignees, String location, String machine, int priority) {
        super.dateCreated = dateCreated;
        super.dateCompleted = dateCompleted;
        super.description = description;
        super.title = title;
        super.assignees = assignees;
        super.location = location;
        this.machine = machine;
        this.priority = priority;
    }

    protected void editRequest(Date startDate, Date endDate, String description, String title, ArrayList<Staff> assignees, String location) {
        super.dateCreated = startDate;
        super.dateCompleted = endDate;
        super.description = description;
        super.title = title;
        super.assignees = assignees;
        super.location = location;
    }

    private void blocksPath() { //TODO: void or boolean?

    }
}
