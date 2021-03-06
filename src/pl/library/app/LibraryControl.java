package pl.library.app;

import pl.library.exceptions.*;
import pl.library.io.ConsolePrinter;
import pl.library.io.DataReader;
import pl.library.io.file.FileManager;
import pl.library.io.file.FileManagerBuilder;
import pl.library.model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

public class
LibraryControl {

    private final ConsolePrinter printer = new ConsolePrinter();
    private final DataReader dataReader = new DataReader(printer);
    private final FileManager fileManager;

    private Library library;

    public LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();

        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę. ");
            library = new Library();
        }
    }

    public void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK -> addBook();
                case ADD_MAGAZINE -> addMagazine();
                case PRINT_BOOKS -> printBooks();
                case PRINT_MAGAZINES -> printMagazines();
                case DELETE_PUBLICATION -> deletePublication();
                case ADD_USER -> addUser();
                case PRINT_USERS -> printUsers();
                case FIND_BOOK -> findPublication();
                case BORROW_PUBLICATION -> borrowPublication();
                case PRINT_BORROWED_PUBLICATIONS -> printBorrowedPublications();
                case EXIT -> exit();
                default -> printer.printLine("Nie ma takiej opcji, wprowadź ponownie");
            }
        } while (option != Option.EXIT);
    }

    private void printBorrowedPublications() {
        for (LibraryUser value : library.getUsers().values()) {
            if (!value.getBorrowedPublication().isEmpty()){
                printer.printLine(value.toString());
                value.getBorrowedPublication().forEach(publication -> printer.printLine(publication.toString()));
            }
        }
    }

    private void borrowPublication() {
        String notFoundMessage = "Nie znaleziono publikacji o takim tytule";
        printer.printLine("Podaj pesel użytkownika na kogo ma zostać wypożyczona publikacja");
        String getPesel = dataReader.getString();

        if (library.getUsers().containsKey(getPesel)){
            printer.printLine("Wybrano uzytkownika: " +library.getUsers().get(getPesel).toString());
            printer.printLine("Podaj tytuł publikacji jaka ma zostać przypisana do uzytkownika");
            String getTitle = dataReader.getString();
            library.findPublicationByTitle(getTitle)
                    .ifPresentOrElse(
                            pubIsPresent -> library
                                    .getUsers()
                                    .get(getPesel)
                                    .borrowedPublications(library.getPublications().get(getTitle)),
                            () -> printer.printLine(notFoundMessage)
                    );
        }
    }

    private void findPublication() {
        String notFoundMessage = "Nie znaleziono publikacji o takim tytule";

        printer.printLine("Podaj tytuł publikacji:");
        String title = dataReader.getString();
        library.findPublicationByTitle(title)
                .map(Publication::toString)
                .ifPresentOrElse(
                        pubIsPresent -> printer.printLine(pubIsPresent),
                        () -> printer.printLine(notFoundMessage)
                );
    }

    private void deletePublication() {
        String key = dataReader.getTitleToDelete();
        if (library.getPublications().containsKey(key)) {
            library.getPublications().remove(key);
            printer.printLine("Usunięto " + key + " z bazy.");
        } else printer.printLine("Nie znaleziono publikacji " + key + " w bazie.");

    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printUsers() {
        printer.printUser(library.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;

        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzona wartość nie jest liczbą, podaj ponownie.");
            }
        }
        return option;
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Eksport danych do pliku zakończono powodzeniem");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Wyjście z programu.");
        dataReader.close();
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję:");
        Option[] options = Option.values();
        for (Option option : options) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane.");
        }
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane.");
        }
    }

    private void printBooks() {
        printer.printBooks(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void printMagazines() {
        printer.printMagazines(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)
        ));
    }
}


enum Option {
    EXIT(0, "Wyjście z programu"),
    ADD_BOOK(1, "Dodaj książkę"),
    ADD_MAGAZINE(2, "Dodaj magazyn"),
    PRINT_BOOKS(3, "Wyświetl dostępne książki"),
    PRINT_MAGAZINES(4, "Wyświetl dostępne magazyny"),
    DELETE_PUBLICATION(5, "Usuń publikację"),
    ADD_USER(6, "Dodaj czytelnika"),
    PRINT_USERS(7, "Wyświetl użytkowników"),
    FIND_BOOK(8, "Wyszukaj książkę"),
    BORROW_PUBLICATION (9, "Wypożycz książkę lub magazyn"),
    PRINT_BORROWED_PUBLICATIONS(10, "Wyświetl wypozyczone pozycje");


    private final int value;
    private final String description;

    Option(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " - " + description;
    }

    static Option createFromInt(int option) throws NoSuchOptionException {
        try {
            return Option.values()[option];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("Brak opcji o id " + option);
        }
    }
}

