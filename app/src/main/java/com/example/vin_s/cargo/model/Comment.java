package com.example.vin_s.cargo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Howard on 7/3/16.
 */
public class Comment implements Serializable{

    private String id;
    private String postID;
    private String ownerID;
    private String content;
    private Date dateOfComment;
    private String replyTo; //Store the id of the comment that this comment is replying to

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

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }
}
