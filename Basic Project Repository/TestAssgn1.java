import java.util.Scanner;
import model.*;

import java.util.Properties;

public class TestAssgn1{
    public static void main(String[] args){
        addBook();
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
}
