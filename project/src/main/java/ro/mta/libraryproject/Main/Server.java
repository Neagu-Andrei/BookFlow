package ro.mta.libraryproject.Main;

import ro.mta.libraryproject.Library.Archive;
import ro.mta.libraryproject.Library.Book;
import ro.mta.libraryproject.Library.Library;
import ro.mta.libraryproject.Others.Utils;
import ro.mta.libraryproject.Persons.Librarian;
import ro.mta.libraryproject.Persons.RegisteredUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Anghel
 */
class Server {
    /**
     * @param hash
     * @return
     * @author Alexandru Alexandru
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String getRandomString() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return (sb.toString());
    }

    public static void runServer() {
        ServerSocket server = null;
        try {
            // server is listening on port 15808
            server = new ServerSocket(15808);
            server.setReuseAddress(true);
            // running infinite loop for getting
            // client request
            while (true) {
                // socket object to receive incoming client
                // requests
                Socket client = server.accept();
                // Displaying that new client is connected
                // to server
                System.out.println("New client connected "
                        + client.getInetAddress()
                        .getHostAddress());
                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);
                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        ManagerDB managerDB = new ManagerDB();
        Library library = new Library();
        private String clientName = "guest";
        private String clientPass = "";
        private boolean loggedIn = false;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    // writing the received message from
                    // client

                    System.out.printf(
                            " Sent from the %s: %s\n",
                            clientName, line);
                    if (line.equals("Login") && !loggedIn) {


                        if ((line = in.readLine()) != null) {
                            clientName = line;
                        }

                        if ((line = in.readLine()) != null) {
                            clientPass = line;
                        }
                        //TO DO: Function to check database for login
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hashPass = digest.digest(
                                clientPass.getBytes(StandardCharsets.UTF_8));
                        String role = managerDB.login(clientName, bytesToHex(hashPass));

                        if (role != null) {
                            loggedIn = true;
                            System.out.println(clientName + " logged in");
                            out.println("Successfully logged in");
                            out.println(role);
                        } else {
                            loggedIn = false;
                            clientName = "guest";
                            clientPass = "";
                            out.println("Wrong username and password!");
                            out.println("none");
                        }

                    }

                    if (line.equals("View books")) {
                        String buffer = library.viewLibraryBooks();
                        buffer += "-----------";
                        out.println(buffer);
                    }

                    if (line.equals("View all users") && loggedIn) {
                        String buffer = managerDB.adminViewPersonDetails();
                        out.println("*****List of users*****");
                        out.println(buffer);
                    }

                    if (line.equals("View books librarian") && loggedIn) {
                        System.out.println("Aici!!!");
                        String buffer = library.viewLibraryBooks();
                      //  buffer += "-----------";
                        out.println(buffer);
                      //  String line1=in.readLine();

                    }

                    if (line.equals("View all registered users") && loggedIn) {
                        String buffer = managerDB.librarianViewPersonDetails();
                        out.println("*****List of registered users*****");
                        out.println(buffer);
                    }

                    if (line.equals("Delete") && loggedIn) {
                        String title = in.readLine();
                        String author = in.readLine();

                        boolean status = managerDB.deleteBook(title, author);

                        out.println("*****Delete book*****");
                        if(status == true) {
                            out.println("Deleted!");
                        }
                        else {
                            out.println("Not deleted!");
                        }
                    }

                    if (line.equals("View my virtual books") && loggedIn) {
                        int userID = managerDB.searchUser(clientName);
                        String buffer = managerDB.viewMyVirtualBooks(userID);
                        out.println("*****List of my virtual books*****");
                        out.println(buffer);
                    }

                    if (line.equals("View virtual books") && loggedIn) {
                        String buffer = managerDB.viewVirtualBooks();
                        out.println("*****List of virtual books*****");
                        out.println(buffer);
                    }

                    if (line.equals("Buy virtual book") && loggedIn) {
                        String bookId = in.readLine();
                        String isbn = in.readLine();
                        String title = in.readLine();
                        String authors = in.readLine();
                        String genre = in.readLine();
                        String pages = in.readLine();
                        String key = managerDB.getVirtualKey(bookId);

                        int userID = managerDB.searchUser(clientName);
                        String ID = String.valueOf(userID);

                        byte[] salt = Utils.getRandomBytes(20);
                        String accessKey = Utils.getHash(key, salt);

                        boolean status = managerDB.buyVirtualBook(ID, isbn, title, authors, genre, pages, accessKey);

                        out.println("*****List of virtual books*****");
                        if (status == true) {
                            out.println("Added!");
                        } else {
                            out.println("Not Added!");
                        }
                    }

                    if (line.equals("Add virtual book") && loggedIn) {
                        String isbn = in.readLine();
                        String title = in.readLine();
                        String authors = in.readLine();
                        String genre = in.readLine();
                        String pages = in.readLine();

                        String randomString = getRandomString();

                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] keyHash = digest.digest(
                                randomString.getBytes(StandardCharsets.UTF_8));

                        String key = bytesToHex(keyHash);

                        boolean status = managerDB.addVirtualBook(isbn, title, authors, genre, pages, key);

                        out.println("*****Add virtual book*****");
                        if (status == true) {
                            out.println("Added!");
                        } else {
                            out.println("Not Added!");
                        }
                    }

                    if (line.equals("Edit virtual book") && loggedIn) {
                        String id = in.readLine();
                        String isbn = in.readLine();
                        String title = in.readLine();
                        String authors = in.readLine();
                        String genre = in.readLine();
                        String pages = in.readLine();

                        boolean status = managerDB.editVirtualBook(id, isbn, title, authors, genre, pages);

                        out.println("*****Edit virtual book*****");
                        if (status == true) {
                            out.println("Edited!");
                        } else {
                            out.println("Not edited!");
                        }
                    }

                    if (line.equals("Select view virtual book") && loggedIn) {
                        String isbn = in.readLine();
                        String key = in.readLine();

                        String buffer = managerDB.selectViewVirtualBook(isbn, key);

                        out.println("*****Select view virtual book*****");
                        out.println(buffer);
                    }

                    if (line.equals("Delete virtual book") && loggedIn) {
                        String isbn = in.readLine();

                        boolean status = managerDB.deleteVirtualBook(isbn);

                        out.println("*****Delete virtual book*****");
                        if (status == true) {
                            out.println("Deleted!");
                        } else {
                            out.println("Not deleted!");
                        }
                    }

                    if (line.equals("View virtual books for user") && loggedIn) {
                        String buffer = managerDB.viewVirtualBooksForUser();
                        out.println("*****List of virtual books for user*****");
                        out.println(buffer);
                    }

                    if (line.equals("View borrowed books") && loggedIn) {
                        String buffer = managerDB.viewBorrowedBooks();
                        out.println("*****List of borrowed books*****");
                        out.println(buffer);
                    }

                    if (line.equals("Change my information") && loggedIn) {
                        String firstname = in.readLine();
                        String lastname = in.readLine();
                        String birthday = in.readLine();
                        String address = in.readLine();
                        String phone = in.readLine();
                        String email = in.readLine();
                        String username = in.readLine();
                        String password = in.readLine();


                        int userID = managerDB.searchUser(clientName);
                        String ID = String.valueOf(userID);
                        System.out.println(userID);

                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hashPass = digest.digest(
                                password.getBytes(StandardCharsets.UTF_8));

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        RegisteredUser editedUser;
                        if (birthday.isEmpty()) {
                            editedUser = new RegisteredUser(firstname,
                                    lastname, null,
                                    address, phone, email, username, bytesToHex(hashPass));
                        } else {
                            editedUser = new RegisteredUser(firstname,
                                    lastname, LocalDate.parse(birthday, formatter),
                                    address, phone, email, username, bytesToHex(hashPass));
                        }


                        if (managerDB.changeInformation(editedUser, userID) == false) {
                            out.println("The change could not be made!");
                        } else {
                            out.println("The change was successful!");
                        }
                    }

                    if (line.equals("Get my information") && loggedIn) {
                        String b = managerDB.getUserInfo(clientName);
                        if (b.isEmpty())
                            out.println("The user was not found. Maybe was deleted");
                        else out.println(b);
                    }

                    if (line.equals("Update personal information") && loggedIn) {
                        String b = in.readLine();
                        String[] s = b.split("\t");
                        if (s.length == 8) {
                            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                            byte[] hashPass = digest.digest(
                                    s[7].getBytes(StandardCharsets.UTF_8));
                            s[7] = bytesToHex(hashPass);
                        }
                        if (managerDB.updateUserInfo(s, clientName)) {
                            out.println("Updated successfully");
                            if (!Objects.equals(clientName, s[6])) {
                                System.out.println("User changed name:"+clientName+"->"+s[6]);
                                clientName = s[6];
                            }
                        } else out.println("Update failed");
                    }

                    if (line.equals("Search borrow book by ISBN") && loggedIn) {
                        String isbn = in.readLine();
                        String buffer = managerDB.searchISBNBorrowBook(isbn);
                        //out.println("*****Searched borrow book*****");
                        out.println(buffer);
                    }

                    if (line.equals("Borrow book") && loggedIn) {
                        String title = in.readLine();
                        String author = in.readLine();

                        int bookID = managerDB.searchBook(title, author);
                        int userID = managerDB.searchUser(clientName);

                        String buffer = "";
                        if (managerDB.requestBorrow(userID, bookID) == false) {
                            buffer = "The request can't be made!";
                        } else {
                            buffer = "Request done!";
                        }
                        if (buffer.equals("Request done!")) {
                            String borrow = "borrow";
                            if (managerDB.verifyStatus(borrow, bookID, userID) != null) {
                                if (managerDB.borrowBook(bookID, userID) == false) {
                                    out.println("The book can't be borrow!");
                                } else {
                                    out.println("Borrow successfully!");
                                }
                            }
                        }
                    }

                    if (line.equals("Add book") && loggedIn) {
                        System.out.println("ISBN:\n");
                        String isbn = in.readLine();
                        System.out.println("Title:\n");
                        String title = in.readLine();
                        System.out.println("Author:\n");
                        String author = in.readLine();
                        List<String> authors = new ArrayList<String>();
                        authors.add(author);
                        System.out.println("Genre:\n");
                        String genre = in.readLine();
                        System.out.println("ro.mta.libraryproject.Library.Book status:\n");
                        String status = in.readLine();
                        System.out.println("ro.mta.libraryproject.Library.Shelf ID:\n");
                        int shelfID = Integer.parseInt(in.readLine());
                        System.out.println("Number of pages:\n");
                        int numPages = Integer.parseInt(in.readLine());

                        Book book = new Book(isbn, title, status, authors, genre, 1, numPages, shelfID, 0);

                        // TO DO: use librarian to add books
                        managerDB.addBook(book);
                    }

                    if (line.equals("Edit book") && loggedIn) {
                        String title = in.readLine();
                        System.out.println(title);
                        String author = in.readLine();
                        System.out.println(author);

                        if (managerDB.getBook(title, author) == false) {
                            out.println("The book doesn't exist!");
                        } else {
                            out.println("The book exist!");
                            String isbn = in.readLine();
                            String title1 = in.readLine();
                            String author1 = in.readLine();
                            String genre = in.readLine();
                            String numberOfPages = in.readLine();
                            String bookStatus = in.readLine();
                            String shelfID = in.readLine();
                            String nrBooks = in.readLine();

                            if (managerDB.editBook(isbn, title1, author1, genre, numberOfPages, bookStatus, shelfID, nrBooks, title, author) == false) {
                                out.println("Couldn't edit the book!");
                            } else {
                                out.println("Edit successfully!");
                            }
                        }

                    }

                    if (line.equals("Edit") && loggedIn) {
                        String isbn = in.readLine();
                        String title = in.readLine();
                        String status = in.readLine();
                        String author = in.readLine();
                        String genre = in.readLine();
                        String NumberOfBooks = in.readLine();
                        String NumberOfPages = in.readLine();
                        String ShelfID = in.readLine();

                        boolean state = managerDB.editBooklib(isbn, title, status, author, genre, NumberOfBooks,NumberOfPages, ShelfID);

                        out.println("*****Edit book*****");
                        if(state == true) {
                            out.println("Edited!");
                        }
                        else {
                            out.println("Not edited!");
                        }

                    }

                    if (line.equals("Add archive") && loggedIn) {
                        String readAccess = in.readLine();
                        String expirationDate = in.readLine();
                        String archiveUser = clientName;

                        boolean status;
                        status = readAccess.equals("true");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        Archive newArchive = new Archive(archiveUser, LocalDate.parse(expirationDate, formatter), status);

                        int userID = managerDB.searchUser(clientName);
                        int archiveID = managerDB.searchArchive(userID);

                        String buffer = "";

                        if (managerDB.requestArchive(archiveID, userID) == false) {
                            buffer = "The request cann't be made!";
                        } else {
                            buffer = "Request done!";
                        }
                        if (buffer.equals("Request done!")) {
                            String add = "add";
                            if (managerDB.verifyStatusArchive(add, archiveID, userID) != null) {
                                if (managerDB.addArchive(newArchive) == false) {
                                    out.println("Couldn't add new archive!");
                                } else {
                                    out.println("New archive was added!");
                                }
                            }
                        }
                    }

                    if (line.equals("View private archives") && loggedIn) {
                        String buffer = managerDB.ViewPrivateArchives();
                        //System.out.println(buffer);
                        if (buffer == null) {
                            out.println("No data to display!");
                        } else {
                            out.println(buffer);

                        }
                    }

                    if (line.equals("Delete archive by ID") && loggedIn) {
                        String ID = in.readLine();
                        System.out.println(ID);
                        String idArchive = managerDB.getArchiveID(ID);
                        managerDB.HandleDeleteArchive(idArchive);
                        managerDB.approveRequest(ID, "archive");
                        out.println("Approved!");

                    }

                    if (line.equals("Add librarian") && loggedIn) {
                        String firstname = in.readLine();
                        String lastname = in.readLine();
                        String birthday = in.readLine();
                        String address = in.readLine();
                        String phone = in.readLine();
                        String email = in.readLine();
                        String username = in.readLine();
                        String password = in.readLine();

                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hashPass = digest.digest(
                                password.getBytes(StandardCharsets.UTF_8));

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                        Librarian newLibrarian = new Librarian(firstname,
                                lastname, LocalDate.parse(birthday, formatter),
                                address, phone, email, username, bytesToHex(hashPass));

                        if (managerDB.addLibrarian(newLibrarian) == false) {
                            out.println("Couldn't add new librarian!");
                        } else {
                            out.println("New librarian was added!");
                        }
                    }

                    if (line.equals("Edit user") && loggedIn) {
                        String ID = in.readLine();
                        String firstname = in.readLine();
                        String lastname = in.readLine();
                        String birthday = in.readLine();
                        String address = in.readLine();
                        String phone = in.readLine();
                        String email = in.readLine();
                        String username = in.readLine();
                        String password = in.readLine();

                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hashPass = digest.digest(
                                password.getBytes(StandardCharsets.UTF_8));

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        RegisteredUser editedUser = new RegisteredUser(firstname,
                                lastname, LocalDate.parse(birthday, formatter),
                                address, phone, email, username, bytesToHex(hashPass));

                        if (managerDB.editUser(editedUser, ID) == false) {
                            out.println("Couldn't edit the data of the user!");
                        } else {
                            out.println("Edit data completed succesfully!");
                        }
                    }

                    //DUPLICATED FUNCTIONALITY
                    if (line.equals("Delete") && loggedIn) {
                        String isbn = in.readLine();

                        boolean status = managerDB.delBook(isbn);
                        if(status == true) {
                            out.println("Deleted!");
                        }
                        else {
                            out.println("Not deleted!");
                        }

                    }

                    if (line.equals("Reserve book") && loggedIn) {
                        String author = in.readLine();
                        String title = in.readLine();

                        int bookID = managerDB.searchBook(title, author);
                        int userID = managerDB.searchUser(clientName);

                        if (managerDB.reserveRequest(userID, bookID) == false) {
                            out.println("Couldn't make the reservation request!");
                        } else {
                            out.println("The reservation request completed succesfully!");
                        }
                    }

                    if (line.equals("View borrowed books") && loggedIn) {
                        String buffer = "Borrowed books:\n";
                        int idUser = managerDB.searchUser(clientName);
                        buffer += managerDB.getBorrowedBook(idUser);
                        buffer = buffer.substring(0, buffer.length() - 1);
                        out.println(buffer);
                    }

                    if (line.equals("View unapproved requests") && loggedIn) {
                        String option = "books";
                        String buffer = managerDB.unapprovedRequests(option);
                        out.println("*****Unapproved book requests*****");
                        out.println(buffer);

                        option = "archive";
                        buffer = managerDB.unapprovedRequests(option);
                        out.println("*****Unapproved archive requests*****");
                        out.println(buffer);
                    }

                    if (line.equals("Return book") && loggedIn) {
                        String author = in.readLine();
                        String title = in.readLine();

                        int bookID = managerDB.searchBook(title, author);
                        int userID = managerDB.searchUser(clientName);

                        if (managerDB.returnRequest(userID, bookID) == false) {
                            out.println("Couldn't make the return request!");
                        } else {
                            out.println("Return request completed succesfully!");
                        }
                    }

                    if (line.equals("Search book") && loggedIn) {
                        String title = in.readLine();
                        String author = in.readLine();

                        int bookID = managerDB.searchBook(title, author);
                        out.println(bookID);
                    }

                    if (line.equals("Handle requests") && loggedIn) {
                        String option = in.readLine();
                        String ID = in.readLine();
                        char decision = in.readLine().toCharArray()[0];
                        String idBook, idUser;

                        String requestType = managerDB.getRequestType(option, ID);
                        System.out.println(requestType);
                        if(decision=='A'){
                            if (requestType.equals("borrow")) {
                                idBook = managerDB.getBookID(ID);
                                idUser = managerDB.getUserID(ID);
                                managerDB.HandleBorrowBook(idUser, idBook);
                                managerDB.updateNumberOfBooks(idBook, -1);
                                managerDB.approveRequest(ID, "book");
                                out.println("Borrow request completed successfully!");
                            }
                            if (requestType.equals("reserve")) {
                                idBook = managerDB.getBookID(ID);
                                managerDB.updateNumberOfBooks(idBook, -1);
                                managerDB.approveRequest(ID, "book");
                                out.println("Reserve request completed successfully!");
                            }
                            if (requestType.equals("return")) {
                                idBook = managerDB.getBookID(ID);
                                idUser = managerDB.getUserID(ID);
                                managerDB.updateNumberOfBooks(idBook, 1);
                                managerDB.HandleReturnBook(idUser, idBook);
                                managerDB.approveRequest(ID, "book");
                                out.println("Return request completed successfully!");
                            }
                            if (requestType.equals("delete")) {
                                String idArchive = managerDB.getArchiveID(ID);
                                managerDB.HandleDeleteArchive(idArchive);
                                managerDB.approveRequest(ID, "archive");
                                out.println("Delete archive request completed successfully!");
                            }
                            if (requestType.equals("add")) {
                                managerDB.approveRequest(ID, "archive");
                                out.println("Add archive request completed successfully!");
                            }
                        }
                        else if(decision=='D'){
                            if (requestType.equals("borrow")) {
                                managerDB.denyRequest(ID, "book");
                                out.println("Borrow request denied successfully!");
                            }
                            if (requestType.equals("reserve")) {
                                managerDB.denyRequest(ID, "book");
                                out.println("Reserve request denied successfully!");
                            }
                            if (requestType.equals("return")) {
                                managerDB.denyRequest(ID, "book");
                                out.println("Return request denied successfully!");
                            }
                            if (requestType.equals("delete")) {
                                String idArchive = managerDB.getArchiveID(ID);
                                managerDB.denyRequest(ID, "archive");
                                out.println("Delete archive request denied successfully!");
                            }
                            if (requestType.equals("add")) {
                                managerDB.denyRequest(ID, "archive");
                                out.println("Add archive request denied successfully!");
                            }
                        }
                        else {
                            out.println("The option is not supported!");
                        }
                    }

                    if (line.equals("Logout") && loggedIn) {

                        loggedIn = false;
                        System.out.println(clientName + " logged out");
                        clientName = "guest";
                        clientPass = "";
                        //out.println("Successfully logged out");

                    }

                    if (line.equals("View archives") && loggedIn) {
                        String buffer = managerDB.librarianViewArchives();
                        System.out.println(buffer);
                        out.println(buffer);
                        //out.println("OKK");
                    }

                    if (line.equals("Add") && loggedIn) {
                        String isbn = in.readLine();
                        String title = in.readLine();
                        String status = in.readLine();
                        String authors = in.readLine();
                        String genre = in.readLine();
                        String books = in.readLine();
                        String pages = in.readLine();
                        String shelfId = in.readLine();

                        boolean state = managerDB.addBooklib(isbn, title, status, authors, genre, books, pages, shelfId);

                        out.println("*****Add virtual book*****");
                        if(state == true) {
                            out.println("Added!");
                        }
                        else {
                            out.println("Not Added!");
                        }

                    }

                    if (line.equals("Sign up") && !loggedIn) {

                        String firstname = in.readLine();

                        String lastname = in.readLine();

                        String birthday = in.readLine();

                        String address = in.readLine();

                        String phone = in.readLine();

                        String email = in.readLine();

                        String username = in.readLine();

                        String password = in.readLine();

                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hashPass = digest.digest(
                                password.getBytes(StandardCharsets.UTF_8));

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                        LocalDate day = LocalDate.parse(birthday, formatter);

                        RegisteredUser newRegisteredUser = new RegisteredUser(firstname,
                                lastname, day,
                                address, phone, email, username, bytesToHex(hashPass));

                        if (managerDB.addUser(newRegisteredUser) == false) {
                            out.println("Failed!");
                        } else {
                            out.println("Successfully registered!");
                        }
                    }

                }
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}