package pl.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryUser extends User {
    private final List<Publication> publicationsHistory = new ArrayList<>();
    private final List<Publication> borrowedPublication = new ArrayList<>();

    public LibraryUser(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    @Override
    public String toCsv() {
        return getFirstName() + ";" + getLastName() + ";" + getPesel();
    }

    public List<Publication> getPublicationsHistory() {
        return publicationsHistory;
    }

    public List<Publication> getBorrowedPublication() {
        return borrowedPublication;
    }

    public void addPublicationToHistory(Publication pub) {
        publicationsHistory.add(pub);
    }

    public void borrowedPublications(Publication pub) {
        borrowedPublication.add(pub);
    }

    public boolean returnPublication(Publication pub) {
        boolean result = false;
        if (borrowedPublication.contains(pub)) {
            borrowedPublication.remove(pub);
            addPublicationToHistory(pub);
            result = true;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUser that = (LibraryUser) o;
        return Objects.equals(publicationsHistory, that.publicationsHistory) &&
                Objects.equals(borrowedPublication, that.borrowedPublication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationsHistory, borrowedPublication);
    }
}
