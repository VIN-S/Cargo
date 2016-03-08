package com.example.vin_s.cargo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Howard on 7/3/16.
 */
public class Comment implements Serializable{

    private String id;
    private String postID;
    private String ownerID;
    private String content;
    private Date dateOfComment;

    public Comment() {
        this.id = "com" + UUID.randomUUID().toString();
    }

    public Comment(String postID, String ownerID, String content, Date dateOfComment) {
        this.id = "com" + UUID.randomUUID().toString();
        this.postID = postID;
        this.ownerID = ownerID;
        this.content = content;
        this.dateOfComment = dateOfComment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateOfComment() {
        return dateOfComment;
    }

    public void setDateOfComment(Date dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

}
