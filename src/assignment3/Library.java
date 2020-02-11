package assignment3;
import java.util.*;
import java.util.function.Predicate;
public class Library {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowBookRecord> borrowRecords = new ArrayList<>();
    private ArrayList<ReservedBookRecord> reserveRecords = new ArrayList<>();
    private User currentUser ;
    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<User> getStudentsFromUserList() {
        ArrayList<User> studentList = new ArrayList<>();
        users.stream().filter((user) -> (user.getClass().getName().equals("assignment3.Student"))).forEachOrdered((user) -> {
            studentList.add( user);
        });
        return studentList;
    }
    
    public ArrayList<Staff> getStaffsFromUserList() {
        ArrayList<Staff> staffList = new ArrayList<>();
        for(User user: users) {
            if(user.getClass().getName().equals("assignment3.Staff")) {
                staffList.add((Staff) user);
            }
        }
        return staffList;
    }
    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<ReservedBookRecord> getReserveRecords() {
        return reserveRecords;
    }

    public void setReserveRecords(ArrayList<ReservedBookRecord> reserveRecords) {
        this.reserveRecords = reserveRecords;
    }
     
    public boolean verifyLogin(String username, String password) {
        User matchedUser = User.findUserByUsername(users, username);
        if(matchedUser == null)
            return false;
        else
            return matchedUser.verifyUsernameAndPassword(username, password);
    }

    public boolean addBook(Book book) {
        books.add(book);
        return books.contains(book);
    }
    public boolean addUser(User user) {
        users.add(user);
        return users.contains(user);
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) throws ClassNotFoundException {
        User matchedUser = User.findUserByUsername(users, username);
        boolean result = true;
        if (matchedUser.getPassword().equals(currentPassword)) {
            matchedUser.setPassword(newPassword);
            result = true;
            
        } else {
            result = false;
            System.out.println(result);
        }
        return result;
    }


    public String getUserFullNameByUserName(String username) {
        return User.findUserByUsername(users, username).getFullname();
    }

    public String getUserNameByUserFullName(String firtName, String lastName) throws Exception {
        String username = null;
        for (User user : users) {
            if (user.getFirstName().equals(firtName) && user.getLastName().equals(lastName)) {
                username = user.getUsername();
            }
        }
        if (username == null) {
            throw new Exception("Can not find record");
        }
        return username;
    }

    public User getUserByUserFullName(String fullName) {
        User matchedUser = null;
        for(User user: users) {
            if(user.getFullname().equals(fullName)) {
                matchedUser = user;
                break;
            }
        }
        return matchedUser;
    }
    public String getUserInfoByUserName(String username) {
        return User.findUserByUsername(users, username).toString();
    }

    public ArrayList<String> getListOfUserFullName()  { // not sort
        ArrayList<String> userFullNames = new ArrayList<>();
        for (User user : users) {
            userFullNames.add(user.getFullname());
        }
        return userFullNames;
    }

    
    public boolean verifyLibrarianByUsername(String username) {
        return User.findUserByUsername(users, username).getUserType() == UserType.Librarian;
    }

    public boolean editBook(String username, Book editedBook) {
        boolean result = true;
        for(Book book: books) {
            if(book.getISBNNumber().equals(editedBook.getISBNNumber()) && book.getBookCopyNumber() == editedBook.getBookCopyNumber()) 
                book.editBook(editedBook.getTitle(), editedBook.getAuthorName().get(0), editedBook.getAuthorName().get(1), editedBook.getLocation(), editedBook.getBookType(), username);
        }
        return result;
    }

    public ArrayList<String> listAllBookTitle() {
        ArrayList<String> bookTitles = new ArrayList<>();
        books.forEach((book) -> {
            bookTitles.add(book.getTitle());
        });
        return bookTitles;
    }
    public ArrayList<Book> findBookByISBN(String isbn) {
        ArrayList<Book> matchedBookList = Book.findBookByISBN(books, isbn);
        if(matchedBookList.isEmpty())
            throw new NullPointerException("No books found");
        else
            return matchedBookList;
    }

    public ArrayList<Book> findBookByTitle(String title) {
        ArrayList<Book> bookList = new ArrayList<>();
        this.getBooks().stream().filter((book) -> (book.getTitle().equals(title))).forEachOrdered((book) -> {
            bookList.add(book);
        });
        return bookList;
    }
    
    public ArrayList<Book> findBookByAuthorName(String author) {
        ArrayList<Book> bookList = new ArrayList<>();
        this.getBooks().stream().filter((book) -> (book.getAuthorName().contains(author))).forEachOrdered((book) -> {
            bookList.add(book);
        });
        return bookList;
    }
    
