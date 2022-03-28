package pl.library.model;

import java.io.Serializable;
import java.time.Year;

public abstract class Publication implements Serializable, Comparable<Publication>, CsvConvertible {
    private Year year;
    private String title;
    private String publisher;

    public Publication(int year, String title, String publisher) {
        this.year = Year.of(year);
        this.title = title;
        this.publisher = publisher;
    }


    @Override
    public String toString() {
        return title + " " + year + " " + publisher;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public int compareTo(Publication o) {
        return title.compareToIgnoreCase(o.title);
    }
}
