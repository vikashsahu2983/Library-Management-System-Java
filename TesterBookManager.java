import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// -------------------------------------
// Custom Exception
// -------------------------------------
class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg) {
        super(msg);
    }
}

// -------------------------------------
// Book Class
// -------------------------------------
class Book {
    private String name;
    private String author;
    private String ISBN;
    private String genre;
    private int quantity;

    public Book(String name, String author, String ISBN, String genre, int quantity) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String n) { name = n; }

    public String getAuthor() { return author; }
    public void setAuthor(String a) { author = a; }

    public String getISBN() { return ISBN; }
    public void setISBN(String i) { ISBN = i; }

    public String getGenre() { return genre; }
    public void setGenre(String g) { genre = g; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { quantity = q; }

    @Override
    public String toString() {
        return "Book [name=" + name + ", author=" + author + ", ISBN=" + ISBN +
               ", genre=" + genre + ", quantity=" + quantity + "]";
    }
}

// -------------------------------------
// Library Management
// -------------------------------------
class LibraryManagement {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book b) {
        books.add(b);
        System.out.println("Book Added Successfully.");
    }

    public Book searchBookByTitle(String t) {
        for (Book b : books)
            if (b.getName().equals(t)) return b;
        return null;
    }

    public Book searchBookByISBN(String i) {
        for (Book b : books)
            if (b.getISBN().equals(i)) return b;
        return null;
    }

    public List<Book> searchBookByAuthor(String a) {
        List<Book> list = new ArrayList<>();
        for (Book b : books)
            if (b.getAuthor().equals(a)) list.add(b);
        return list;
    }

    public List<Book> searchBookByGenre(String g) {
        List<Book> list = new ArrayList<>();
        for (Book b : books)
            if (b.getGenre().equals(g)) list.add(b);
        return list;
    }

    public boolean removeBookByTitle(String t) {
        return books.removeIf(b -> b.getName().equals(t));
    }

    public void updateBook_Title(String ISBN, String newTitle) {
        Book b = searchBookByISBN(ISBN);
        if (b == null) {
            System.out.println("Book Not Found.");
            return;
        }
        b.setName(newTitle);
        System.out.println("Title Updated.");
    }

    public void updateBook_Author(String ISBN, String newAuthor) {
        Book b = searchBookByISBN(ISBN);
        if (b == null) {
            System.out.println("Book Not Found.");
            return;
        }
        b.setAuthor(newAuthor);
        System.out.println("Author Updated.");
    }

    public void updateBook_Quantity(String ISBN, int qty) {
        Book b = searchBookByISBN(ISBN);
        if (b == null) {
            System.out.println("Book Not Found.");
            return;
        }
        b.setQuantity(qty);
        System.out.println("Quantity Updated.");
    }

    public void updateBook_Genre(String ISBN, String g) {
        Book b = searchBookByISBN(ISBN);
        if (b == null) {
            System.out.println("Book Not Found.");
            return;
        }
        b.setGenre(g);
        System.out.println("Genre Updated.");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No Books Available.");
            return;
        }
        for (Book b : books) System.out.println(b);
    }

    public void displayBook(String t) {
        Book b = searchBookByTitle(t);
        if (b == null) System.out.println("Book Not Found.");
        else System.out.println(b);
    }
}

// -------------------------------------
// Admin Class
// -------------------------------------
class Admin {
    public static final String USER_NAME = "LocalAdmin";
    public static final String PASSWORD = "Admin@9090";

    private LibraryManagement lib;

    public Admin(LibraryManagement l) {
        lib = l;
    }

    public void addBook(Book b) { lib.addBook(b); }
    public boolean removeBookByTitle(String t) { return lib.removeBookByTitle(t); }
    public Book searchBookByTitle(String t) { return lib.searchBookByTitle(t); }
    public Book searchBookByISBN(String i) { return lib.searchBookByISBN(i); }
    public List<Book> searchBookByAuthor(String a) { return lib.searchBookByAuthor(a); }
    public List<Book> searchBookByGenre(String g) { return lib.searchBookByGenre(g); }

