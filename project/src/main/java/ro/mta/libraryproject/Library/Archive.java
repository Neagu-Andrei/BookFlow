package ro.mta.libraryproject.Library;

import java.time.LocalDate;

public class Archive {

    String username;
    LocalDate expirationDate;
    boolean readAccess;

    public Archive(String username, LocalDate expirationDate, boolean readAccess) {
        this.username = username;
        this.expirationDate = expirationDate;
        this.readAccess = readAccess;
    }

    public String getUser() {
        return username;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean getReadAccess() {
        return readAccess;
    }
}
