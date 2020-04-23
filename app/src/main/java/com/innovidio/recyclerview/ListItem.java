package com.innovidio.recyclerview;




public class ListItem {
    private String title;
    private String imageURl;

    public ListItem(String title, String imageURl) {
        this.title = title;
        this.imageURl = imageURl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public String getImageURl() {
        return imageURl;
    }
}

