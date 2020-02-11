package assignment3;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public  class UserMenuFrame extends JFrame{
    JTabbedPane tabbedPane;
    JPanel bookInfoPanel = new JPanel();
    JLabel isbnLabel, copyNumberLabel, titleLabel, author1Label, author2Label, locationLabel, bookTypeLabel, bookStatusLabel;
    JLabel idDisplayLabel, fnameDisplayLabel, lnameDisplayLabel, usernameDisplayLabel, passwordLabel, userTypeDisplayLabel, permissionTypeDisplayLabel, courseDisplayLabel, degreeDisplayLabel, salaryDisplayLabel, departmentDisplayLabel, positionDisplayLabel;
    Status[] bookStatus = {Status.Available, Status.Reserved, Status.Borrowed, Status.Damaged, Status.Deleted};
    PermissionType[] pTypeList = {PermissionType.BorrowAndEdit, PermissionType.Borrow, PermissionType.Reserve, PermissionType.Edit, PermissionType.None, PermissionType.All};

    //User menu1
    JPanel userPanel1, userPasswordPanel2, addressPanel1, userPanel3, userPanel4, userPanel5;
    JPasswordField newPasswordField, currentPasswordField;
    JLabel currentPassLabel, newPassLabel;
    JButton changePasswordButton, updateAddButton, searchBook, searchRecord,advancedSearchBookButton;
    JTextField titleField, isbnField, authorField, locationField;
    JTextField titleField3, authorField3;
    JTextArea advancedSearchResult, availableBookArea, borrowedRecordkArea, reservedRecordkArea, deletedBookArea, damagedBookArea;
    //User menu 5
    JList<String> reserveableBooks;
    JButton reserveButon;
    //User menu 1
    JLabel userInfoLabel1;
    JComboBox<State> stateComboBox;
    
    static State[] states = {State.NSW, State.VIC, State.TAS, State.QLD, State.SA, State.WA };
    JLabel unitLabel, streetNumberLabel, streetNameLabel, suburbLabel, cityLabel, stateLabel, postcodeLabel;
    JTextField unitField, streetNumberField, streetNameField, suburbField, cityField, postcodeField;
    //User menu 4
    JScrollPane advanceSearchDisplayScroll;
    JTextField keywordField;
     
    public UserMenuFrame(String text) {
        super(text);                
        userMenu1();
        userMenu2();
        userMenu3();
        userMenu4();
        userMenu5();
        
        tabbedPane = new JTabbedPane();
        
        //Whole frame, add tab panel
        tabbedPane.addTab("User menu 1", null, userPanel1, "Display and edit information");
        tabbedPane.addTab("User menu 2", null, userPasswordPanel2, "Change password");
        tabbedPane.addTab("User menu 3", null, userPanel3, "Search books");
        tabbedPane.addTab("User menu 4", null, userPanel4, "Advanced search books");
        tabbedPane.addTab("User menu 5", null, userPanel5, "Reserve book");
        add(tabbedPane);
        
        this.setVisible(true);
        this.setSize(450, 400);
        this.setBounds(500, 200, 450, 490);
    }
    public void userMenu1() {
        userPanel1 = new JPanel();
        addressPanel1 = new JPanel();
        userInfoLabel1 = new JLabel();
        
        unitLabel = new JLabel("Unit");
        streetNumberLabel = new JLabel("Number");
        streetNameLabel = new JLabel("Street");
        suburbLabel = new JLabel("Suburb");
        cityLabel = new JLabel("City");
        stateLabel = new JLabel("State");
        postcodeLabel = new JLabel("Postcode");
        
        unitField = new JTextField(15);
        streetNumberField = new JTextField(15);
        streetNameField = new JTextField(15);
        suburbField = new JTextField(15);
        cityField = new JTextField(15);
        stateComboBox = new JComboBox<>(states);
        postcodeField = new JTextField(15);
        updateAddButton = new JButton("Update address");
        
        this.addressPanel1.setLayout(new GridLayout(8,2));
        this.addressPanel1.add(unitLabel);
        this.addressPanel1.add(unitField);
        this.addressPanel1.add(streetNumberLabel);
        this.addressPanel1.add(streetNumberField);
        this.addressPanel1.add(streetNameLabel);
        this.addressPanel1.add(streetNameField);
        this.addressPanel1.add(suburbLabel);
        this.addressPanel1.add(suburbField);
        this.addressPanel1.add(cityLabel);
        this.addressPanel1.add(cityField);
        this.addressPanel1.add(stateLabel);
        this.addressPanel1.add(stateComboBox);
        this.addressPanel1.add(postcodeLabel);
        this.addressPanel1.add(postcodeField);
        
        userPanel1.add(userInfoLabel1);
        userPanel1.add(addressPanel1);
        userPanel1.add(updateAddButton);
    }
    public void userMenu2() {
        userPasswordPanel2 = new JPanel();
        newPasswordField = new JPasswordField(20);
        currentPasswordField = new JPasswordField(20);
        currentPassLabel = new JLabel("Current password:");
        newPassLabel = new JLabel("New password:");
        changePasswordButton = new JButton("Change password");
        
        userPasswordPanel2.setLayout(new FlowLayout());
        userPasswordPanel2.add(currentPassLabel);
        userPasswordPanel2.add(currentPasswordField);
        userPasswordPanel2.add(newPassLabel);
        userPasswordPanel2.add(newPasswordField);
        userPasswordPanel2.add(changePasswordButton);
    }
    public void userMenu3() {
        userPanel3 = new JPanel();
        titleField3 = new JTextField("Title",18);
        authorField3 = new JTextField("Author",18);
        searchBook = new JButton("Search Book");
        searchRecord = new JButton("Search Record");
        availableBookArea = new JTextArea(4, 35);
        availableBookArea.setEditable(false);
        
        deletedBookArea = new JTextArea(4, 35);
        deletedBookArea.setEditable(false);
        
        damagedBookArea = new JTextArea(4, 35);
        damagedBookArea.setEditable(false);
        
        reservedRecordkArea = new JTextArea(2, 35);
        reservedRecordkArea.setEditable(false);
        
        borrowedRecordkArea = new JTextArea(2, 35);
        borrowedRecordkArea.setEditable(false);    
        
        userPanel3.add(titleField3);
        userPanel3.add(authorField3);
        userPanel3.add(searchBook);
        userPanel3.add(searchRecord);
        userPanel3.add(new JLabel("Available books"));
        userPanel3.add(new JScrollPane(availableBookArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        userPanel3.add(new JLabel("Deleted books"));
        userPanel3.add(new JScrollPane(deletedBookArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        userPanel3.add(new JLabel("Damaged books"));
        userPanel3.add(new JScrollPane(damagedBookArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
//        userPanel3.add(new JLabel("Reserved records"));
//        userPanel3.add(reservedRecordkArea);
//        userPanel3.add(new JLabel("Borrowed records"));
//        userPanel3.add(borrowedRecordkArea);
    }
    public void userMenu4() {
        userPanel4 = new JPanel();
//        isbnField = new JTextField(18);
//        titleField = new JTextField(18);
//        authorField = new JTextField(18);
//        locationField = new JTextField(18);
        keywordField = new JTextField(18);
        advancedSearchBookButton = new JButton("Adanced search");
        advancedSearchResult = new JTextArea(15, 37);
        advancedSearchResult.setEditable(false);
        
        advanceSearchDisplayScroll = new JScrollPane(advancedSearchResult,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        bookInfoPanel.setLayout(new GridLayout(4,2));
//        bookInfoPanel.add(isbnLabel);
//        bookInfoPanel.add(isbnField);
//        bookInfoPanel.add(titleLabel);
//        bookInfoPanel.add(titleField);
//        bookInfoPanel.add(new JLabel("List of Authors"));
//        bookInfoPanel.add(authorField);
//        bookInfoPanel.add(locationLabel);
//        bookInfoPanel.add(locationField);
//        userPanel4.add(bookInfoPanel);
        userPanel4.add(new JLabel("Keyword"));
        userPanel4.add(keywordField);
        userPanel4.add(advancedSearchBookButton);
        userPanel4.add(advanceSearchDisplayScroll);
    }

    public void userMenu5() {
        userPanel5 = new JPanel();
        this.reserveButon = new JButton("Reserve");
        this.reserveableBooks = new JList<>();
        userPanel5.add(new JScrollPane(this.reserveableBooks,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        userPanel5.add(reserveButon);
    }
    public void initBookInfoLabel() {
        bookInfoPanel = new JPanel();
        isbnLabel = new JLabel("ISBN");
        copyNumberLabel = new JLabel("Copy Number");
        titleLabel = new JLabel("Title");
        author1Label = new JLabel("Author 1");
        author2Label = new JLabel("Author 2");
        locationLabel = new JLabel("Location");
        bookTypeLabel = new JLabel("Type of Book");
        bookStatusLabel = new JLabel("Status of Book");
    }
    public void initUserInfoLabel() {
        idDisplayLabel = new JLabel("ID");
        fnameDisplayLabel = new JLabel("First name");
        lnameDisplayLabel = new JLabel("Last name");
        usernameDisplayLabel = new JLabel("User name");
        passwordLabel = new JLabel("Password");
        userTypeDisplayLabel = new JLabel("Type of User");
        permissionTypeDisplayLabel = new JLabel("Type of Permission");
        courseDisplayLabel = new JLabel("Course of Student");
        degreeDisplayLabel = new JLabel("Degree of Student");
        salaryDisplayLabel = new JLabel("Salary of Staff");
        departmentDisplayLabel = new JLabel("Department of Staff");
        positionDisplayLabel = new JLabel("Position of Staff");
    }
}
