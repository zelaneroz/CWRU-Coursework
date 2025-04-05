package HW1;

// Main method to test the classes
public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // Creating some HW1.Book objects
        Book book1 = new Book("1984", "George Orwell", "12345");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "67890");
        Book book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "11111");

        // Adding books to the library
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // Printing all books in the library
        System.out.println("All books in the library:");
        library.printBooks();

        // Removing a book
        library.removeBook("67890");

        // Printing all books again after removal
        System.out.println("\nAfter removing a book:");
        library.printBooks();
    }
}