    public ArrayList<Book> advancedSearch(String keyword) {
        ArrayList<Book> bookList = new ArrayList<>();
//        this.getBooks().stream().filter((book) -> (book.getTitle().contains(title) || book.getLocation().contains(location) ||book.getISBNNumber().contains(isbn) || book.getAuthorName().contains(author) )).forEachOrdered((Book book) -> {
//            bookList.add(book);
//        });
        for (Book book : books) {
            if(book.getISBNNumber().contains(keyword) || book.getTitle().contains(keyword) || book.getLocation().contains(keyword))
                bookList.add(book);
            for(String author: book.getAuthorName()) {
                if(author.contains(keyword))
                    bookList.add(book);
            }
        }
        return bookList;
    }
    public ArrayList<BorrowBookRecord> getBorrowRecords() {
        return borrowRecords;
    }
    
    public boolean addBorrowBookRecord(String librarianUserName, String studentUsername, String bookISBN, int bookCopyNumber){
        Book.findBookByISBNandCopyNumber(books, bookISBN, bookCopyNumber).setBookStatus(Status.Borrowed);
        BorrowBookRecord record = new BorrowBookRecord(studentUsername, bookISBN, bookCopyNumber, null, librarianUserName);
        borrowRecords.add(record);
        return borrowRecords.contains(record);
    }
    //return means remove this book or set something ??
    public boolean returnBookRecord(String librarianUserName, String studentUsername, String bookISBN, int bookCopyNumber) {
        boolean result = false;
        Book.findBookByISBNandCopyNumber(books, bookISBN, bookCopyNumber).setBookStatus(Status.Available);
        for(BorrowBookRecord record: borrowRecords) {
            if(record.filterRecordByUsernameAndISBNAndCopyNumber(studentUsername, bookISBN, bookCopyNumber)) {
                borrowRecords.remove(record);
                record.setStatus(false);
                result = true;
            } else 
                result = false;           
        }
        return result;
    }
    
    public boolean addReservedBookRecord(String studentUsername, String bookISBN, int bookCopyNumber){
        Book.findBookByISBNandCopyNumber(books, bookISBN, bookCopyNumber).setBookStatus(Status.Reserved);
        ReservedBookRecord record = new ReservedBookRecord(studentUsername, bookISBN, bookCopyNumber, null);
        this.reserveRecords.add(record);
        return reserveRecords.contains(record);
    }
    public  ArrayList<User> findUserByStatus(boolean status) {
        ArrayList<User> userList = new ArrayList<>();
        users.stream().filter((user) -> (user.getStatus() == status)).forEachOrdered((user) -> {
            userList.add(user);
        });
        return userList;
    }
    public User getUserByFirstName(String firstname) {
        User outputUser = null;
        for(User user: users) {
            if(user.getFirstName().equals(firstname))
                outputUser = user;
        }
        if(outputUser == null)
            throw new NullPointerException();
        return outputUser;
    }
    public User getUserByLastName(String lastname) {
        User outputUser = null;
        for(User user: users) {
            if(user.getLastName().equals(lastname))
                outputUser = user;
        }
        if(outputUser == null)
            throw new NullPointerException();
        return outputUser;
    }
    
//    public String[] getFullNamesListByUserList(User[] userList) {
//        String[] fullNameList = null ;
//        for(int i = 0; i < userList.length; i++) 
//            fullNameList[i] = userList[i].getFullname();
//        return fullNameList;
//    }
    public ArrayList<BorrowBookRecord> findBorrowRecordByUsername(String username) {
        ArrayList<BorrowBookRecord> matchedRecordList = new ArrayList<>();
        System.out.println(username);
        for(BorrowBookRecord borrowRecord: borrowRecords) {
            if(borrowRecord.filterRecordByUsername(username))
                matchedRecordList.add(borrowRecord);
        }
        return matchedRecordList;
    }
    
    public ArrayList<ReservedBookRecord> findReserveRecordByUsername(String username) {
        ArrayList<ReservedBookRecord> matchedRecordList = new ArrayList<>();
        for(ReservedBookRecord borrowRecord: reserveRecords) {
            if(borrowRecord.filterRecordByUsername(username))
                matchedRecordList.add(borrowRecord);
        }
        return matchedRecordList;
    }
    public  ArrayList<Book> findBooks(Predicate<Book> p) {
        ArrayList<Book> outputBook = new ArrayList<>();
        for(Book book: books) {
            if (p.test(book))
                outputBook.add(book);
        }
        return outputBook;
    }
}
