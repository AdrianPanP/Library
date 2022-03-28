package pl.library.model;

import java.util.Objects;

public class Book extends Publication {
    public static final String TYPE = "Książka";


    //Pola
    private String author;
    private int pages;
    private String isbn;


    //Konstruktory

    public Book(String title, String author, int year, int pages,  String publisher) {
        super(year, title, publisher);
        this.author = author;
        this.pages = pages;
    }

    public Book(String title, String author, int year, int pages, String publisher, String isbn) {
        this(title, author, year, pages, publisher);
        this.isbn = isbn;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";")+
                getTitle() +";"+
                getPublisher() +";"+
                getYear() +";"+
                author +";"+
                pages +";"+
                isbn +" ";
    }

    //getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return getTitle() + " " + author + " " + getYear() + " " +  pages + " " + getPublisher() + " " + isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages &&
                Objects.equals(author, book.author) &&
                Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, pages, isbn);
    }


}

