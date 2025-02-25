import java.util.Scanner;
import java.util.Vector;

import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;

import java.util.Properties;
import database.*;


//java -cp mariadb-java-client-3.0.3.jar;classes;. ATM
//To run Java FX
public class Tester {
    public static void main(String[] args) throws Exception {
        JDBCBroker jdbcBroker = JDBCBroker.getInstance();
        jdbcBroker.getConnection();
        //patronTest();
        patronDate();
        patronZip();
        //addBook();
        findBookWithTitleLike();
        findBooksPublishedBefore();
    } // main

    // Insert a new Patron into the database
    // Created by: Ryan
    public static void patronTest()
    {
        Properties insertProp = new Properties();

        Scanner patronNScanner = new Scanner(System.in); // Scanner for name
        System.out.println("Enter Patron Name: "); // Patron name
        String name = patronNScanner.nextLine();

        Scanner patronAScanner = new Scanner(System.in); // Scanner for address
        System.out.println("Enter Patron Address: "); // Patron address
        String address = patronAScanner.nextLine();

        Scanner patronCScanner = new Scanner(System.in); // Scanner for city
        System.out.println("Enter the Patron City: "); // Patron city
        String city = patronCScanner.nextLine();

        Scanner patronSScanner = new Scanner(System.in); // Scanner for state code
        System.out.println("Enter the Patron State Code: "); // Patron state code
        String stateCode = patronSScanner.nextLine();

        Scanner patronZScanner = new Scanner(System.in); // Scanner for zip
        System.out.println("Enter the Patron Zip: "); // Patron zip
        String zip = patronZScanner.nextLine();

        Scanner patronEScanner = new Scanner(System.in); // Scanner for email
        System.out.println("Enter the Patron Email: "); // Patron email
        String email = patronEScanner.nextLine();

        Scanner patronDScanner = new Scanner(System.in); // Scanner for date of birth
        System.out.println("Enter the Patron DOB (YYYY-MM-DD): "); // Patron date of birth
        String dateOfBirth = patronDScanner.nextLine();

        // Put scanned items into Property object
        insertProp.setProperty("name", name);
        insertProp.setProperty("address", address);
        insertProp.setProperty("city", city);
        insertProp.setProperty("stateCode", stateCode);
        insertProp.setProperty("zip", zip);
        insertProp.setProperty("email", email);
        insertProp.setProperty("dateOfBirth", dateOfBirth);

        // Make Patron from Property parameters and add it to database
        Patron insertPatron = new Patron(insertProp);
        insertPatron.save();

        System.out.println("Added to database");
    } // patronTest

    // Find Patron younger than date
    public static void patronDate() throws Exception {
        Scanner patronDScanner = new Scanner(System.in);
        System.out.println("Enter the Patron DOB (YYYY-MM-DD): ");
        String dateOfBirth = patronDScanner.nextLine();
        PatronCollection findPatron = new PatronCollection();

        findPatron.findPatronsYoungerThan(dateOfBirth);
        System.out.println("Patrons younger than " + dateOfBirth + " are: ");
        Vector<Patron> patrons = (Vector<Patron>) findPatron.getState("Patrons");
        for(Patron patron : patrons){
            System.out.println(patron.toString());
        }
    } // patronDate

    // Find Patron with zip
    public static void patronZip() throws Exception {
        Scanner patronZScanner = new Scanner(System.in);
        System.out.println("Enter the Patron zip code: ");
        String zip = patronZScanner.nextLine();
        PatronCollection findPatron = new PatronCollection();

        findPatron.findPatronsAtZipCode(zip);
        System.out.println("Patrons with same zip " + zip + " are: ");
        Vector<Patron> patrons = (Vector<Patron>) findPatron.getState("Patrons");
        for(Patron patron : patrons){
            System.out.println(patron.toString());
        }
    } // patronZip

    // Insert a new Book into the database
    public static void addBook(){
        Scanner input = new Scanner(System.in);
        //Test inserting a book into the database

        //Prompt the user for Book details
        System.out.println("Enter book Title: ");
        String bookTitle = input.nextLine();

        System.out.println("Enter Author: ");
        String author = input.nextLine();

        System.out.println("Enter Published Year: ");
        String year = input.nextLine();

        //Create a Properties object using the given details
        Properties bookProps = new Properties();
        bookProps.setProperty("bookTitle",bookTitle);
        bookProps.setProperty("author", author);
        bookProps.setProperty("pubYear", year);
        bookProps.setProperty("status", "active");

        //Create a book object using the Properties
        Book book = new Book(bookProps);

        //insert book into the database
        book.save();
    }

    // Find books with similar title
    public static void findBookWithTitleLike(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Title:");
        String title = input.nextLine();

        BookCollection collection = new BookCollection();

        try {
            collection.findBooksWithTitleLike(title);
            if (collection.getState("Books") == null || ((Vector<Book>) collection.getState("Books")).isEmpty()) {
                System.out.println("No books found with the title: " + title);
            } else {
                Vector<Book> books = (Vector<Book>) collection.getState("Books");
                for (Book book : books) {
                    System.out.println(book.getState("bookTitle")); // Print book details
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }

    }

    // Find books published before inputted year
    // Created by: Jayden
    public static void findBooksPublishedBefore(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a year: ");
        String year = input.nextLine();
        BookCollection collection = new BookCollection();

        try {
            collection.findBooksOlderThanDate(year);
            if (collection.getState("Books") == null || ((Vector<Book>) collection.getState("Books")).isEmpty()) {
                System.out.println("No books older than the given year: " + year);
            } else {
                Vector<Book> books = (Vector<Book>) collection.getState("Books");
                for (Book book : books) {
                    System.out.println(book.getState("bookTitle")); // Print book details
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }

    }

}