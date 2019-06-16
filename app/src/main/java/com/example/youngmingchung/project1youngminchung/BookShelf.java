package com.example.youngmingchung.project1youngminchung;


public class BookShelf {

    private String title;
    private String genre;
    private String author;
    private String date;
    public BookShelf(){}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "Student [ title: "+title+", genre: "+ genre +
                ", author: "+ author + ", date: "+ date +" ]";
    }


}
