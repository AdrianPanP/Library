package pl.library.io.file;

import pl.library.exceptions.DataExportException;
import pl.library.exceptions.DataImportException;
import pl.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "Library.o";

    @Override
    public Library importData() {
        try     (
                FileInputStream fis =new FileInputStream(FILE_NAME) ;
                ObjectInputStream ois = new ObjectInputStream(fis);
                ){
            return (Library) ois.readObject();  //trzeba zrobić rzutowanie gdyż metoda zwraca obiekt typu Object
        } catch(FileNotFoundException e){
            throw new DataImportException("Brak pliku: " + FILE_NAME);
        } catch(IOException e){
            throw new DataImportException("Błąd odczytu pliku: " + FILE_NAME);
        } catch(ClassNotFoundException e){
            throw new DataImportException("Niezgodny typ danych w pliku: " + FILE_NAME);
        }
    }

    @Override
    public void exportData(Library library) {

        try     (
                FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                )
        {
            oos.writeObject(library);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku. " + FILE_NAME);
        }
    }
}
