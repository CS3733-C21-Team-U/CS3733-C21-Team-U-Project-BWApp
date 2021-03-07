package edu.wpi.u.requests;
import java.sql.Timestamp;

public class Comment{
    String title;
    String description;
    String author;
    CommentType type;
    Timestamp timestamp;

    public Comment(String title, String description, String author, CommentType type, Timestamp timestamp) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.type = type;
        this.timestamp = timestamp;
    }
}