    public void updateBook_Title(String i, String t) { lib.updateBook_Title(i, t); }
    public void updateBook_Author(String i, String a) { lib.updateBook_Author(i, a); }
    public void updateBook_Quantity(String i, int q) { lib.updateBook_Quantity(i, q); }
    public void updateBook_Genre(String i, String g) { lib.updateBook_Genre(i, g); }

    public void displayBooks() { lib.displayBooks(); }
    public void displayBook(String t) { lib.displayBook(t); }
}

// -------------------------------------
// User Class
// -------------------------------------
class User {
    public static final String USER_NAME = "LocalUser";
    public static final String PASSWORD = "User@9090";

    private LibraryManagement lib;

    public User(LibraryManagement l) {
        lib = l;
    }

    public Book searchBookByTitle(String t) { return lib.searchBookByTitle(t); }
    public Book searchBookByISBN(String i) { return lib.searchBookByISBN(i); }
    public List<Book> searchBookByAuthor(String a) { return lib.searchBookByAuthor(a); }
    public List<Book> searchBookByGenre(String g) { return lib.searchBookByGenre(g); }

    public void displayBooks() { lib.displayBooks(); }
    public void displayBook(String t) { lib.displayBook(t); }

    public void borrowBook(String t, int qty) {
        Book b = searchBookByTitle(t);
        if (b == null) {
            System.out.println("Book Not Found.");
            return;
        }

        int stock = b.getQuantity();
        if (stock < qty) {
            System.out.println("Not Enough Stock.");
            return;
        }

        b.setQuantity(stock - qty);
        System.out.println("Book Borrowed.");

        System.out.println("Returning Book in 3 sec...");
        try { Thread.sleep(3000); } catch(Exception e) {}

        returnBook(b, qty);
    }

    public void returnBook(Book b, int qty) {
        b.setQuantity(b.getQuantity() + qty);
        System.out.println("Book Returned.");
    }
}

