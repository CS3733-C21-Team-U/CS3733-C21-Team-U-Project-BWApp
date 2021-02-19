package edu.wpi.u.requests;

import java.util.List;

//TODO: Private or protected fields?
public class SecurityRequest extends Request {
    private String codeLevel;

    public SecurityRequest(Date dateCreated, Date dateCompleted, String description,
                           List<Staff> assignees, String codeLevel) {
        super.dateCreated = dateCreated;
        super.dateCompleted = dateCompleted;
        super.description = description;
        super.assignees = assignees;
        this.codeLevel = codeLevel;
    }
}
