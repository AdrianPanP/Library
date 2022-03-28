package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.library.model.Library;
import pl.library.model.Publication;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {


    @Test
    public void addPublication_PublicationNull_throws() {
        Publication pub = null;
        Library library = new Library();
        Assertions.assertThrows(NullPointerException.class, ()-> library.addPublication(pub));
    }
}