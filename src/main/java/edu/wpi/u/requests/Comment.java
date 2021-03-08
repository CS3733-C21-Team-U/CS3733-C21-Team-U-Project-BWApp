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

    public Comment(String title, String description, String author, CommentType type) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.type = type;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public CommentType getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
