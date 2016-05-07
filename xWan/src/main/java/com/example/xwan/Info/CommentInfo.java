package com.example.xwan.Info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/19.
 */
public class CommentInfo implements Serializable{
    private String commentId;
    private String commentTo_userName;
    private String commentTo_userId;
    private String commentFrom_uesrName;
    private String commentFrom_uesrHeardUrl;
    private String commentFrom_userId;
    private String commentContent_text;
    private List<ImageInfo> commentImgUrls;

    public List<ImageInfo> getCommentImgUrls() {
        return commentImgUrls;
    }

    public void setCommentImgUrls(List<ImageInfo> commentImgUrls) {
        this.commentImgUrls = commentImgUrls;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentTo_userName() {
        return commentTo_userName;
    }

    public void setCommentTo_userName(String commentTo_userName) {
        this.commentTo_userName = commentTo_userName;
    }

    public String getCommentTo_userId() {
        return commentTo_userId;
    }

    public void setCommentTo_userId(String commentTo_userId) {
        this.commentTo_userId = commentTo_userId;
    }

    public String getCommentFrom_uesrName() {
        return commentFrom_uesrName;
    }

    public void setCommentFrom_uesrName(String commentFrom_uesrName) {
        this.commentFrom_uesrName = commentFrom_uesrName;
    }

    public String getCommentFrom_uesrHeardUrl() {
        return commentFrom_uesrHeardUrl;
    }

    public void setCommentFrom_uesrHeardUrl(String commentFrom_uesrHeardUrl) {
        this.commentFrom_uesrHeardUrl = commentFrom_uesrHeardUrl;
    }

    public String getCommentFrom_userId() {
        return commentFrom_userId;
    }

    public void setCommentFrom_userId(String commentFrom_userId) {
        this.commentFrom_userId = commentFrom_userId;
    }

    public String getCommentContent_text() {
        return commentContent_text;
    }

    public void setCommentContent_text(String commentContent_text) {
        this.commentContent_text = commentContent_text;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "commentId='" + commentId + '\'' +
                ", commentTo_userName='" + commentTo_userName + '\'' +
                ", commentTo_userId='" + commentTo_userId + '\'' +
                ", commentFrom_uesrName='" + commentFrom_uesrName + '\'' +
                ", commentFrom_uesrHeardUrl='" + commentFrom_uesrHeardUrl + '\'' +
                ", commentFrom_userId='" + commentFrom_userId + '\'' +
                ", commentContent_text='" + commentContent_text + '\'' +
                ", commentImgUrls=" + commentImgUrls +
                '}';
    }
}
