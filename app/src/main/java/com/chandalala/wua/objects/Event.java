package com.chandalala.wua.objects;

public class Event {

    private String time, content;
    private String image;

    public Event(Event event) {
        this.time = event.getTime();
        this.content = event.getContent();
        this.image = event.getImage();
    }

    public Event(String time, String content, String image) {
        this.time = time;
        this.content = content;
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
