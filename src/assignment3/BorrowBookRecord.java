package assignment3;
import java.util.*;
public class BorrowBookRecord implements Comparable<BorrowBookRecord>{
    private String username, bookISBN, librarianUsernameBorrow, librarianUsernameReturn;
    private int bookCopyNumber;
    private Date borrowDate;
    private Date returnDate = new Date();
    private boolean status = true;

    public BorrowBookRecord(String username, String bookISBN, int bookCopyNumber, Date borrowDate, String librarianUsernameBorrow) {
        this.username = username;
        this.bookISBN = bookISBN;
        this.librarianUsernameBorrow = librarianUsernameBorrow;
        this.bookCopyNumber = bookCopyNumber;
        this.borrowDate = borrowDate;
        this.returnDate = null;
        this.status = true;
        this.librarianUsernameReturn = "-";
    }

    public String getUsername() {
        return username;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public String getLibrarianUsernameBorrow() {
        return librarianUsernameBorrow;
    }

    public String getLibrarianUsernameReturn() {
        return librarianUsernameReturn;
    }

    public int getBookCopyNumber() {
        return bookCopyNumber;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean isStatus() {
        return (status == false);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public void setLibrarianUsernameBorrow(String librarianUsernameBorrow) {
        this.librarianUsernameBorrow = librarianUsernameBorrow;
    }

    public void setLibrarianUsernameReturn(String librarianUsernameReturn) {
        this.librarianUsernameReturn = librarianUsernameReturn;
    }

    public void setBookCopyNumber(int bookCopyNumber) {
        this.bookCopyNumber = bookCopyNumber;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public boolean returnBook(Date returnDate, String libUserReturn) {
        if(isReturned() == true) 
            return false;
        else {
            this.returnDate = returnDate;
            this.librarianUsernameReturn = libUserReturn;
            this.status = false;
            return true;
        }
    }
    public boolean isReturned() {
        return (status == false);
    }
    public boolean filterRecordByUsername(String username) {
        if (this.username.equals(username) && !isReturned())
            return true;
        else
            return false;
    }
    
    public boolean filterRecordByUsernameAndISBN(String username, String bookISBN) {
        return this.username.equals(username) && !isReturned() && this.bookISBN.equals(bookISBN);
    }
    
    public boolean filterRecordByUsernameAndISBNAndCopyNumber(String username, String bookISBN, int bookCopyNumber) {
        return this.username.equals(username) && !isReturned() && this.bookISBN.equals(bookISBN) && this.bookCopyNumber==bookCopyNumber;
    }
    
    public boolean filterRecordByISBN(String bookISBN) {
        return this.bookISBN.equals(bookISBN);
    }
    
    public static int checkNumberOfBorrowedBooksByUsername(ArrayList<BorrowBookRecord> records, String username) {
        int numberOfBooks = 0;
        for(BorrowBookRecord record: records) {
            if(record.filterRecordByUsername(username))
                numberOfBooks ++;
        }
        return numberOfBooks;
    }
    
    public static int checkNumberOfBorrowedBooksByISBN(ArrayList<BorrowBookRecord> records, String bookISBN) {
        int numberOfBooks = 0;
        for(BorrowBookRecord record: records) {
            if(record.filterRecordByISBN(bookISBN))
                numberOfBooks ++;
        }
        return numberOfBooks;
    }
    public static ArrayList<BorrowBookRecord> getSortedBorrowRecords( ArrayList<BorrowBookRecord> recordList) { // not sort
        ArrayList<BorrowBookRecord> resultRecordList = new ArrayList<>();
        recordList.forEach((record) -> { 
            resultRecordList.add(record);
        });
        Collections.sort(resultRecordList);
        return resultRecordList;
    }
    public static String allRecordToString(ArrayList<BorrowBookRecord> recordList) {
        String text = "";
        text = recordList.stream().map((record) -> String.format("%s\n", record.toString())).reduce(text, String::concat);
        return text;
    }
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", this.bookISBN, this.bookCopyNumber, this.username, this.librarianUsernameBorrow, this.librarianUsernameReturn);
    }
    @Override
    public int compareTo(BorrowBookRecord other) {
        return this.username.compareTo(other.username);
    }
}
