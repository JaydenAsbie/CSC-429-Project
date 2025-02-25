import java.util.Scanner;
import model.*;
import java.util.Vector;
import java.util.Properties;

public class TestAssgn1{
    public static void main(String[] args){
        //addBook();
        findBookWithTitleLike();
        findBooksPublishedBefore();
    }

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
    public static void findBookWithTitleLike(){
       Scanner input = new Scanner(System.in);

        try {
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
        } finally {
            //input.close(); // Close scanner to prevent resource leaks
        }

    }
    public static void findBooksPublishedBefore(){
        Scanner input = new Scanner(System.in);

        try {
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
        }finally {
           //input.close();
        }

    }
}
