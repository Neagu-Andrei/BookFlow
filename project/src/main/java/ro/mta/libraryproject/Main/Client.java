package ro.mta.libraryproject.Main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

  /* @author Andi*
 *   
 *  Ouatu Laura
 *    
 */

public class Client {
    private static void printMenu() {
        String[] menu_items = new String[]{"Login", "Sign up", "Exit", "View books"};
        // repeat as long as exit
        // is not typed at client

        int i = 1;
        for (String item : menu_items) {
            System.out.print(i + ")" + item + "\n");
            i++;
        }
    }

    public static void main(String[] args)
            throws Exception {
          /* @author Andi*
         * Member decription
         * client socket s
         * DataOutputStream to send data to the server
         * BufferedReader br to read data coming from the server
         * BufferedReader kb to read data from the keyboard
         * String str for client input and str1 for data coming from the server
         */

        Socket s = new Socket("localhost", 15808);

        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        String str, str1;

        printMenu();

        boolean logged = false;

        while (!(str = kb.readLine()).equals("Exit")) {
            if (str.equals("Login")) {
                System.out.println("Username:");
                String username = kb.readLine();
                System.out.println("Password:");
                String password = kb.readLine();
                dos.writeBytes(str + "\n");
                dos.writeBytes(username + "\n");
                dos.writeBytes(password + "\n");

                str1 = br.readLine();
                String type = br.readLine();

                if (str1.equals("Successfully logged in")) {
                    logged = true;
                    if (type.equals("admin")) {
                        System.out.println("-----Options-----");
                        System.out.println("1)View books");
                        System.out.println("2)Add librarian");
                        System.out.println("3)View all users");
                        System.out.println("4)Edit user");
                        System.out.println("5)View borrowed books");
                        System.out.println("6)Logout");
                        while (!(str = kb.readLine()).equals("Logout")) {
                            //TO DO
                            if (str.equals("View books")) {
                                //TO DO
                                dos.writeBytes(str + "\n");
                                while ((str1 = br.readLine()) != null) {
                                    System.out.println(str1);
                                }
                            }
                            if (str.equals("Add librarian")) {
                                //TO DO
                                System.out.println("First name:");
                                String firstname;
                                while ((firstname = kb.readLine()).isEmpty())
                                    System.out.println("First name can't be empty:");
                                System.out.println("Last name:");
                                String lastname;
                                while ((lastname = kb.readLine()).isEmpty())
                                    System.out.println("Last name can't be empty:");
                                System.out.println("Birthday:");
                                String birthday;
                                while ((birthday = kb.readLine()).isEmpty())
                                    System.out.println("Birthday can't be empty:");
                                System.out.println("Address:");
                                String address;
                                while ((address = kb.readLine()).isEmpty())
                                    System.out.println("Address can't be empty:");
                                System.out.println("Phone number:");
                                String phone;
                                while ((phone = kb.readLine()).isEmpty())
                                    System.out.println("Phone can't be empty:");
                                System.out.println("Email:");
                                String email;
                                while ((email = kb.readLine()).isEmpty())
                                    System.out.println("Email can't be empty:");
                                System.out.println("Username:");
                                String usernameInput;
                                while ((usernameInput = kb.readLine()).isEmpty())
                                    System.out.println("Username can't be empty:");
                                System.out.println("Password:");
                                String passwordInput;
                                while ((passwordInput = kb.readLine()).isEmpty())
                                    System.out.println("Password can't be empty:");

                                dos.writeBytes(firstname + "\n");
                                dos.writeBytes(lastname + "\n");
                                dos.writeBytes(birthday + "\n");
                                dos.writeBytes(address + "\n");
                                dos.writeBytes(phone + "\n");
                                dos.writeBytes(email + "\n");
                                dos.writeBytes(usernameInput + "\n");
                                dos.writeBytes(passwordInput + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);
                            }

                            if (str.equals("View all users")) {
                                dos.writeBytes(str + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);
                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);
                            }

                            if (str.equals("Edit user")) {
                                dos.writeBytes(str + "\n");
                                System.out.println("ID of the user whose data you want to change:");
                                String ID = kb.readLine();
                                System.out.println("Leave empty if you don't want to change it");
                                System.out.println("First name:");
                                String firstname = kb.readLine();
                                System.out.println("Last name:");
                                String lastname = kb.readLine();
                                System.out.println("Birthday:");
                                String birthday = kb.readLine();
                                System.out.println("Address:");
                                String address = kb.readLine();
                                System.out.println("Phone number:");
                                String phone = kb.readLine();
                                System.out.println("Email:");
                                String email = kb.readLine();
                                System.out.println("Username:");
                                String newUsername = kb.readLine();
                                System.out.println("Password:");
                                String newPassword = kb.readLine();

                                dos.writeBytes(ID + "\n");
                                dos.writeBytes(firstname + "\n");
                                dos.writeBytes(lastname + "\n");
                                dos.writeBytes(birthday + "\n");
                                dos.writeBytes(address + "\n");
                                dos.writeBytes(phone + "\n");
                                dos.writeBytes(email + "\n");
                                dos.writeBytes(newUsername + "\n");
                                dos.writeBytes(newPassword + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("View borrowed books")) {
                                dos.writeBytes(str + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);
                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);
                            }
                        }
                        dos.writeBytes("Logout\n");
                        logged = false;
                        printMenu();
                    }
                    if (type.equals("librarian")) {
                        System.out.println("-----Options-----");
                        System.out.println("1)View books");
                        System.out.println("2)Add book");
                        System.out.println("3)Edit book");
                        System.out.println("4)Delete book");
                        System.out.println("5)View all registered users");
                        System.out.println("6)View borrowed books");
                        System.out.println("7)View unapproved requests");
                        System.out.println("8)Handle requests");
                        System.out.println("9)Search borrow book by ISBN");
                        System.out.println("10)Logout");
                        while (!(str = kb.readLine()).equals("Logout")) {
                            //TO DO
                            if (str.equals("View books")) {
                                //TO DO
                                dos.writeBytes(str + "\n");
                                while ((str1 = br.readLine()) != null) {
                                    System.out.println(str1);
                                }
                            }
                            if (str.equals("Add book")) {
                                //TO DO

                                dos.writeBytes(str + "\n");
                                System.out.println("ISBN:");
                                String isbn;
                                while ((isbn = kb.readLine()).isEmpty())
                                    System.out.println("ISBN can't be empty:");
                                System.out.println("Title:");
                                String title;
                                while ((title = kb.readLine()).isEmpty())
                                    System.out.println("Title can't be empty:");
                                System.out.println("Author:");
                                String author;
                                while ((author = kb.readLine()).isEmpty())
                                    System.out.println("Author can't be empty:");
                                System.out.println("Genre:");
                                String genre;
                                while ((genre = kb.readLine()).isEmpty())
                                    System.out.println("Genre can't be empty:");
                                System.out.println("Number Of Pages:");
                                String numberOfPages;
                                while ((numberOfPages = kb.readLine()).isEmpty())
                                    System.out.println("Number of pages can't be empty:");
                                System.out.println("ro.mta.libraryproject.Library.Book Status:");
                                String bookStatus;
                                while ((bookStatus = kb.readLine()).isEmpty())
                                    System.out.println("ro.mta.libraryproject.Library.Book status can't be empty:");
                                System.out.println("ro.mta.libraryproject.Library.Shelf ID:");
                                String shelfID;
                                while ((shelfID = kb.readLine()).isEmpty())
                                    System.out.println("ro.mta.libraryproject.Library.Shelf ID can't be empty:");
                                System.out.println("Number of Books:");
                                String nrBooks;
                                while ((nrBooks = kb.readLine()).isEmpty())
                                    System.out.println("Number of books can't be empty:");

                                dos.writeBytes(isbn + "\n");
                                dos.writeBytes(title + "\n");
                                dos.writeBytes(author + "\n");
                                dos.writeBytes(genre + "\n");
                                dos.writeBytes(numberOfPages + "\n");
                                dos.writeBytes(bookStatus + "\n");
                                dos.writeBytes(shelfID + "\n");
                                dos.writeBytes(nrBooks + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("Edit book")) {
                                //TO DO
                                dos.writeBytes(str + "\n");
                                System.out.println("Title:");
                                String title;
                                while ((title = kb.readLine()).isEmpty())
                                    System.out.println("Title can't be empty:");

                                System.out.println("Author:");
                                String author;
                                while ((author = kb.readLine()).isEmpty())
                                    System.out.println("Author can't be empty:");

                                dos.writeBytes(title + "\n");
                                dos.writeBytes(author + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                                if (str1.equals("The book exist!")) {
                                    System.out.println("Leave empty if you don't want to change it");
                                    System.out.println("ISBN:");
                                    String isbn = kb.readLine();
                                    System.out.println("Title:");
                                    String title1 = kb.readLine();
                                    System.out.println("Author:");
                                    String author1 = kb.readLine();
                                    System.out.println("Genre:");
                                    String genre = kb.readLine();
                                    System.out.println("Number Of Pages:");
                                    String numberOfPages = kb.readLine();
                                    System.out.println("ro.mta.libraryproject.Library.Book Status:");
                                    String bookStatus = kb.readLine();
                                    System.out.println("ShelfID:");
                                    String shelfID = kb.readLine();
                                    System.out.println("nrBooks:");
                                    String nrBooks = kb.readLine();


                                    dos.writeBytes(title1 + "\n");
                                    dos.writeBytes(author1 + "\n");
                                    dos.writeBytes(genre + "\n");
                                    dos.writeBytes(numberOfPages + "\n");
                                    dos.writeBytes(bookStatus + "\n");
                                    dos.writeBytes(shelfID + "\n");
                                    dos.writeBytes(nrBooks + "\n");
                                }
                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("Delete book")) {
                                dos.writeBytes(str + "\n");
                                System.out.println("Title:");
                                String title;
                                while ((title = kb.readLine()).isEmpty())
                                    System.out.println("Title can't be empty:");

                                System.out.println("Author:");
                                String author;
                                while ((author = kb.readLine()).isEmpty())
                                    System.out.println("Author can't be empty:");

                                dos.writeBytes(title + "\n");
                                dos.writeBytes(author + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("View all registered users")) {
                                dos.writeBytes(str + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);
                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);
                            }

                            if (str.equals("View borrowed books")) {
                                dos.writeBytes(str + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);
                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);
                            }

                            if (str.equals("View unapproved requests")) {
                                dos.writeBytes(str + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);

                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);

                                dos.writeBytes(str + "\n");
                                str1 = br.readLine();
                                System.out.println(str1);

                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);
                            }

                            if (str.equals("Search borrow book by ISBN")) {
                                dos.writeBytes(str + "\n");

                                System.out.println("ISBN:");
                                String isbn;
                                while ((isbn = kb.readLine()).isEmpty())
                                    System.out.println("ISBN can't be empty:");

                                dos.writeBytes(isbn + "\n");

                                //str1 = br.readLine();
                                //System.out.println(str1);
                                str1 = br.readLine();
                                str1 = str1.replace('~', '\n');
                                System.out.println(str1);
                            }

                            if (str.equals("Handle requests")) {
                                System.out.println("Handle request for Book/Archive:");
                                String option;
                                while ((option = kb.readLine()).isEmpty())
                                    System.out.println("The option (Book/Archive) can't be empty:");

                                dos.writeBytes(str + "\n");
                                System.out.println("ID of the request you want to handle:");
                                String id;
                                while ((id = kb.readLine()).isEmpty())
                                    System.out.println("ID can't be empty:");

                                dos.writeBytes(option + "\n");
                                dos.writeBytes(id + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                        }
                        dos.writeBytes("Logout\n");
                        logged = false;
                        printMenu();
                    }
                    if (type.equals("registeredUser")) {
                        System.out.println("-----Options-----");
                        System.out.println("1)View books");
                        System.out.println("2)Borrow book");
                        System.out.println("3)Reserve book");
                        System.out.println("4)View borrowed books");
                        System.out.println("5)Return book");
                        System.out.println("6)Change my information");
                        System.out.println("7)Add archive");
                        System.out.println("8)Logout");
                        while (!(str = kb.readLine()).equals("Logout")) {
                            //TO DO
                            if (str.equals("View books")) {
                                //TO DO
                                dos.writeBytes(str + "\n");
                                while ((str1 = br.readLine()) != null) {
                                    System.out.println(str1);
                                }
                            }
                            if (str.equals("Borrow book")) {
                                dos.writeBytes(str + "\n");
                                String title, author;
                                System.out.println("Title:");
                                while ((title = kb.readLine()).isEmpty())
                                    System.out.println("Title can't be empty:");
                                System.out.println("Author:");
                                while ((author = kb.readLine()).isEmpty())
                                    System.out.println("Author can't be empty:");


                                dos.writeBytes(title + "\n");
                                dos.writeBytes(author + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);

                            }
                            if (str.equals("Change my information")) {
                                System.out.println("Leave empty if you don't want to change it");
                                System.out.println("First name:");
                                String firstname = kb.readLine();
                                System.out.println("Last name:");
                                String lastname = kb.readLine();
                                System.out.println("Birthday:");
                                String birthday = kb.readLine();
                                System.out.println("Address:");
                                String address = kb.readLine();
                                System.out.println("Phone number:");
                                String phone = kb.readLine();
                                System.out.println("Email:");
                                String email = kb.readLine();
                                System.out.println("Username:");
                                username = kb.readLine();
                                System.out.println("Password:");
                                password = kb.readLine();

                                dos.writeBytes(str + "\n");
                                dos.writeBytes(firstname + "\n");
                                dos.writeBytes(lastname + "\n");
                                dos.writeBytes(birthday + "\n");
                                dos.writeBytes(address + "\n");
                                dos.writeBytes(phone + "\n");
                                dos.writeBytes(email + "\n");
                                dos.writeBytes(username + "\n");
                                dos.writeBytes(password + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("Add archive")) {
                                System.out.println("Read access:");
                                String readAccess;
                                while ((readAccess = kb.readLine()).isEmpty())
                                    System.out.println("Read access can't be empty:");
                                System.out.println("Expiration date:");
                                String expirationDate;
                                while ((expirationDate = kb.readLine()).isEmpty())
                                    System.out.println("Expiration date can't be empty:");

                                dos.writeBytes(str + "\n");
                                dos.writeBytes(readAccess + "\n");
                                dos.writeBytes(expirationDate + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("Reserve book")) {
                                System.out.println("Author:");
                                String author;
                                while ((author = kb.readLine()).isEmpty())
                                    System.out.println("Author can't be empty:");
                                System.out.println("Title:");
                                String title;
                                while ((title = kb.readLine()).isEmpty())
                                    System.out.println("Title can't be empty:");

                                dos.writeBytes(str + "\n");
                                dos.writeBytes(author + "\n");
                                dos.writeBytes(title + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                            if (str.equals("View borrowed books")) {
                                dos.writeBytes(str+"\n");
                                while ((str1 = br.readLine()) != null) {
                                    System.out.println(str1);
                                }
                            }
                            if (str.equals("Return book")) {
                                System.out.println("Author:");
                                String author;
                                while ((author = kb.readLine()).isEmpty())
                                    System.out.println("Author can't be empty:");
                                System.out.println("Title:");
                                String title;
                                while ((title = kb.readLine()).isEmpty())
                                    System.out.println("Title can't be empty:");
                                dos.writeBytes(str + "\n");
                                dos.writeBytes(author + "\n");
                                dos.writeBytes(title + "\n");

                                str1 = br.readLine();
                                System.out.println(str1);
                            }
                        }
                        dos.writeBytes("Logout\n");
                        logged = false;
                        printMenu();
                    }
                } else {
                    //TO DO
                    System.out.println(str1);
                    printMenu();
                }
            }

            if (str.equals("View books")) {
                //TO DO
                dos.writeBytes(str + "\n");
                while ((str1 = br.readLine()) != null) {
                    System.out.println(str1);
                }
                printMenu();
            }

            if (str.equals("Sign up") && !logged) {
                System.out.println("First name:");
                String firstname;
                while ((firstname = kb.readLine()).isEmpty())
                    System.out.println("First name can't be empty:");
                System.out.println("Last name:");
                String lastname;
                while ((lastname = kb.readLine()).isEmpty())
                    System.out.println("Last name can't be empty:");
                System.out.println("Birthday:");
                String birthday;
                while ((birthday = kb.readLine()).isEmpty())
                    System.out.println("Birthday can't be empty:");
                System.out.println("Address:");
                String address;
                while ((address = kb.readLine()).isEmpty())
                    System.out.println("Address can't be empty:");
                System.out.println("Phone number:");
                String phone;
                while ((phone = kb.readLine()).isEmpty())
                    System.out.println("Phone can't be empty:");
                System.out.println("Email:");
                String email;
                while ((email = kb.readLine()).isEmpty())
                    System.out.println("Email can't be empty:");
                System.out.println("Username:");
                String username;
                while ((username = kb.readLine()).isEmpty())
                    System.out.println("Username can't be empty:");
                System.out.println("Password:");
                String password;
                while ((password = kb.readLine()).isEmpty())
                    System.out.println("Password can't be empty:");

                dos.writeBytes(str + "\n");
                dos.writeBytes(firstname + "\n");
                dos.writeBytes(lastname + "\n");
                dos.writeBytes(birthday + "\n");
                dos.writeBytes(address + "\n");
                dos.writeBytes(phone + "\n");
                dos.writeBytes(email + "\n");
                dos.writeBytes(username + "\n");
                dos.writeBytes(password + "\n");
                str1 = br.readLine();
                System.out.println(str1);

                printMenu();
            }
        }

        // close connection.
        dos.close();
        br.close();
        kb.close();
        s.close();
    }
}
