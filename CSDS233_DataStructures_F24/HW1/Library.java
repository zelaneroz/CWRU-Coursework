package HW1;

import java.util.ArrayList;

// HW1.Book class
class Book {
    private String title;
    private String author;
    private String isbn;

    // Constructor to initialize all attributes
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // Method to return book details
    public String getDetails() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn;
    }

    // Getter for ISBN to help in removing the book from the HW1.Library
    public String getIsbn() {
        return isbn;
    }
}

// HW1.Library class
public class Library {
    private ArrayList<Book> books = new ArrayList<Book>();

    // Method to add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to remove a book by ISBN
    public void removeBook(String isbn) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.remove(i);
                System.out.println("HW1.Book with ISBN " + isbn + " removed.");
                return;
            }
        }
        System.out.println("HW1.Book with ISBN " + isbn + " not found.");
    }

    // Method to print details of all books in the library
    public void printBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (Book book : books) {
                System.out.println(book.getDetails());
            }
        }
    }
}
