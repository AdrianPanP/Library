package pl.library.io;

import pl.library.model.Book;
import pl.library.model.LibraryUser;
import pl.library.model.Magazine;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public LibraryUser createLibraryUser() {
        printer.printLine("Imię:");
        String firstName = sc.nextLine();
        printer.printLine("Nazwisko:");
        String lastName = sc.nextLine();
        printer.printLine("Pesel:");
        String pesel = sc.nextLine();
        return new LibraryUser(firstName, lastName, pesel);
    }

    public Book readAndCreateBook() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Autor: ");
        String author = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int releaseYear = getInt();
        printer.printLine("Ilośc stron: ");
        int pages = getInt();
        printer.printLine("Wydawca: ");
        String publisher = sc.nextLine();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();
        return new Book(title, author, releaseYear, pages, publisher, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Wydawca: ");
        String publisher = sc.nextLine();
        printer.printLine("Język:");
        String language = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int year = getInt();
        printer.printLine("Miesiąc: ");
        int month = getInt();
        printer.printLine("Dzień: ");
        int day = getInt();

        return new Magazine(title, publisher, year, month, day, language);
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString() {
        return sc.nextLine();
    }

    public void close() {
        sc.close();
    }

    public String getTitleToDelete() {
        printer.printLine("Podaj tytuł jaki chcesz usunąć z bazy");
        return sc.nextLine();
    }
}
