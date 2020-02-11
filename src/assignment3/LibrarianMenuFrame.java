package assignment3;
import java.awt.*;
import javax.swing.*;
public class LibrarianMenuFrame extends UserMenuFrame{
    JPanel libPanel1, libPanel2, libPanel5;
    //menu1
    JButton searchBook1Button, editBook1Button;
    JTextField isbnEditField1, titleField1, author1Field1, author2Field1, bookTypeField1, locationField1;
    JTextField isbnSearchField1;
    JComboBox<Status> bookStatusComboBox;
    //menu2
    JButton deleteBook, createBook, copyBook;
    JTextField isbnField2, copyNumberField2;
    JList<String> bookResult;
    JTextField isbnEditField2, titleField2, author1Field2, author2Field2, bookTypeField2, locationField2,copyNumberEditField2;
    JComboBox<Status> bookStatusComboBox2;
    //menu5
    JButton reportRecord;
    JTextArea borrowRecordResult;
    public LibrarianMenuFrame() {
        super("Librarian");
        this.libPanel2();
        this.libPanel1();
        this.libPanel5();
        
        tabbedPane.addTab("Librarian menu 1", null, libPanel1, "Edit book");
        tabbedPane.addTab("Librarian menu 2", null, libPanel2, "Create or delete book");
        tabbedPane.addTab("Librarian menu 5", null, libPanel5, "Report and advance search");
    }
    public void libPanel1() {
        libPanel1 = new JPanel();
        bookInfoPanel = new JPanel();
        initBookInfoLabel();
        
        isbnEditField1 = new JTextField(18); 
        titleField1 = new JTextField(18);
        author1Field1 = new JTextField(18);
        author2Field1 = new JTextField(18);
        bookTypeField1 = new JTextField(18);
        locationField1 = new JTextField(18);
        bookStatusComboBox = new JComboBox<>(bookStatus);
        bookStatusComboBox.setEditable(false);
                
        this.searchBook1Button = new JButton("Search");
        this.editBook1Button = new JButton("Edit Book");
        this.isbnSearchField1 = new JTextField("isbnSearch", 20);
        
        libPanel1.add(new JLabel("ISBN"));
        libPanel1.add(isbnSearchField1);
        libPanel1.add(searchBook1Button);
        
        bookInfoPanel.setLayout(new GridLayout(8,2));
        bookInfoPanel.add(isbnLabel);
        bookInfoPanel.add(isbnEditField1);
        bookInfoPanel.add(titleLabel);
        bookInfoPanel.add(titleField1);
        bookInfoPanel.add(author1Label);
        bookInfoPanel.add(author1Field1);
        bookInfoPanel.add(author2Label);
        bookInfoPanel.add(author2Field1);
        bookInfoPanel.add(bookTypeLabel);
        bookInfoPanel.add(bookTypeField1);
        bookInfoPanel.add(locationLabel);
        bookInfoPanel.add(locationField1);
        libPanel1.add(bookInfoPanel);
        libPanel1.add(editBook1Button);
        
    }
    
    public void libPanel2() {
        libPanel2 = new JPanel();
        initBookInfoLabel();
        
        deleteBook = new JButton("Delete");
        createBook = new JButton("Create");
        copyBook = new JButton("Copy");
        isbnField2 = new JTextField(30);
        copyNumberField2 = new JTextField(30);
        bookResult = new JList<>();
        isbnEditField2 = new JTextField("isbn", 18); 
        titleField2 = new JTextField("title", 18);
        author1Field2 = new JTextField("author1", 18);
        author2Field2 = new JTextField("author2", 18);
        bookTypeField2 = new JTextField("book type", 18);
        bookStatusComboBox2 = new JComboBox(bookStatus);
        locationField2 = new JTextField("location", 18);
        copyNumberEditField2 = new JTextField("copyNumber", 18);
        
        libPanel2.add(new JLabel("ISBN"));
        libPanel2.add(isbnField2);
        libPanel2.add(new JLabel("Copy number"));
        libPanel2.add(copyNumberField2);
        libPanel2.add(createBook);
        libPanel2.add(deleteBook);
        libPanel2.add(copyBook);
        libPanel2.add(new JScrollPane(bookResult));
        
        bookInfoPanel.setLayout(new GridLayout(9,2));
        bookInfoPanel.add(isbnLabel);
        bookInfoPanel.add(isbnEditField2);
        bookInfoPanel.add(titleLabel);
        bookInfoPanel.add(titleField2);
        bookInfoPanel.add(author1Label);
        bookInfoPanel.add(author1Field2);
        bookInfoPanel.add(author2Label);
        bookInfoPanel.add(author2Field2);
        bookInfoPanel.add(bookTypeLabel);
        bookInfoPanel.add(bookTypeField2);
        bookInfoPanel.add(bookStatusLabel);
        bookInfoPanel.add(bookStatusComboBox2);
        bookInfoPanel.add(locationLabel);
        bookInfoPanel.add(locationField2);
        bookInfoPanel.add(copyNumberLabel);
        bookInfoPanel.add(copyNumberEditField2);
        libPanel2.add(bookInfoPanel);
    }
    public void libPanel5() {
        libPanel5 = new JPanel();
        reportRecord = new JButton("Report Records");
        borrowRecordResult = new JTextArea("Click button to report borrow records. records are sorted by username",5,34);
        JScrollPane borrowRecordResultScroll = new JScrollPane(borrowRecordResult,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        libPanel5.add(reportRecord);
        libPanel5.add(borrowRecordResultScroll);
    }
    
}
