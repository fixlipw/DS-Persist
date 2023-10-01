package com.ufc.ds_persist.model;

import com.ufc.ds_persist.enumeration.BookStatus;
import com.ufc.ds_persist.enumeration.BookType;

public class Leitura {

    private String title;
    private String authorName;
    private int pagesQtd;
    private BookType type;
    private BookStatus status;

    public Leitura(String title, String authorName, int pagesQtd, BookType type, BookStatus status) {
        this.title = title;
        this.authorName = authorName;
        this.type = type;
        this.pagesQtd = pagesQtd;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPagesQtd() {
        return pagesQtd;
    }

    public void setPagesQtd(int pagesQtd) {
        this.pagesQtd = pagesQtd;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Leitura{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", pagesQtd=" + pagesQtd +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}