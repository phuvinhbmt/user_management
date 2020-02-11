package assignment3;
import java.util.*;
public class ReservedBookRecord {
    private String username, bookISBN;
    private int bookCopyNumber;
    private Date reserveDate = new Date();
    private Date borrowDate = new Date();
    private boolean status = true;

    public ReservedBookRecord(String username, String bookISBN, int bookCopyNumber, Date reserveDate) {
        this.username = username;
        this.bookISBN = bookISBN;
        this.bookCopyNumber = bookCopyNumber;
        this.reserveDate = reserveDate;
        this.borrowDate = null;
        this.status = true;
    }

    
    public boolean receiveBook(Date borrowDate, String libUserBorrow) {
        if(this.isBorrowed()== true) 
            return false;
        else {
            this.borrowDate = borrowDate;
            this.status = false;
            return true;
        }
    }
    public boolean isBorrowed() {
        return (status == false);
    }
    public boolean filterRecordByUsername(String username) {
        return this.username.equals(username) && !isBorrowed();
    }
    
    public boolean filterRecordByUsernameAndISBN(String username, String bookISBN) {
        return this.username.equals(username) && !isBorrowed() && this.bookISBN.equals(bookISBN);
    }
    
    public boolean filterRecordByUsernameAndISBNAndCopyNumber(String username, String bookISBN, int bookCopyNumber) {
        return this.username.equals(username) && !isBorrowed() && this.bookISBN.equals(bookISBN) && this.bookCopyNumber==bookCopyNumber;
    }
    
    public boolean filterRecordByISBN(String bookISBN) {
        return this.bookISBN.equals(bookISBN);
    }
    
    public static int checkNumberOfReservedBooksByUsername(ArrayList<ReservedBookRecord> reservedRcordlist, String username) {
        int numberOfBooks = 0;
        numberOfBooks = reservedRcordlist.stream().filter((record) -> (record.filterRecordByUsername(username))).map((_item) -> 1).reduce(numberOfBooks, Integer::sum);
        return numberOfBooks;
    }
    
    public static int checkNumberOfReservedBooksByISBN(ArrayList<ReservedBookRecord> reservedRcordlist, String bookISBN) {
        int numberOfBooks = 0;
        numberOfBooks = reservedRcordlist.stream().filter((record) -> (record.filterRecordByISBN(bookISBN))).map((_item) -> 1).reduce(numberOfBooks, Integer::sum);
        return numberOfBooks;
    } 
    // display reserve records list
    public static String allRecordToString(ArrayList<ReservedBookRecord> recordList) {
        String text = "";
        for(ReservedBookRecord record: recordList) {
            text += String.format("%s\n", record.toString());
        }
        return text;
    }
    @Override
    public String toString() {
        return String.format("%s %s %s", this.bookISBN, this.bookCopyNumber, this.username);
    }
}
