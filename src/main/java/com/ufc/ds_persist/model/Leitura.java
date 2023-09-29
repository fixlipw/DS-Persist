package com.ufc.ds_persist.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import com.ufc.ds_persist.enumeration.BookType;

@JacksonXmlRootElement(localName = "leitura")
public class Leitura {


    @JacksonXmlProperty(localName = "title")
    private String title;
    @JacksonXmlProperty(localName = "author_name")
    private String authorName;
    @JacksonXmlProperty(localName = "pages")
    private int pagesQtd;
    @JacksonXmlProperty(localName = "reading_type")
    private BookType type;

    public Leitura(String title, String authorName, int pagesQtd, BookType type) {
        this.title = title;
        this.authorName = authorName;
        this.type = type;
        this.pagesQtd = pagesQtd;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getPagesQtd() {
        return pagesQtd;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public BookType getType() {
        return type;
    }

    public void setPagesQtd(int pagesQtd) {
        this.pagesQtd = pagesQtd;
    }

    @Override
    public String toString() {
        return "Leitura{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", pagesQtd=" + pagesQtd +
                ", gender='" + type + '\'' +
                '}';
    }
}