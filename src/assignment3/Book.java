package assignment3;
import java.util.*;
import java.util.stream.Collectors;
public class Book implements Cloneable, Comparable{
// not change book status 
    private final String ISBNNumber;
    private String title, bookType, location, createOrModifiedByUsername;
    private final int bookCopyNumber;
    private ArrayList<String> authorName = new ArrayList<>();
    private Status bookStatus;

    public Book(String ISBNNumber, String title, ArrayList<String> authorName, String location, String bookType, Status bookStatus, int bookCopyNumber) {
        this.ISBNNumber = ISBNNumber;
        this.bookCopyNumber = bookCopyNumber;
        this.title = title;
        this.authorName = authorName;
        this.location = location;
        this.bookType = bookType;
        this.bookStatus = bookStatus;
        this.createOrModifiedByUsername = createOrModifiedByUsername;
    }
    public Book(String ISBNNumber, String title, String author1, String author2, String location, String bookType, Status bookStatus, int bookCopyNumber) {
        this.ISBNNumber = ISBNNumber;
        this.bookCopyNumber = bookCopyNumber;
        this.title = title;
        this.authorName.add(author1);
        this.authorName.add(author2);
        this.location = location;
        this.bookType = bookType;
        this.bookStatus = bookStatus;
        this.createOrModifiedByUsername = createOrModifiedByUsername;
    }

    public String getISBNNumber() {
        return ISBNNumber;
    }

    
    public int getBookCopyNumber() {
        return bookCopyNumber;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAuthorName() {
        return authorName;
    }

    public String getLocation() {
        return location;
    }

    public String getBookType() {
        return bookType;
    }

    public Status getBookStatus() {
        return bookStatus;
    }

    public String getCreateOrModifiedByUsername() {
        return createOrModifiedByUsername;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setAuthorName(ArrayList<String> authorName) {
        this.authorName = authorName;
    }

    public void addAuthor(String author1, String author2) {
        this.authorName.set(0, author1);
        this.authorName.set(1, author2);
    }
    protected void setLocation(String location) {
        this.location = location;
    }

    protected void setBookType(String bookType) {
        this.bookType = bookType;
    }

    protected void setBookStatus(Status bookStatus) {
        this.bookStatus = bookStatus;
    }

    protected void setCreateOrModifiedByUsername(String createOrModifiedByUsername) {
        this.createOrModifiedByUsername = createOrModifiedByUsername;
    }
    public boolean isBorrowed() {
        return(this.bookStatus == Status.Borrowed);
    }

    public boolean isReserved() {
        return this.bookStatus == Status.Reserved;
    }

    public boolean isAvailable() {
        return this.bookStatus == Status.Available;
    }
    
    public boolean isDeleted() {
        return this.bookStatus == Status.Deleted;
    }

    public boolean isDamaged() {
        return this.bookStatus == Status.Damaged;
    }
    public boolean updatedBorrowed(String username){
        this.setBookStatus(Status.Borrowed);
        return this.getBookStatus() == Status.Borrowed;
    } 
    public boolean updatedReserved(String username){
        this.setBookStatus(Status.Reserved);
        return this.getBookStatus() == Status.Reserved;
    } 
    public boolean updatedAvailable(String username){
        this.setBookStatus(Status.Available);
        return this.getBookStatus() == Status.Available;
    } 
    public boolean updatedDamaged(String username){
        this.setBookStatus(Status.Damaged);
        return this.getBookStatus() == Status.Damaged;
    } 
    public boolean checkBookInfoByISBNandCopy(String isbn, int copyNumber) {
        return(this.ISBNNumber.equals(isbn) && this.bookCopyNumber == copyNumber);
    }
    public void editBook(String title, ArrayList<String> authorName, String location, String bookType, String username) {
        setTitle(title);   
        setAuthorName(authorName);
        setBookType(bookType);        
        setLocation(location);        
        setCreateOrModifiedByUsername(username);
    }
    
    public void editBook(String title, String author1, String author2, String location, String bookType, String username) {
        setTitle(title);   
        this.addAuthor(author1, author2);
        setBookType(bookType);        
        setLocation(location);        
        setCreateOrModifiedByUsername(username);
    }
    @Override
    public String toString() {
        return String.format("%s   %-30s %-30s %s  %s  %s %d",ISBNNumber, title, authorName, location, bookType, bookStatus, this.bookCopyNumber );
    }
    @Override
    public Book clone() throws CloneNotSupportedException{
        return (Book)super.clone();
    }
    @Override
    public int compareTo(Object o) {
        Book other = (Book) o;
        int result = this.ISBNNumber.compareTo(other.ISBNNumber);
        if (result == 0)
            result = Integer.compare(this.getBookCopyNumber(), other.getBookCopyNumber());
        return result;
    }
    public static Book findBookByISBNandCopyNumber(ArrayList<Book> inputBooks, String isbn, int copyNumber) {
        Book outputBook = null;
        for(Book book: inputBooks) {
            if (book.getISBNNumber().equals(isbn) && book.getBookCopyNumber() == copyNumber) {
                outputBook = book;
                break;
            }
        }
        if(outputBook == null )
            throw new NullPointerException("No books found");
        return outputBook;
    }
    public static ArrayList<Book> findBookByISBN(ArrayList<Book> inputBooks, String isbn) {
        ArrayList<Book> outputBooks = new ArrayList<>();
        inputBooks.stream().filter(book -> book.getISBNNumber().equals(isbn)).forEach(book -> {
            outputBooks.add(book);
        });
            return outputBooks;
    }
    public static ArrayList<Book> findBookByStatus(ArrayList<Book> inputBooks, Status status) {
        ArrayList<Book> outputBooks = new ArrayList<>();
        inputBooks.stream().filter(book -> book.getBookStatus() == status).forEach(book -> outputBooks.add(book));
//        new optimization
//        inputBooks.stream()
//                .filter(book -> book.getBookStatus() == status)
//                .collect(Collectors.toList());
//        if(outputBooks.isEmpty())
//            throw new NullPointerException("No books found");
//        else
            return outputBooks;
    }
    public static ArrayList<String> allBookTitleToArrayListOfString(ArrayList<Book> inputBooks) {
        ArrayList<String> bookTitles = new ArrayList<>();
        inputBooks.forEach((book) -> {
            bookTitles.add(book.getTitle());        
        });
        return bookTitles;
    }
    public static String allBookToString(ArrayList<Book> inputBooks) {
        String bookString = "";
//        bookString = inputBooks.stream().map((book) -> String.format("%s\n", book)).reduce(bookString, String::concat);

//        new optimization
        bookString = inputBooks.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
//        for(Book b: inputBooks) 
//            bookString += String.format("%s\n", b);
//        inputBooks.stream().map((Book b) -> {
//            return String.format("%s\n", b);
//            
//        }).forEach(b -> {
//            bookString += b;
//        });
        
        return bookString;
    }
}















