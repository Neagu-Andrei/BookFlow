package ro.mta.libraryproject.Main;

import org.javatuples.Pair;
import ro.mta.libraryproject.Library.Archive;
import ro.mta.libraryproject.Library.Book;
import ro.mta.libraryproject.Others.Utils;
import ro.mta.libraryproject.Persons.Librarian;
import ro.mta.libraryproject.Persons.RegisteredUser;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Class implementing the database controller part of the Application
 *
 *  Valentin-Ciprian Popescu
 */
public class ManagerDB {
    private final String jdbcURL = "jdbc:sqlserver://libraryip1.database.windows.net:1433;database=library";
    private final String username = "library";
    private final String password = "Database1";
    private Connection connection;
    private Statement statement;

    /**
     * Default constructor for class ro.mta.libraryproject.Main.ManagerDB
     * Uses the default string connection
     */
    public ManagerDB() {
        try {
            Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("DB Connection Status: Refused(exception thrown)!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructor for class ro.mta.libraryproject.Main.ManagerDB
     *
     * @param jdbcURL  The location of the database
     * @param username The user you will use for log in
     * @param password The password of the user
     */
    public ManagerDB(String jdbcURL, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("DB Connection Status: Refused(exception thrown)!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructor for class ro.mta.libraryproject.Main.ManagerDB
     *
     * @param username The user you will use for log in
     * @param password The password of the user
     */
    public ManagerDB(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(this.jdbcURL, username, password);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("DB Connection Status: Refused(exception thrown)!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return Returns the active user that works with the database
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method is used to switch the user of the database in case every
     * person has different permissions
     *
     * @param username The name of the new user
     * @param password The password of the new user
     */
    public void switchUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(this.jdbcURL, username, password);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("DB Connection Status: Refused(exception thrown). Couldn't switch user!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Function that is used for queries in the database
     *
     * @param sql The select statement we want to use
     * @return Returns a ResultSet of the query
     */
    public ResultSet executeSelect(String sql) {
        try {
            ResultSet resultSet = this.statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());

            return null;
        }
    }

    /**
     * Function that is used for queries in the database
     * The result of the query will be displayed in the terminal
     *
     * @param sql The select statement we want to use
     */
    public void printSelect(String sql) {
        try {
            ResultSet resultSet = this.statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnsNumber = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + metaData.getColumnName(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return Returns a pair of ResultSet and ResultSetMetaData
     * ResultSet contains the actual data stored in the table
     * ResultSetMetaData contains data like number of columns or columns names
     */
    public Pair<ResultSet, ResultSetMetaData> fetchBooks() {
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from book;");
            ResultSetMetaData metaData = resultSet.getMetaData();

            return new Pair<ResultSet, ResultSetMetaData>(resultSet, metaData);
        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public Pair<ResultSet, ResultSetMetaData> fetchShelfs() {
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from shelfs;");
            ResultSetMetaData metaData = resultSet.getMetaData();

            return new Pair<ResultSet, ResultSetMetaData>(resultSet, metaData);
        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public Pair<ResultSet, ResultSetMetaData> fetchBookSections() {
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from sections;");
            ResultSetMetaData metaData = resultSet.getMetaData();

            return new Pair<ResultSet, ResultSetMetaData>(resultSet, metaData);
        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void loginUser(RegisteredUser registeredUser) {
        String hash_pass = registeredUser.getPassword();
        String user = registeredUser.getUsername();
        String query = "select username, personPassword, r.roleName from person as p inner JOIN role as r on r.ID = p.personRole where username = ";
        query += "'" + user + "' and personPassword = ";
        query += "'" + hash_pass + "';";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            if (count == 1) {
                System.out.println("ro.mta.libraryproject.Persons.RegisteredUser: " + user + " has logged in!");
            }

        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());
        }
    }

    public String login(String username, String password_hash) {
        String query = "select username, personPassword, r.roleName from person as p inner JOIN role as r on r.ID = p.personRole where username = ";
        query += "'" + username + "' and personPassword = ";
        query += "'" + password_hash + "';";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            if (count == 1) {
                ResultSet resultSet2 = this.statement.executeQuery(query);
                resultSet2.next();

                return resultSet2.getString(3);
            } else {
                return null;
            }


        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());

            return null;
        }
    }

    /**
     * Function that is used to add a librarian in the database
     *
     * @param librarian Instance that contains the librarian's data
     * @return Returns true if the query was succesfully executed, false if not
     */
    public boolean addLibrarian(Librarian librarian) {
        String firstName = librarian.getFirstName();
        String lastName = librarian.getLastName();
        LocalDate birthDate = librarian.getBirthDate();
        String personAddress = librarian.getAddress();
        String phoneNumber = librarian.getPhoneNumber();
        String email = librarian.getEmail();
        int personRole = 2;
        String username = librarian.getUsername();
        String personPassword = librarian.getPassword();

        String query = "insert into person (firstName, lastName, birthDate, personAddress, phoneNumber, email, personRole, username,personPassword) values (";
        query += "'" + firstName + "', ";
        query += "'" + lastName + "', ";
        query += "'" + birthDate + "', ";
        query += "'" + personAddress + "', ";
        query += "'" + phoneNumber + "', ";
        query += "'" + email + "', ";
        query += "'" + personRole + "', ";
        query += "'" + username + "', ";
        query += "'" + personPassword + "');";

        try {
            System.out.println("test");
            boolean resultSet = this.statement.execute(query);
            //System.out.println("ro.mta.libraryproject.Persons.Librarian: " + username + " was added to the database!");
            return !resultSet;
        } catch (SQLException e) {
//            System.out.println("DB Query Status: Couldn't execute query!");
//            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used to add a registered user in the database
     *
     * @param registeredUser Instance that contains the registeredUser's data
     * @return Returns true if the query was successfully executed, false if not
     */
    public boolean addUser(RegisteredUser registeredUser) {
        String firstName = registeredUser.getFirstName();
        String lastName = registeredUser.getLastName();
        LocalDate birthDate = registeredUser.getBirthDate();
        String personAddress = registeredUser.getAddress();
        String phoneNumber = registeredUser.getPhoneNumber();
        String email = registeredUser.getEmail();
        int personRole = 3;
        String username = registeredUser.getUsername();
        String personPassword = registeredUser.getPassword();

        String query = "insert into person (firstName, lastName, birthDate, personAddress, phoneNumber, email, personRole, username,personPassword) values (";
        query += "'" + firstName + "', ";
        query += "'" + lastName + "', ";
        query += "'" + birthDate + "', ";
        query += "'" + personAddress + "', ";
        query += "'" + phoneNumber + "', ";
        query += "'" + email + "', ";
        query += "'" + personRole + "', ";
        query += "'" + username + "', ";
        query += "'" + personPassword + "');";

        try {
            boolean resultSet = this.statement.execute(query);

            //System.out.println("Registered user: " + username + " was added to the database!");
            return !resultSet;
        } catch (SQLException e) {
//            System.out.println("DB Query Status: Couldn't execute query!");
//            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param newArchive
     * @return
     */
    public boolean addArchive(Archive newArchive) {
        String archiveUser = newArchive.getUser();
        boolean readAccess = newArchive.getReadAccess();
        LocalDate expirationDate = newArchive.getExpirationDate();
        LocalDate now_date = LocalDate.now();

        if (now_date.isAfter(expirationDate))
            return false;

        String query = "insert into archive (readAccess, archiveUser, expirationDate) values (";
        query += "'" + readAccess + "', ";
        query += "'" + archiveUser + "', ";
        query += "'" + expirationDate + "');";


        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @param title
     * @param author
     * @return
     */
    public boolean getBook(String title, String author) {
        String query = "SELECT * FROM book ";
        query += " WHERE title = ";
        query += "'" + title + "' and author = ";
        query += "'" + author + "';";
        try {
            boolean resultSet = this.statement.execute(query);
            //System.out.println("ro.mta.libraryproject.Library.Book: " + title + " is in the database!");
            return !resultSet;
        } catch (SQLException e) {
            // System.out.println("DB Query Status: Couldn't execute query!");
            // System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean getBorrowBook(int bookID, int userID) {
        String query = "SELECT lendings.ID, book.ID, person.ID  FROM (( lendings ";
        query += " INNER JOIN  book ON lendings.book = book.ID )";
        query += " INNER JOIN person ON lendings.lendingUser = person.ID ) ";
        query += "WHERE book.ID= '" + bookID + "' AND person.ID= ";
        query += "'" + userID + "';";
        try {
            boolean resultSet = this.statement.execute(query);
            //  System.out.println("ro.mta.libraryproject.Library.Book: " + bookID + " is in the database!");
            return resultSet;
        } catch (SQLException e) {
            // System.out.println("DB Query Status: Couldn't execute query!");
            // System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param isbn
     * @param title1
     * @param author1
     * @param genre
     * @param numberOfPages
     * @param bookStatus
     * @param shelfID
     * @param nrBooks
     * @param title
     * @param author
     * @return
     */
    public boolean editBook(String isbn, String title1, String author1, String genre, String numberOfPages, String bookStatus, String shelfID, String nrBooks, String title, String author) {
        String query = "UPDATE book " + "SET ";
        if (!(isbn.isEmpty())) {
            query += " isbn = ' " + isbn + "' ";
        }
        if (!(title1.isEmpty())) {
            query += ", title = ' " + title1 + "' ";
        }
        if (!(author1.isEmpty())) {
            query += ", author = ' " + author1 + "' ";
        }
        if (!(genre.isEmpty())) {
            query += ", genre = ' " + genre + "' ";
        }
        if (!(numberOfPages.isEmpty())) {
            query += ", numberOfPages = ' " + numberOfPages + "' ";
        }
        if (!(bookStatus.isEmpty())) {
            query += ", bookStatus = ' " + bookStatus + "' ";
        }
        if (!(shelfID.isEmpty())) {
            query += ", shelfID = ' " + shelfID + "' ";
        }
        if (!(nrBooks.isEmpty())) {
            query += ", nrBooks = ' " + nrBooks + "' ";
        }
        query += "WHERE title= '" + title + "' and author = '" + author + "';";

        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Function that is used to edit user data
     *
     * @param editedUser Instance that contains the registeredUser's new data
     * @param ID         registeredUser's ID
     * @return Returns true if the query was succesfully executed, false if not
     */
    public boolean editUser(RegisteredUser editedUser, String ID) {
        String firstname = editedUser.getFirstName();
        String lastname = editedUser.getLastName();
        LocalDate birthDate = editedUser.getBirthDate();
        String personAddress = editedUser.getAddress();
        String phoneNumber = editedUser.getPhoneNumber();
        String email = editedUser.getEmail();
        String username = editedUser.getUsername();
        String personPassword = editedUser.getPassword();

        boolean resultSet;
        String query;
        try {
            if (firstname.length() != 0) {
                query = "update person set firstName=";
                query += "'" + firstname + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (lastname.length() != 0) {
                query = "update person set lastName=";
                query += "'" + lastname + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (birthDate != null) {
                query = "update person set birthDate=";
                query += "'" + birthDate + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (personAddress.length() != 0) {
                query = "update person set personAddress=";
                query += "'" + personAddress + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (phoneNumber.length() != 0) {
                query = "update person set phoneNumber=";
                query += "'" + phoneNumber + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (email.length() != 0) {
                query = "update person set email=";
                query += "'" + email + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (username.length() != 0) {
                query = "update person set username=";
                query += "'" + username + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
            if (personPassword.length() != 0) {
                query = "update person set personPassword=";
                query += "'" + personPassword + "' where person.ID = ";
                query += "'" + ID + "';";

                resultSet = this.statement.execute(query);
                if (resultSet) {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Function that is used to delete a book
     *
     * @param title  The title of the book
     * @param author The author of the book
     * @return Returns true if the query was succesfully executed, false if not
     */
    public boolean deleteBook(String title, String author) {
        String queryExists = "select * from book where book.title=";
        queryExists += "'" + title + "' and book.author=";
        queryExists += "'" + author + "';";

        int count = 0;
        try {
            ResultSet resultSet = this.statement.executeQuery(queryExists);
            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            return false;
        }
        System.out.println(count);
        if (count > 0) {
            try {
                String query = "delete from book where book.title=";
                query += "'" + title + "' and book.author=";
                query += "'" + author + "';";
                boolean resultSetDelete = this.statement.execute(query);
                System.out.println(resultSetDelete);
                return !resultSetDelete;
            } catch (SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param book
     * @return Returns true if the query was succesfully executed, false if not
     */
    public boolean addBook(Book book) {
        String isbn = book.getISBN();
        String title = book.getTitle();
        String authors = book.getAuthors();
        String genre = book.getGenre();
        String status = book.getStatus();
        int shelfID = book.getShelfId();
        int numBooks = 1;
        int numPages = book.getNumberOfPages();

        String query = "insert into book (isbn, title, author, genre, bookStatus, shelfID, nrBooks, numberOfPages) values (";
        query += "'" + isbn + "', ";
        query += "'" + title + "', ";
        query += "'" + authors + "', ";
        query += "'" + genre + "', ";
        query += "'" + status + "', ";
        query += shelfID + ", ";
        query += numBooks + ", ";
        query += numPages + ");";

        try {
            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
//            System.out.println("DB Query Status: Couldn't execute query!");
//            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param title
     * @param author
     * @return Returns true if the query was succesfully executed, false if not
     */
    public int searchBook(String title, String author) {
        String query = "SELECT ID FROM book ";
        query += " WHERE title = ";
        query += "'" + title + "' and author = ";
        query += "'" + author + "';";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            if (count == 1) {
                ResultSet resultSet2 = this.statement.executeQuery(query);
                resultSet2.next();

                return resultSet2.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }

    }

    /**
     * Function that return data about a book
     *
     * @param ISBN
     * @return Returns a string with information about a book
     */
    public String searchISBNBorrowBook(String ISBN) {
        String buffer = "";
        String query = "SELECT lendings.ID, person.username, lendings.lendingDate, lendings.returnDate ";
        query += "FROM lendings ";
        query += "INNER JOIN person ON person.ID = lendings.lendingsUser ";
        query += "INNER JOIN book ON book.ID = lendings.book ";
        query += " WHERE ISBN = ";
        query += "'" + ISBN + "';";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String username = resultSet.getString(2);
                String lendingDate = resultSet.getString(3);
                String returnDate = resultSet.getString(4);
                buffer += ID + "%" + username + "%" + ISBN + "%" + lendingDate + "%" + returnDate + "~";
            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();
            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * @param clientName
     * @return Returns true if the query was succesfully executed, false if not
     */
    public int searchUser(String clientName) {
        String query = "SELECT ID FROM person ";
        query += " WHERE username = ";
        query += "'" + clientName + "';";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            if (count == 1) {
                ResultSet resultSet2 = this.statement.executeQuery(query);
                resultSet2.next();

                return resultSet2.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * @param bookID
     * @param userID
     * @return Returns true if the query was succesfully executed, false if not
     */
    public boolean borrowBook(int bookID, int userID) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 14);


        java.util.Date utilStartDate = c.getTime();
        java.sql.Date returnDate = new java.sql.Date(utilStartDate.getTime());
        //  java.sql.Date returnDate = ( java.sql.Date) c.getTime();

        int nbBooks;
        int nbBorrowedBooks;

        try {
            ResultSet rs = this.statement.executeQuery(String.format("SELECT nrBooks FROM books WHERE ID = %d", bookID));
            rs.next();
            nbBooks = rs.getInt("nrBooks");

            rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM lendings WHERE book = %d", bookID));
            rs.next();
            nbBorrowedBooks = rs.getInt(0);

            if (nbBorrowedBooks >= nbBooks)
                return false;

        } catch (SQLException e) {
            return false;
        }

        String query = "insert into lendings ( lendingUser, book, lendingDate, returnDate) values (";
        query += "'" + userID + "', ";
        query += "'" + bookID + "', ";
        query += "'" + date + "', ";
        query += "'" + returnDate + "');";

        try {

            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            System.out.println("DB Query Status: Couldn't execute query!");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that return date of an user
     *
     * @param ID of the user
     * @return Returns RegisterUser data
     */
    public RegisteredUser selectUserData(int ID) {
        String query = "SELECT * FROM person ";
        query += " WHERE ID = ";
        query += "'" + ID + "';";


        try {
            this.statement = connection.createStatement();
            ResultSet resultSet = this.statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            if (count != 0) {
                ResultSet resultSet2 = this.statement.executeQuery(query);
                resultSet2.next();

                String firstname = resultSet2.getString(2);
                String lastname = resultSet2.getString(3);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String birthday = resultSet2.getDate(4).toString();

                String address = resultSet2.getString(5);
                String phone = resultSet2.getString(6);
                String email = resultSet2.getString(7);
                String username = resultSet2.getString(9);
                String password = resultSet2.getString(10);

                RegisteredUser user = new RegisteredUser(firstname, lastname, LocalDate.parse(birthday, formatter), address, phone, email, username, password);
                return user;
            } else {
                RegisteredUser user = new RegisteredUser("x", "x");
                return user;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Function that return borrow books
     *
     * @param idUser of the user
     * @return Returns getBorrowedBook data
     */
    public String getBorrowedBook(int idUser) {
        String buffer = "";
        String query = "SELECT book.isbn, book.title, book.author, book.genre, lendings.lendingDate, lendings.returnDate  ";
        query += "FROM lendings ";
        query += "INNER JOIN book ON lendings.book=book.ID ";
        query += "INNER JOIN person ON lendings.lendingsUser=person.ID ";
        query += " WHERE person.ID = ";
        query += "'" + idUser + "'  ;";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String isbn = resultSet.getString(1);
                String title = resultSet.getString(2);
                String author = resultSet.getString(3);
                String genre = resultSet.getString(4);
                String lending = resultSet.getString(5);
                String ret = resultSet.getString(4);

                buffer += isbn + "%" + title + "%" + author + "%" + genre + "%" + lending + "%" + ret + "~";
            }

            return buffer;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public boolean changeInformation(RegisteredUser editedUser, int userID) {
        String firstname = editedUser.getFirstName();
        String lastname = editedUser.getLastName();
        LocalDate birthDate = editedUser.getBirthDate();
        String personAddress = editedUser.getAddress();
        String phoneNumber = editedUser.getPhoneNumber();
        String email = editedUser.getEmail();
        String username = editedUser.getUsername();
        String personPassword = editedUser.getPassword();
        int count = 0;
        String query = "UPDATE person " + "SET ";
        if (!(firstname.isEmpty())) {
            query += " firstName = ' " + firstname + "' ";
            count++;
        }
        if (!(lastname.isEmpty())) {
            if (count > 0) {
                query += ", lastName = ' " + lastname + "' ";
                count++;
            } else {
                query += " lastName = ' " + lastname + "' ";
                count++;
            }
        }
        if ((birthDate) != null) {
            if (count > 0) {
                query += ", birthDate = ' " + birthDate + "' ";
                count++;
            } else {
                query += " birthDate = ' " + birthDate + "' ";
                count++;
            }
        }
        if (!(personAddress.isEmpty())) {
            if (count > 0) {
                query += ", personAddress = ' " + personAddress + "' ";
                count++;
            } else {
                query += " personAddress = ' " + personAddress + "' ";
                count++;
            }
        }
        if (!(phoneNumber.isEmpty())) {
            if (count > 0) {
                query += ", phoneNumber = ' " + phoneNumber + "' ";
                count++;
            } else {
                query += " phoneNumber = ' " + phoneNumber + "' ";
                count++;
            }
        }
        if (!(email.isEmpty())) {
            if (count > 0) {
                query += ", email = ' " + email + "' ";
                count++;
            } else {
                query += " email = ' " + email + "' ";
                count++;
            }
        }
        if (!(username.isEmpty())) {
            if (count > 0) {
                query += ", username = ' " + username + "' ";
                count++;
            } else {
                query += " username = ' " + username + "' ";
                count++;
            }
        }
        if (!(personPassword.isEmpty())) {
            if (count > 0) {
                query += ", personPassword = ' " + personPassword + "' ";
                count++;
            } else {
                query += " personPassword = ' " + personPassword + "' ";
                count++;
            }
        }
        query += "WHERE ID= '" + userID + "';";

        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used display data of the users - for admin
     *
     * @return Returns a string containing the data of the users
     */
    public String adminViewPersonDetails() {
        String buffer = "";
        String query = "SELECT person.ID, firstName, lastName, birthDate, personAddress, phoneNumber, email, role.roleName, username ";
        query += "FROM person ";
        query += "INNER JOIN role ON role.ID = person.personRole;";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String birthDate = resultSet.getString(4);
                String address = resultSet.getString(5);
                String phone = resultSet.getString(6);
                String email = resultSet.getString(7);
                String role = resultSet.getString(8);
                String username = resultSet.getString(9);
                buffer += ID + "%" + firstName + "%" + lastName + "%" + birthDate + "%" + address + "%" + phone + "%" + email + "%" + role + "%" + username + "~";
            }

            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    /**
     * Function that is used display data of the users - for librarian
     *
     * @return Returns a string containing the data of the registered users
     */
    public String librarianViewPersonDetails() {
        String buffer = "";
        String query = "SELECT person.ID, firstName, lastName, birthDate, personAddress, phoneNumber, email, role.roleName, username ";
        query += "FROM person ";
        query += "INNER JOIN role ON role.ID = person.personRole ";
        query += "where role.roleName = 'registeredUser';";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String birthDate = resultSet.getString(4);
                String address = resultSet.getString(5);
                String phone = resultSet.getString(6);
                String email = resultSet.getString(7);
                String role = resultSet.getString(8);
                String username = resultSet.getString(9);
                buffer += ID + "%" + firstName + "%" + lastName + "%" + birthDate + address + "%" + phone + "%" + email + "%" + role + "%" + username + "~";
            }

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean requestBorrow(int userID, int bookID) {
        String query = "insert into bookRequests ( idUser, idBook, requestType, requestStatus, requestTimestamp) values (";
        query += "'" + userID + "', ";
        query += "'" + bookID + "', ";
        String type = "borrow";
        query += "'" + type + "', ";
        String status = "unapproved";
        query += "'" + status + "',";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        query += "'" + formatter.format(date) + "');";

        try {

            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean requestArchive(int archiveID, int userID) {

        String query = "insert into archiveRequests ( idUser, idArchive, requestType, requestStatus, requestTimestamp) values (";
        query += "'" + userID + "', ";
        query += "'" + userID + "', ";
        String type = "add";
        query += "'" + type + "', ";
        String status = "unapproved";
        query += "'" + status + "',";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        query += "'" + formatter.format(date) + "');";

        try {

            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int searchArchive(int userID) {
        String query = "SELECT ID FROM archive ";
        query += " WHERE archiveUser = ";
        query += "'" + userID + "';";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            if (count == 1) {
                ResultSet resultSet2 = this.statement.executeQuery(query);
                resultSet2.next();

                return resultSet2.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Function that is used display borrowed books - for librarian
     *
     * @return Returns a string containing details about borrowed books
     */
    public String viewBorrowedBooks() {
        String buffer = "";
        String query = "SELECT lendings.ID, person.username, book.isbn, lendings.lendingDate, lendings.returnDate ";
        query += "FROM lendings ";
        query += "INNER JOIN person ON person.ID = lendings.lendingsUser ";
        query += "INNER JOIN book ON book.ID = lendings.book;";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String username = resultSet.getString(2);
                String ISBN = resultSet.getString(3);
                String lendingDate = resultSet.getString(4);
                String returnDate = resultSet.getString(5);
                buffer += ID + "%" + username + "%" + ISBN + "%" + lendingDate + "%" + returnDate + "~";
            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();
            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Function that is used to make a reserve book request
     *
     * @param userID is the ID of the user
     * @param bookID is the ID of the book
     * @return Returns a true if reservation completed succesfully, false if not
     */
    public boolean reserveRequest(int userID, int bookID) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        String query = "insert into bookRequests(idUser, idBook, requestType, requestStatus, requestTimestamp)";
        query += "values (" + userID + ", " + bookID + ", 'reserve', 'unapproved', '" + formatter.format(date) + "')";

        try {
            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used display unapproved requests - for librarian
     *
     * @param option is the object for which we want to handle the request
     * @return Returns a string containing details about the requests that are still pending
     */
    public String unapprovedRequests(String option) {
        String buffer = "";
        String query = "";

        if (option.equals("books")) {
            query += "select bookRequests.ID, person.username, book.title, book.author, bookRequests.requestTimestamp from bookRequests ";
            query += "inner join person on person.ID = bookRequests.idUser ";
            query += "inner join book on book.ID = bookRequests.idBook ";
            query += "where bookRequests.requestStatus = 'unapproved';";
        }
        if (option.equals("archive")) {
            query += "select archiveRequests.ID, person.username, archive.ID as archiveID, archiveRequests.requestStatus, archiveRequests.requestTimestamp from archiveRequests ";
            query += "inner join person on person.ID = archiveRequests.idUser ";
            query += "inner join archive on archive.ID = archiveRequests.idArchive ";
            query += "where archiveRequests.requestStatus = 'unapproved';";
        }

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String username = resultSet.getString(2);
                String optionID = resultSet.getString(3);
                String status = resultSet.getString(4);
                String timestamp = resultSet.getString(5);
                buffer += ID + "\t" + username + "\t" + optionID + "\t" + status + "\t" + timestamp + "~";
            }

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Function that is used to make a return book request
     *
     * @param userID is the ID of the user
     * @param bookID is the ID of the book
     * @return Returns a true if return request completed succesfully, false if not
     */
    public boolean returnRequest(int userID, int bookID) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        String query = "insert into bookRequests(idUser, idBook, requestType, requestStatus, requestTimestamp)";
        query += "values (" + userID + ", " + bookID + ", 'return', 'unapproved', '" + formatter.format(date) + "')";

        try {
            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used to retrun the type of request
     *
     * @param option is the object for which we want to handle the request
     * @param ID     is the ID of the request
     * @return Returns a borrow, reserve, delete if option is "Book" or add, delete if option is "Archive"
     */
    public String getRequestType(String option, String ID) {
        String buffer = "";
        String query = "";

        if (option.equals("Book")) {
            query += "select bookRequests.requestType from bookRequests  ";
            query += "where bookRequests.ID = '" + ID + "';";
        }
        if (option.equals("Archive")) {
            query += "select archiveRequests.requestType from archiveRequests  ";
            query += "where archiveRequests.ID = '" + ID + "';";
        }

        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            while (resultSet.next()) {
                String type = resultSet.getString(1);
                buffer += type;
            }
            return buffer;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return option;
        }
    }

    /**
     * Function that is used to retrun the type of request
     * @return Returns a borrow, reserve, delete if option is "Book" or add, delete if option is "Archive"

    public boolean HandleBorrowBook(String ID) {
    String query = "";

    query += "select idUser, idBook from bookRequests ";
    query += "where bookRequests.ID = '" + ID + "';";

    try {
    ResultSet resultSet = this.statement.executeQuery(query);
    while (resultSet.next()) {
    int idUser = resultSet.getInt(1);
    int idBook = resultSet.getInt(2);

    borrowBook(idUser, idBook);

    }

    return true;
    }
    catch (SQLException e) {
    System.out.println(e.getMessage());
    return false;
    }
    }*/

    /**
     * Function that is used to update the status of a request to "approved"
     *
     * @param ID     is the ID of the request
     * @param option is the object for which we want to handle the request
     * @return Returns a true if return update completed succesfully, false if not
     */
    public boolean approveRequest(String ID, String option) {

        String query = "";
        if (option.equals("archive")) {
            query += "update archiveRequests set archiveRequests.requestStatus='approved' ";
            query += "where archiveRequests.ID = " + ID + ";";
        }
        if (option.equals("book")) {
            query += "update bookRequests set bookRequests.requestStatus='approved' ";
            query += "where bookRequests.ID = " + ID + ";";
        }
        try {
            this.statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean denyRequest(String ID, String option) {

        String query = "";
        if (option.equals("archive")) {
            query += "update archiveRequests set archiveRequests.requestStatus='denied' ";
            query += "where archiveRequests.ID = " + ID + ";";
        }
        if (option.equals("book")) {
            query += "update bookRequests set bookRequests.requestStatus='denied' ";
            query += "where bookRequests.ID = " + ID + ";";
        }
        try {
            this.statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used to return archiveID from table archiveRequests based on the request ID
     *
     * @param ID is the ID of the request
     * @return Returns archiveID
     */
    public String getArchiveID(String ID) {

        String query = "select archiveRequests.idArchive from archiveRequests ";
        query += "where archiveRequests.ID = " + ID + ";";
        String idArchive = "";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            while (resultSet.next()) {
                idArchive = resultSet.getString(1);
            }
            return idArchive;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Function that is used to return archiveID from table archiveRequests based on the request ID
     *
     * @param ID is the ID of the archive to be deleted
     * @return Returns true if delete request completed succesfully, false if not
     */
    public boolean HandleDeleteArchive(String ID) {

        String query = "delete from archive ";
        query += "where archive.ID = '" + ID + "' ;";
        try {
            this.statement.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used to return bookID from table bookRequests based on the request ID
     *
     * @param ID is the ID of the request
     * @return Returns bookID
     */
    public String getBookID(String ID) {

        String query = "select bookRequests.idBook from bookRequests ";
        query += "where bookRequests.ID = " + ID + ";";
        String idBook = "";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            while (resultSet.next()) {
                idBook = resultSet.getString(1);
            }
            return idBook;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Function that is used update the number of bookes (for reserve, return and borrow request)
     *
     * @param ID    is the ID of the bookID
     * @param value is the value used to modify the value of number of books
     * @return Returns true if update completed succesfully, false if not
     */
    public boolean updateNumberOfBooks(String ID, int value) {
        String query = "";
        query += "update book set book.nrBooks = book.nrBooks +" + value + " ";
        query += "where book.ID = " + ID + ";";

        try {
            this.statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used to return bookID from table bookRequests based on the request ID
     *
     * @param ID is the ID of the request
     * @return Returns userID
     */
    public String getUserID(String ID) {

        String query = "select bookRequests.idUser from bookRequests ";
        query += "where bookRequests.ID = " + ID + ";";
        String idUser = "";
        try {
            ResultSet resultSet = this.statement.executeQuery(query);
            while (resultSet.next()) {
                idUser = resultSet.getString(1);
            }
            return idUser;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Function that is used to update the return date of a book
     *
     * @param userID is the ID of the ID of the user
     * @param bookID is the ID of the ID of the book
     * @return Returns true if return request completed succesfully, false if not
     */
    public boolean HandleReturnBook(String userID, String bookID) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        String query = "";
        query += "update lendings set lendings.returnDate = '" + formatter.format(date) + "'";
        query += "where lendingsUser = " + userID + " and" + " book = " + bookID + ";";

        try {
            this.statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used to handle a borrow request
     *
     * @param userID is the ID of the ID of the user
     * @param bookID is the ID of the ID of the book
     * @return Returns true if return request completed succesfully, false if not
     */
    public boolean HandleBorrowBook(String userID, String bookID) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Date returnDate = new Date(System.currentTimeMillis() + 1209600000);

        String query = "";
        query += "insert into lendings(lendingsUser, book, lendingDate, returnDate)";
        query += "values (" + userID + "," + bookID + ", '" + formatter.format(date) + "', '" + returnDate + "')";

        try {
            this.statement.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String verifyStatus(String borrow, int userID, int bookID) {
        String buffer = "";
        String query = "SELECT bookRequests.ID , bookRequests.idUser, bookRequests.idBook ";
        query += "FROM bookRequests ";
        query += " WHERE bookRequests.requestStatus = 'approved' and bookRequests.idUser= ";
        query += "'" + userID + "' and bookRequests.idBook= ";
        query += "'" + bookID + "'  and  bookRequests.requestType= ";
        query += "'" + borrow + "'  ;";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String userId = resultSet.getString(2);
                String bookId = resultSet.getString(3);
                buffer += ID + " " + username + " " + userId + " " + bookId + "~";
            }


            return buffer;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String verifyStatusArchive(String add, int archiveID, int userID) {
        String buffer = "";
        String query = "SELECT archiveRequests.ID , archiveRequests.idUser, archiveRequests.idArchive ";
        query += "FROM archiveRequests ";
        query += " WHERE archiveRequests.requestStatus = 'approved' and archiveRequests.idUser= ";
        query += "'" + userID + "' and archiveRequests.idArchive= ";
        query += "'" + archiveID + "'  and  bookRequests.requestType= ";
        query += "'" + add + "'  ;";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String userId = resultSet.getString(2);
                String bookId = resultSet.getString(3);
                buffer += ID + " " + userId + " " + bookId + "~";
            }

            return buffer;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String viewUserRequests(String username, String option) {

        String query = "";
        String buffer = "";
        if (option.equals("books")) {
            query += "select bookRequests.ID, person.username, book.title, book.author, bookRequests.requestTimestamp from bookRequests ";
            query += "inner join person on person.ID = bookRequests.idUser ";
            query += "inner join book on book.ID = bookRequests.idBook ";
            query += "where person.username = '" + username.trim() + "';";
        }
        if (option.equals("archive")) {
            query += "select archiveRequests.ID, person.username, archive.ID as archiveID, archiveRequests.requestStatus, archiveRequests.requestTimestamp from archiveRequests ";
            query += "inner join person on person.ID = archiveRequests.idUser ";
            query += "inner join archive on archive.ID = archiveRequests.idArchive ";
            query += "where person.username = '" + username.trim() + "';";
        }

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String user = resultSet.getString(2);
                String optionID = resultSet.getString(3);
                String status = resultSet.getString(4);
                String timestamp = resultSet.getString(5);
                buffer += ID + " " + user + " " + optionID + " " + status + " " + timestamp + "~";
            }

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Function that is used for: display user virtual books - for user
     *
     * @return Returns a string containing information needed
     *   
     */
    public String viewMyVirtualBooks(int userId) {
        String buffer = "";
        String query = "SELECT virtualBooksBought.ID, virtualBooksBought.isbn, virtualBooksBought.title, virtualBooksBought.author, virtualBooksBought.genre, virtualBooksBought.numberOfPages, virtualBooksBought.accessKey ";
        query += "FROM virtualBooksBought";
        query += " INNER JOIN person ON person.ID = '";
        query += userId + "';";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String title = resultSet.getString(3);
                String authors = resultSet.getString(4);
                String genre = resultSet.getString(5);
                String numberOfPages = resultSet.getString(6);
                String accessKey = resultSet.getString(7);
                buffer += ID + "%" + ISBN + "%" + title + "%" + authors + "%" + genre + "%" + numberOfPages + "%" + accessKey + "~";
            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    /**
     * Function that is used for: display all virtual books - for librarian
     *
     * @return Returns a string containing information needed
     *   
     */
    public String viewVirtualBooks() {
        String buffer = "";
        String query = "SELECT virtualBooks.ID, virtualBooks.isbn, virtualBooks.title, virtualBooks.author, virtualBooks.genre, virtualBooks.numberOfPages, virtualBooks.virtualKey ";
        query += "FROM virtualBooks";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String title = resultSet.getString(3);
                String authors = resultSet.getString(4);
                String genre = resultSet.getString(5);
                String numberOfPages = resultSet.getString(6);
                String virtualKey = resultSet.getString(7);
                buffer += ID + "%" + ISBN + "%" + title + "%" + authors + "%" + genre + "%" + numberOfPages + "%" + virtualKey + "~";
            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Function that is used for: display all virtual books - for user
     *
     * @return Returns a string containing information needed
     *   
     */
    public String viewVirtualBooksForUser() {
        String buffer = "";
        String query = "SELECT virtualBooks.ID, virtualBooks.isbn, virtualBooks.title, virtualBooks.author, virtualBooks.genre, virtualBooks.numberOfPages ";
        query += "FROM virtualBooks";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String title = resultSet.getString(3);
                String authors = resultSet.getString(4);
                String genre = resultSet.getString(5);
                String numberOfPages = resultSet.getString(6);
                buffer += ID + "%" + ISBN + "%" + title + "%" + authors + "%" + genre + "%" + numberOfPages + "~";
            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Function that is used for: buy virtual book
     *
     * @return Returns true or false
     *   
     */
    public boolean buyVirtualBook(String userID, String isbn, String title, String authors, String genre, String pages, String accessKey) {
        String query = "insert into virtualBooksBought (isbn, userID, title, author, genre, numberOfPages, accessKey) values (";
        query += "'" + isbn + "', ";
        query += "'" + userID + "', ";
        query += "'" + title + "', ";
        query += "'" + authors + "', ";
        query += "'" + genre + "', ";
        query += "'" + pages + "', ";
        query += "'" + accessKey + "');";

        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Function that is used for: add virtual book librarian
     *
     * @return Returns true or false
     *   
     */
    public boolean addVirtualBook(String isbn, String title, String authors, String genre, String pages, String virtualKey) {
        String query = "insert into virtualBooks (isbn, title, author, genre, numberOfPages, virtualKey) values (";
        query += "'" + isbn + "', ";
        query += "'" + title + "', ";
        query += "'" + authors + "', ";
        query += "'" + genre + "', ";
        query += "'" + pages + "', ";
        query += "'" + virtualKey + "');";

        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Function that is used for: edit virtual book librarian
     *
     * @return Returns true or false
     *   
     */
    public boolean editVirtualBook(String id, String isbn, String title, String authors, String genre, String pages) {
        String query = "UPDATE virtualBooks " + "SET ";
        query += "isbn = '" + isbn + "', ";
        query += "title = '" + title + "', ";
        query += "author = '" + authors + "', ";
        query += "genre = '" + genre + "', ";
        query += "numberOfPages = '" + pages + "' ";
        query += "WHERE id = '" + id + "';";

        try {
            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Function that is used for: delete virtual book librarian
     *
     * @return Returns true or false
     *   
     */
    public boolean deleteVirtualBook(String isbn) {
        String query = "DELETE from virtualBooks ";
        query += "WHERE virtualBooks.isbn like " + isbn + ";";

        System.out.println(isbn);

        String secondQuery = "DELETE from virtualBooksBought ";
        secondQuery += "WHERE virtualBooksBought.isbn like " + isbn + ";";

        try {
            boolean resultSet = this.statement.execute(query);
            this.statement.execute(secondQuery);
            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Function that is used for: verify is selected book can be read using accessKey
     *
     * @return Returns buffer or null
     *   
     */
    public String selectViewVirtualBook(String isbn, String accessKey) {
        String query = "SELECT virtualBooks.virtualKey FROM virtualBooks ";
        query += "WHERE virtualBooks.isbn like '" + isbn + "';";

        String buffer = "";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String key = resultSet.getString(1);
                buffer += key;
            }
            if (Utils.verifyHash(buffer, accessKey) == true) {
                return "yes";
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Function that is used for: get virtual key for book
     *
     * @return Returns a string containing information needed
     *   
     */
    public String getVirtualKey(String virtualBookId) {
        String buffer = "";
        String query = "SELECT virtualBooks.virtualKey ";
        query += "FROM virtualBooks ";
        query += "WHERE virtualBooks.ID = '" + virtualBookId + "';";

        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                buffer += ID + "~";
            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    public String librarianViewArchives() {
        String buffer = "";
        String query = "SELECT archive.ID, readAccess, archiveUser, expirationDate ";
        query += "FROM archive";


        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String readAcces = resultSet.getString(2);
                String userId = resultSet.getString(3);
                String expDate = resultSet.getString(4);

                //  buffer += "ID:"+ID + " " +"readAcces:"+ readAcces + " "+"userId:" + userId + " " +"Date:"+ expDate;
                buffer += ID + "%" + readAcces + "%" + userId + "%" + expDate + "~";

            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    public String ViewPrivateArchives() {
        String buffer = "";
        String query = "SELECT archive.ID, readAccess, archiveUser, expirationDate ";
        query += "FROM archive";
        query += "WHERE readAccess=1";


        try {
            ResultSet resultSet = this.statement.executeQuery(query);

            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                String readAcces = resultSet.getString(2);
                String userId = resultSet.getString(3);
                String expDate = resultSet.getString(4);

                //  buffer += "ID:"+ID + " " +"readAcces:"+ readAcces + " "+"userId:" + userId + " " +"Date:"+ expDate;
                buffer += ID + "%" + readAcces + "%" + userId + "%" + expDate + "~";

            }
            StringBuffer sb = new StringBuffer(buffer);
            sb.deleteCharAt(sb.length() - 1);
            buffer = sb.toString();

            return buffer;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean delBook(String isbn) {
        String query = "DELETE from book ";
        query += "WHERE book.isbn like " + isbn + ";";

        System.out.println(isbn);

        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addBooklib(String isbn, String title, String status, String authors, String genre, String books, String pages, String shelfId) {
        String query = "insert into book (isbn, title, author, genre, numberOfPages, bookStatus, shelfID, nrBooks) values (";
        query += "'" + isbn + "', ";
        query += "'" + title + "', ";
        query += "'" + authors + "', ";
        query += "'" + genre + "', ";
        query += "'" + pages + "', ";
        query += "'" + status + "', ";
        query += "'" + shelfId + "', ";
        query += "'" + books + "');";

        try {
            boolean resultSet = this.statement.execute(query);

            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean editBooklib(String isbn, String title, String status, String author, String genre, String numberOfBooks, String numberOfPages, String shelfID) {
        String query = "UPDATE book " + "SET ";
        query += "isbn = '" + isbn + "', ";
        query += "title = '" + title + "', ";
        query += "author = '" + author + "', ";
        query += "genre = '" + genre + "', ";
        query += "numberOfPages = '" + numberOfPages + "' ";
        query += "bookStatus = '" + status + "' ";
        query += "shelfID = '" + shelfID + "' ";
        query += "nrBooks = '" + numberOfBooks + "' ";
        query += "WHERE isbn = '" + isbn + "';";

        try {
            boolean resultSet = this.statement.execute(query);
            return !resultSet;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     *    
     * Function to update user information based on a String array
     * @param s
     * @param username
     * @return true if successful else returns false
     */
    public boolean updateUserInfo(String[] s, String username) {
        String q = "update person set firstName='" + s[0] + "', lastName='" + s[1]
                + "', birthDate='" + s[2] + "', personAddress='" + s[3]
                + "', phoneNumber='" + s[4] + "', email='" + s[5]
                + "', username='" + s[6] + "'";
        if (s.length == 8)
            q += ", personPassword='" + s[7] + "' ";
        q += "where username='" + username + "';";
        try {
            this.statement.executeUpdate(q);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *    
     * Function to get user information as a tab formatted String
     * @param username
     * @return user information if successful or null
     */
    public String getUserInfo(String username) {
        String b = "";
        String q = "select * from person where username='" + username + "';";
        try {
            ResultSet resultSet = this.statement.executeQuery(q);
            while (resultSet.next()) {
                b += resultSet.getString(2) + "\t" + resultSet.getString(3)
                        + "\t" + resultSet.getString(4) + "\t" + resultSet.getString(5)
                        + "\t" + resultSet.getString(6) + "\t" + resultSet.getString(7)
                        + "\t" + resultSet.getString(9);
            }
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}