// -------------------------------------
// MAIN CLASS
// -------------------------------------
public class TesterBookManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LibraryManagement mgmt = new LibraryManagement();
        Admin admin = new Admin(mgmt);
        User user = new User(mgmt);

        mainLoop:
        while (true) {
            System.out.println("\n==== Library Management System ====");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {

            // ---------------- ADMIN LOGIN -----------------
            case 1: {
                System.out.print("Admin Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();

                if (!Admin.USER_NAME.equalsIgnoreCase(u) || !Admin.PASSWORD.equals(p)) {
                    System.out.println("Invalid Admin Credentials.");
                    break;
                }

                while (true) {
                    System.out.println("\n--- ADMIN MENU ---");
                    System.out.println("1. Add Book");
                    System.out.println("2. Remove Book");
                    System.out.println("3. Search By Title");
                    System.out.println("4. Search By ISBN");
                    System.out.println("5. Search By Author");
                    System.out.println("6. Search By Genre");
                    System.out.println("7. Update Title");
                    System.out.println("8. Update Author");
                    System.out.println("9. Update Quantity");
                    System.out.println("10. Update Genre");
                    System.out.println("11. Display All Books");
                    System.out.println("12. Display Single Book");
                    System.out.println("13. Main Menu");
                    System.out.print("Enter Choice: ");

                    int a = Integer.parseInt(sc.nextLine());

                    switch (a) {

                    case 1: {
                        System.out.print("Title: ");
                        String t = sc.nextLine();
                        System.out.print("Author: ");
                        String au = sc.nextLine();
                        System.out.print("ISBN: ");
                        String i = sc.nextLine();
                        System.out.print("Genre: ");
                        String g = sc.nextLine();
                        System.out.print("Quantity: ");
                        int q = Integer.parseInt(sc.nextLine());

                        admin.addBook(new Book(t, au, i, g, q));
                        break;
                    }

                    case 2: {
                        System.out.print("Enter Title: ");
                        String t = sc.nextLine();
                        boolean removed = admin.removeBookByTitle(t);
                        System.out.println(removed ? "Book Removed." : "Book Not Found.");
                        break;
                    }

                    case 3: {
                        System.out.print("Enter Title: ");
                        System.out.println(admin.searchBookByTitle(sc.nextLine()));
                        break;
                    }

                    case 4: {
                        System.out.print("Enter ISBN: ");
                        System.out.println(admin.searchBookByISBN(sc.nextLine()));
                        break;
                    }

                    case 5: {
                        System.out.print("Enter Author: ");
                        admin.searchBookByAuthor(sc.nextLine()).forEach(System.out::println);
                        break;
                    }

                    case 6: {
                        System.out.print("Enter Genre: ");
                        admin.searchBookByGenre(sc.nextLine()).forEach(System.out::println);
                        break;
                    }

                    case 7: {
                        System.out.print("Enter ISBN: ");
                        String i = sc.nextLine();
                        System.out.print("New Title: ");
                        admin.updateBook_Title(i, sc.nextLine());
                        break;
                    }

                    case 8: {
                        System.out.print("Enter ISBN: ");
                        String i = sc.nextLine();
                        System.out.print("New Author: ");
                        admin.updateBook_Author(i, sc.nextLine());
                        break;
                    }

                    case 9: {
                        System.out.print("Enter ISBN: ");
                        String i = sc.nextLine();
                        System.out.print("New Quantity: ");
                        admin.updateBook_Quantity(i, Integer.parseInt(sc.nextLine()));
                        break;
                    }

                    case 10: {
                        System.out.print("Enter ISBN: ");
                        String i = sc.nextLine();
                        System.out.print("New Genre: ");
                        admin.updateBook_Genre(i, sc.nextLine());
                        break;
                    }

                    case 11: {
                        admin.displayBooks();
                        break;
                    }

                    case 12: {
                        System.out.print("Enter Title: ");
                        admin.displayBook(sc.nextLine());
                        break;
                    }

                    case 13: {
                        continue mainLoop;
                    }

                    default:
                        throw new InvalidInputException("Invalid Input");
                    }
                }
            }

            // ---------------- USER LOGIN -----------------
            case 2: {
                System.out.print("User Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();

                if (!User.USER_NAME.equalsIgnoreCase(u) || !User.PASSWORD.equals(p)) {
                    System.out.println("Invalid User Credentials.");
                    break;
                }

                while (true) {
                    System.out.println("\n---- USER MENU ----");
                    System.out.println("1. Search By Title");
                    System.out.println("2. Search By ISBN");
                    System.out.println("3. Search By Author");
                    System.out.println("4. Search By Genre");
                    System.out.println("5. Display All Books");
                    System.out.println("6. Display Single Book");
                    System.out.println("7. Borrow Book");
                    System.out.println("8. Main Menu");
                    System.out.print("Enter Choice: ");

                    int uchoice = Integer.parseInt(sc.nextLine());

                    switch (uchoice) {

                    case 1: {
                        System.out.print("Enter Title: ");
                        System.out.println(user.searchBookByTitle(sc.nextLine()));
                        break;
                    }

                    case 2: {
                        System.out.print("Enter ISBN: ");
                        System.out.println(user.searchBookByISBN(sc.nextLine()));
                        break;
                    }

                    case 3: {
                        System.out.print("Enter Author: ");
                        user.searchBookByAuthor(sc.nextLine()).forEach(System.out::println);
                        break;
                    }

                    case 4: {
                        System.out.print("Enter Genre: ");
                        user.searchBookByGenre(sc.nextLine()).forEach(System.out::println);
                        break;
                    }

                    case 5: {
                        user.displayBooks();
                        break;
                    }

                    case 6: {
                        System.out.print("Enter Title: ");
                        user.displayBook(sc.nextLine());
                        break;
                    }

                    case 7: {
                        System.out.print("Enter Title: ");
                        String t = sc.nextLine();
                        System.out.print("Enter Quantity: ");
                        int q = Integer.parseInt(sc.nextLine());
                        user.borrowBook(t, q);
                        break;
                    }

                    case 8: {
                        continue mainLoop;
                    }

                    default:
                        throw new InvalidInputException("Invalid Input");
                    }
                }
            }

            // ---------------- EXIT -----------------
            case 3: {
                System.out.println("Thanks for using Library System.");
                return;
            }

            default:
                throw new InvalidInputException("Invalid Input");
            }
        }
    }
}
