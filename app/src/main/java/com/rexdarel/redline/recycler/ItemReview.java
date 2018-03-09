package com.rexdarel.redline.recycler;

/**
 * Created by Admin on 3/10/2018.
 */

public class ItemReview {
    private String message;
    private Long rate;
    private String author;
    private String timestamp;
    private String id;

    public ItemReview(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
