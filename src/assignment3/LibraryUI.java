package assignment3;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
public class LibraryUI {
    static Scanner input;
    static java.util.Formatter studentOutput, bookOutput, staffOutput, borrowRecordOutput, reserveRecordOutput;
    String currentLoginUser = null;
    Library library = new Library();
    static String currentFile = null;
    UserMenuFrame userFrame;
    StaffMenuFrame staffFrame;
    AdminMenuFrame adminFrame;
    LibrarianMenuFrame libFrame ;
    public static void main(String args[]) {
        LibraryUI librarySystem = new LibraryUI();
        LibraryUI.openFile("Student.txt");
        librarySystem.initData();
        LibraryUI.openFile("Staff.txt");
        librarySystem.initData();
        LibraryUI.openFile("Book.txt");
        librarySystem.initData();
        LibraryUI.openFile("BorrowRecord.txt");
        librarySystem.initData();
        LibraryUI.openFile("ReserveRecord.txt");
        librarySystem.initData();
//        librarySystem.test();
        librarySystem.menuLogin();
        
    }
    
    public  void test() {
        int counter = 0;
        for(User user: library.getUsers()){
            if(user.getClass().getName().equals("assignment3.Student")){
                System.out.println(user);
                counter++;
            }
        }
        System.out.println("total is " +counter);
        System.out.printf("=====");
    }
    public static void openFile(String fileLocation) {
         try {
            input = new Scanner(Paths.get(fileLocation));
        } catch (IOException ex) {
        }
        currentFile = fileLocation;
    }

    public void initData() {
        try {
            int counter = 0;
            while (input.hasNext()) {
                String eachLine = input.nextLine();
                String[] parameter = eachLine.split(",|\\;");
                switch (currentFile) {
                    case "Student.txt":
                        Student newStudent = new Student(parameter[0], parameter[1], parameter[2], parameter[3], parameter[4], UserType.valueOf(parameter[5]), PermissionType.Reserve, parameter[7], parameter[8]);
                        library.addUser(newStudent);
                        if (parameter.length > 10) {
                            Address address = new Address(Integer.parseInt(parameter[9]), Integer.parseInt(parameter[10]), parameter[11], parameter[12], parameter[13], State.valueOf(parameter[14]), Integer.parseInt(parameter[15]));
                            newStudent.setAddress(address);                            
                        }
                        break;
                    case "Staff.txt":
                        Staff newStaff = new Staff(parameter[0], parameter[1], parameter[2], parameter[3], parameter[4], UserType.valueOf(parameter[5]), PermissionType.valueOf(parameter[6]), parameter[7], Double.parseDouble(parameter[9]), Department.initDepartment(parameter[8]));
                        library.addUser(newStaff);
                        if (parameter.length > 11) {
                            Address address = new Address(Integer.parseInt(parameter[10]), Integer.parseInt(parameter[11]), parameter[12], parameter[13], parameter[14], State.valueOf(parameter[15]), Integer.parseInt(parameter[16]));
                            newStaff.setAddress(address);
                        }
//                        library.addUser(new Staff(parameter[0], parameter[1], parameter[2], parameter[3], parameter[4], UserType.valueOf(parameter[5]), PermissionType.valueOf(parameter[6]), parameter[7], Double.parseDouble(parameter[9]), Department.initDepartment(parameter[8])));
                        break;
                    case "Book.txt":
                        library.addBook(new Book(parameter[0], parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], Status.valueOf(parameter[6]), Integer.parseInt(parameter[7])));
                        break;
//                    case "BorrowRecord.txt":
//                        BorrowBookRecord borrowRecord = new BorrowBookRecord(parameter[0], parameter[1], Integer.parseInt(parameter[2]), null, parameter[4]);
//                        library.getBorrowRecords().add(borrowRecord);
//                        break;
//                    case "ReserveRecord.txt":
//                        ReservedBookRecord reserveRecord = new ReservedBookRecord(parameter[0], parameter[1], Integer.parseInt(parameter[2]), null);
//                        library.getReserveRecords().add(reserveRecord);
//                        break;
                }
            }
        } catch (NoSuchElementException ex) {
            System.err.println("File not formed");
        } catch (IllegalStateException ex) {
            System.err.println("File not form");
        }

    }

    public void menuLogin() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.loginButton.addActionListener(e -> {
            if (library.verifyLogin(loginFrame.usernameField.getText(), loginFrame.passwordField.getText())) {
                JOptionPane.showMessageDialog(loginFrame, "Login succeedsUI");
                currentLoginUser = loginFrame.usernameField.getText();
                this.mainMenuAfterLogin();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Login failsUI");
                loginFrame.usernameField.setText("");
                loginFrame.passwordField.setText("");
            }
        });
        loginFrame.saveChange.addActionListener((ActionEvent e) -> {
            openOutFile();
            addRecords();
            closeFile();
            System.out.println("All changes have been saved");
        });
    }
    public void commitChange() {
        
    }
    public void mainMenuAfterLogin() {
        User currentUser = User.findUserByUsername(library.getUsers(), currentLoginUser);
        if(currentUser.getUserType().getName().contains("Student")) {
            userFrame = new UserMenuFrame("User frame");
            this.menuForChangePassword();
            this.displayUserInformation();
            this.updateAddress();
            this.advancedSearch();
            this.standardSearch();
        }
            
        else if (currentUser.getUserType().getName().contains("Staff")) {
            userFrame = new StaffMenuFrame();
            displayUserFullName();
            this.menuForChangePassword();
            this.displayUserInformation();
            this.updateAddress();
            this.advancedSearch();
            this.standardSearch();
            this.reserveBook();

        }
        else if(currentUser.getUserType().getName().contains("Administrator")) {
            userFrame = new AdminMenuFrame();
            findAndEditUserInfoAdmin1();
            deactiveUser();
            createUser();
            reportActiveUsers();
                    
            this.menuForChangePassword();
            this.displayUserInformation();
            this.updateAddress();
            this.advancedSearch();
            this.standardSearch();
            this.reserveBook();
        }    
        else if(currentUser.getUserType() == UserType.Librarian) {
            userFrame = new LibrarianMenuFrame();
            createOrDeleteBook();
            editBookInfo();
            reportBorrowRecord();
            
            this.menuForChangePassword();
            this.displayUserInformation();
            this.updateAddress();
            this.advancedSearch();
            this.standardSearch();
            this.reserveBook();
        }
    }

    public void reportBorrowRecord() {
        libFrame.reportRecord.addActionListener((ActionEvent e)->{
            libFrame.borrowRecordResult.setText(BorrowBookRecord.allRecordToString(BorrowBookRecord.getSortedBorrowRecords(library.getBorrowRecords())));
            System.out.println(BorrowBookRecord.allRecordToString(BorrowBookRecord.getSortedBorrowRecords(library.getBorrowRecords())));
        });
    }
    public void menuForChangePassword() {
        userFrame.changePasswordButton.addActionListener((ActionEvent e)->{
            String currentPassword = String.valueOf(userFrame.currentPasswordField.getPassword());
            String newPassword = String.valueOf(userFrame.newPasswordField.getPassword());
            boolean passwordChanged = true;
            try {
                passwordChanged = library.changePassword(currentLoginUser, currentPassword, newPassword);
            } 
            catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(userFrame, ex.getMessage());
                passwordChanged = false;
            }
            catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(userFrame, ex.getMessage());
                passwordChanged = false;
                System.out.println("hello");
            }
            if(passwordChanged)
                JOptionPane.showMessageDialog(userFrame, "Password has been changed" );
            else 
                JOptionPane.showMessageDialog(userFrame, "Password is not changed");
            ;
            System.out.println("password after change is "+ User.findUserByUsername(library.getUsers(), currentLoginUser).getPassword());
        });
    }
    User currentSearchingUser;
    public void findAndEditUserInfoAdmin1() {
        adminFrame = (AdminMenuFrame) userFrame;
        adminFrame.searchButton.addActionListener((ActionEvent e) -> {
            ArrayList<User> matchedUsers = new ArrayList<>();
            String fname = adminFrame.firstNameField.getText();
            String lname = adminFrame.lastNameField.getText();
            try {
                if (fname.equals("")) {
                    currentSearchingUser = library.getUserByLastName(lname);
                } else if (lname.equals("")) {
                    currentSearchingUser = library.getUserByFirstName(fname);
                } else {
                    currentSearchingUser = library.getUserByUserFullName(fname + " " + lname);
                }
                String[] users = {currentSearchingUser.getFullname()};
                adminFrame.userList1.setListData(users);

            } catch(NullPointerException ex) {
                JOptionPane.showMessageDialog(userFrame, "No User found");
            }
            
//            try {
//                String matchedUsername = library.getUserNameByUserFullName(fname, lname);
//                String[] users = {currentSearchingUser.getFullname()};
//                adminFrame.userList1.setListData(users);
//                
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(adminFrame, "User not found");
//            }
        });
        adminFrame.userList1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    adminFrame.displayUserInfo(currentSearchingUser);
//                    adminFrame.adminMenu1.add(adminFrame.userInfoPanel, BorderLayout.SOUTH);
                }
            }
        });
        adminFrame.editUserButon.addActionListener((ActionEvent e) -> {
            PermissionType[] pTypes = {PermissionType.BorrowAndEdit, PermissionType.Reserve, PermissionType.Borrow, PermissionType.Edit, PermissionType.None, PermissionType.All};
            String id = adminFrame.idDisplayField.getText();
            String fname = adminFrame.fnameDisplayField.getText();
            String lname = adminFrame.lnameDisplayField.getText();
            String username= adminFrame.usernameDisplayField.getText();
            PermissionType permissionType = pTypes[adminFrame.permissionTypeComboBox.getSelectedIndex()];
            String degree= adminFrame.degreeDisplayField.getText();
            String course= adminFrame.courseDisplayField.getText();
            
            currentSearchingUser.setId(id);
            currentSearchingUser.setFirstName(fname);
            currentSearchingUser.setLastName(lname);
            
            if(currentSearchingUser.getClass().getName().equals("assignment3.Student")) {
                Student std = (Student) currentSearchingUser;
                System.out.println("UI"+permissionType.name());
                try {
                    std.setPermissiontype(permissionType);
                    std.setId(id);
                    std.setFirstName(fname);
                    std.setLastName(lname);
                    std.setUsername(username);
                    std.setDegree(degree);
                    std.setCourse(course);
                    System.out.println(std.getPermissionType() == permissionType);
                } catch(IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(userFrame, ex.getMessage());
                }
                
            }
            else if (currentSearchingUser.getClass().getName().equals("assignment3.Staff")) {
                Staff sff = (Staff) currentSearchingUser;
                try {
                    double salary = Double.parseDouble(adminFrame.salaryDisplayField.getText());
                    String position = adminFrame.positionDisplayField.getText();
                    Department department = (Department) adminFrame.departmentComboBox1.getSelectedItem();
                    sff.setDepartment(department);
                    sff.setPosition(position);
                    sff.setSalary(salary);
                } catch(NumberFormatException   ex) {
                    JOptionPane.showMessageDialog(userFrame, ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(userFrame, ex.getMessage());
                }
                
            }
//            currentSearchingUser.setPermissiontype(PermissionType.valueOf(adminFrame.userInfoField[4].getText()));
        });
    }

    public void createOrDeleteBook() {
        libFrame = (LibrarianMenuFrame) userFrame;
        class ButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == libFrame.createBook) {
                    System.out.println("hey hey");
                    String newIsbn = libFrame.isbnEditField2.getText();
                    String newTitle = libFrame.titleField2.getText();
                    String newAuthor1 = libFrame.author1Field2.getText();
                    String newAuthor2 = libFrame.author2Field2.getText();
                    String newBookType = libFrame.bookTypeField2.getText();
                    String newLocation = libFrame.locationField2.getText();
                    String copyNumberEditField2 = libFrame.copyNumberEditField2.getText();
                    ArrayList<String> authors = new ArrayList<>();
                    authors.add(newAuthor1);
                    authors.add(newAuthor2);
                    try {
                        Book newBook = new Book(newIsbn, newTitle, newAuthor1, newAuthor2, newLocation, newBookType, (Status)libFrame.bookStatusComboBox2.getSelectedItem(), Integer.parseInt(copyNumberEditField2));
                        library.addBook(newBook);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(userFrame, "Opps, please enter a number");
                    }
                    
                } else {
                    Book currentBook = null;
                    try {
                        currentBook = Book.findBookByISBNandCopyNumber(library.getBooks(), libFrame.isbnField2.getText(), Integer.parseInt(libFrame.copyNumberField2.getText()));
                        if (e.getSource() == libFrame.deleteBook) {
                            System.out.println(currentBook);
                            if (currentBook.getBookStatus() == Status.Borrowed || currentBook.getBookStatus() == Status.Reserved) {
                                JOptionPane.showMessageDialog(userFrame, "Book is reserved or borrowed");
                            } else {
//                            Book.findBookByISBNandCopyNumber(library.getBooks(), libFrame.isbnField2.getText(), Integer.parseInt(libFrame.copyNumberField2.getText())).setBookStatus(Status.Deleted);
                                currentBook.setBookStatus(Status.Deleted);
                                if (currentBook.isDeleted()) {
                                    JOptionPane.showMessageDialog(userFrame, "Wowww, Book has been deleted");
                                }
                            }
                        } else if (e.getSource() == libFrame.copyBook) {
                            System.out.println(currentBook);
                            String[] bookResult = {currentBook.toString()};
                            libFrame.bookResult.setListData(bookResult);
                        try {
                            library.addBook(currentBook.clone());
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    } catch (Exception  ex) {
                        JOptionPane.showMessageDialog(userFrame, "No Book Found");
                    }
                    
                }

            }

        }
        ButtonHandler buttonHandler = new ButtonHandler();
        libFrame.deleteBook.addActionListener(buttonHandler);
        libFrame.copyBook.addActionListener(buttonHandler);
        libFrame.createBook.addActionListener(buttonHandler);
        
    }
    public void deactiveUser() {
        library.getListOfUserFullName();
        
        String[] users = library.getListOfUserFullName().toArray( new String[library.getListOfUserFullName().size()]);        
        adminFrame.updateListButton.addActionListener((ActionEvent e)-> {
            adminFrame.userList2.setListData((users));
            adminFrame.totalUsersField.setText(""+library.getUsers().size());
        });
        adminFrame.activeButton.addActionListener((ActionEvent e)-> {
            for(String eachFullName: adminFrame.userList2.getSelectedValuesList()) {
                library.getUserByUserFullName(eachFullName).setStatus(true);
            }
            
        });
        
        adminFrame.deactiveButton.addActionListener((ActionEvent e)-> {
            adminFrame.userList2.getSelectedValuesList().forEach((eachFullName) -> {
                library.getUserByUserFullName(eachFullName).setStatus(false);
            });
            
        });
    }
    
    public void createUser() {
        class UserRadioButtonHandler implements ItemListener {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (adminFrame.studentRadioButton.isSelected()) {
                    UserType[] studentsTypes = {UserType.PostStudent, UserType.Student};
                    PermissionType[] studentPTypes = {PermissionType.Reserve, PermissionType.None};
                    adminFrame.permissionTypeComboBox2 = new JComboBox<>(studentPTypes);
                    adminFrame.userTypeComboBox2 = new JComboBox<>(studentsTypes);
                    JFrame studentFrame = new JFrame("Create student");
                    studentFrame.setVisible(true);
                    studentFrame.setBounds(500, 200, 350, 390);
                    
                    studentFrame.setLayout(new GridLayout(10, 2));
                    studentFrame.add(adminFrame.idDisplayLabel);
                    studentFrame.add(adminFrame.idDisplayField2);
                    studentFrame.add(adminFrame.fnameDisplayLabel);
                    studentFrame.add(adminFrame.fnameDisplayField2);
                    studentFrame.add(adminFrame.lnameDisplayLabel);
                    studentFrame.add(adminFrame.lnameDisplayField2);
                    studentFrame.add(adminFrame.usernameDisplayLabel);
                    studentFrame.add(adminFrame.usernameDisplayField2);
                    studentFrame.add(adminFrame.passwordLabel);
                    studentFrame.add(adminFrame.passwordDisplayField2);
                    studentFrame.add(adminFrame.userTypeDisplayLabel);
                    studentFrame.add(adminFrame.userTypeComboBox2);
                    studentFrame.add(adminFrame.permissionTypeDisplayLabel);
                    studentFrame.add(adminFrame.permissionTypeComboBox2);
                    studentFrame.add(adminFrame.courseDisplayLabel);
                    studentFrame.add(adminFrame.courseDisplayField2);
                    studentFrame.add(adminFrame.degreeDisplayLabel);
                    studentFrame.add(adminFrame.degreeDisplayField2);
                    studentFrame.add(adminFrame.createStudentButton);
                } else if (adminFrame.staffRadioButton.isSelected()) {
                    UserType[] staffTypes = {UserType.AcademicStaff, UserType.Admin, UserType.AdminStaff, UserType.Librarian, UserType.Staff};
//                    PermissionType[] staffPermissionTypes = {PermissionType.};
                    adminFrame.userTypeComboBox2 = new JComboBox<>(staffTypes);
                    JFrame staffFrame = new JFrame("Create staff");
                    staffFrame.setVisible(true);
                    staffFrame.setBounds(500, 200, 350, 390);
                    
                    staffFrame.setLayout(new GridLayout(11, 2));
                    staffFrame.add(adminFrame.idDisplayLabel);
                    staffFrame.add(adminFrame.idDisplayField2);
                    staffFrame.add(adminFrame.fnameDisplayLabel);
                    staffFrame.add(adminFrame.fnameDisplayField2);
                    staffFrame.add(adminFrame.lnameDisplayLabel);
                    staffFrame.add(adminFrame.lnameDisplayField2);
                    staffFrame.add(adminFrame.usernameDisplayLabel);
                    staffFrame.add(adminFrame.usernameDisplayField2);
                    staffFrame.add(adminFrame.passwordLabel);
                    staffFrame.add(adminFrame.passwordDisplayField2);
                    staffFrame.add(adminFrame.userTypeDisplayLabel);
                    staffFrame.add(adminFrame.userTypeComboBox2);
                    staffFrame.add(adminFrame.permissionTypeDisplayLabel);
                    staffFrame.add(adminFrame.permissionTypeComboBox2);
                    staffFrame.add(adminFrame.departmentDisplayLabel);
                    staffFrame.add(adminFrame.departmentComboBox);
                    staffFrame.add(adminFrame.positionDisplayLabel);
                    staffFrame.add(adminFrame.positionField2);
                    staffFrame.add(adminFrame.salaryDisplayLabel);
                    staffFrame.add(adminFrame.salaryDisplayField2);
                    staffFrame.add(adminFrame.createStaffButton);
                    
//                    adminFrame.adminMenu2.add(userInfoPanel2, BorderLayout.SOUTH);
                }
            }

        }
        UserRadioButtonHandler userRadioButtonHandler = new UserRadioButtonHandler();
        adminFrame.staffRadioButton.addItemListener(userRadioButtonHandler);
        adminFrame.studentRadioButton.addItemListener(userRadioButtonHandler);
        adminFrame.createStaffButton.addActionListener((ActionEvent e)->{
            try{
                String id = adminFrame.idDisplayField2.getText();
                String fname = adminFrame.fnameDisplayField2.getText();
                String lname = adminFrame.lnameDisplayField2.getText();
                String username = adminFrame.usernameDisplayField2.getText();
                String password = adminFrame.passwordDisplayField2.getText();
                UserType userType = (UserType) adminFrame.userTypeComboBox2.getSelectedItem();
                PermissionType permissionType = (PermissionType) adminFrame.permissionTypeComboBox2.getSelectedItem();
                Department department = (Department) adminFrame.departmentComboBox.getSelectedItem();
                String position = adminFrame.positionField2.getText();
                double salary = Double.parseDouble(adminFrame.salaryDisplayField2.getText());
                Staff staff = new Staff(id, fname, lname, username, password, userType, permissionType, position, salary, department);
                library.addUser(staff);
                if(library.getUsers().contains(staff)) {
                    JOptionPane.showMessageDialog(userFrame, "Congrats! new staff created");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(userFrame, "Opps! enter a number please");
            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(userFrame, ex.getMessage());
            }
        });
        
        adminFrame.createStudentButton.addActionListener((ActionEvent e)->{
            try{
                String id = adminFrame.idDisplayField2.getText();
                String fname = adminFrame.fnameDisplayField2.getText();
                String lname = adminFrame.lnameDisplayField2.getText();
                String username = adminFrame.usernameDisplayField2.getText();
                String password = adminFrame.passwordDisplayField2.getText();
                UserType userType = (UserType) adminFrame.userTypeComboBox2.getSelectedItem();
                PermissionType permissionType = (PermissionType) adminFrame.permissionTypeComboBox2.getSelectedItem();
                String course = adminFrame.courseDisplayField2.getText();
                String degree = adminFrame.degreeDisplayField2.getText();
                Student student = new Student(id, fname, lname, username, password, userType, permissionType, course, degree);
                library.addUser(student);
                if(library.getUsers().contains(student)) {
                    JOptionPane.showMessageDialog(userFrame, "Congrats! new student created");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(userFrame, "Opps! Enter a number please");
            }
        });
    }
    public void reportActiveUsers() {
        adminFrame.reportButton.addActionListener((ActionEvent e)-> {
            int total = 0;
            String display = "";
            for(User eachUser: library.findUserByStatus(true)) {
                display += String.format("%s\n", eachUser.getFullname());
                total++;
            }
            adminFrame.activeUserTextArea.setText(display);
            adminFrame.totalActiveUsers.setText(String.valueOf(total));
        });
    }
    public void displayUserInformation() {
        userFrame.userInfoLabel1.setText(library.getUserInfoByUserName(currentLoginUser));
    }
    
    public void standardSearch() {
        userFrame.searchBook.addActionListener((ActionEvent e) -> {
            String title = userFrame.titleField3.getText();
            String author = userFrame.authorField3.getText();
            ArrayList<Book> allMatchedBookList = new ArrayList<>();
            try {
                if (title.isEmpty() && !author.isEmpty()) {
                    allMatchedBookList = library.findBookByAuthorName(author);
                } else if (!title.isEmpty() && author.isEmpty()) {
//                    allMatchedBookList = library.findBookByTitle(title);
                    allMatchedBookList = library.findBooks(b -> b.getTitle().equals(title));
                } else if (!title.isEmpty() && !author.isEmpty()) {
                    allMatchedBookList = library.findBookByTitle(title);
                    for (Book matchedbook : library.findBookByAuthorName(author)) {
                        allMatchedBookList.add(matchedbook);
                    }
                }
                ArrayList<Book> availableBookList = Book.findBookByStatus(allMatchedBookList, Status.Available);
                ArrayList<Book> deletedBookList = Book.findBookByStatus(allMatchedBookList, Status.Deleted);
                ArrayList<Book> damagedBookList = Book.findBookByStatus(allMatchedBookList, Status.Damaged);
                userFrame.availableBookArea.setText(Book.allBookToString(availableBookList));
                userFrame.deletedBookArea.setText(Book.allBookToString(deletedBookList));
                userFrame.damagedBookArea.setText(Book.allBookToString(damagedBookList));
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(userFrame, "Opps: You can't find any books. MUAHAHA!");
                userFrame.availableBookArea.setText("");
                userFrame.deletedBookArea.setText("");
                userFrame.damagedBookArea.setText("");
            }

        });
        userFrame.searchRecord.addActionListener((ActionEvent e) -> {
            userFrame.borrowedRecordkArea.setText(BorrowBookRecord.allRecordToString(library.findBorrowRecordByUsername(currentLoginUser)));
            userFrame.reservedRecordkArea.setText(ReservedBookRecord.allRecordToString(library.findReserveRecordByUsername(currentLoginUser)));

        });
    }
    public void advancedSearch(){
        
        userFrame.advancedSearchBookButton.addActionListener((ActionEvent e) -> {
            String keyword = userFrame.keywordField.getText();
        });
    }
        
    public void updateAddress() {
        User currentUser = User.findUserByUsername(library.getUsers(), currentLoginUser);
            if(!currentUser.hasNoAddress()) {
                userFrame.unitField.setText(""+currentUser.getAddress().getUnit());
                userFrame.streetNumberField.setText(""+currentUser.getAddress().getStreetNumber());
                userFrame.streetNameField.setText(currentUser.getAddress().getStreet());
                userFrame.suburbField.setText(currentUser.getAddress().getSuburb());
                userFrame.cityField.setText(currentUser.getAddress().getCity());
                userFrame.stateComboBox.setSelectedItem(currentUser.getAddress().getState());
                userFrame.postcodeField.setText(""+currentUser.getAddress().getPostCode());
            }
        userFrame.updateAddButton.addActionListener((ActionEvent e) -> {
            
            State[] states = {State.NSW, State.VIC, State.TAS, State.QLD, State.SA, State.WA};
            int unit = 0;
            int streetNumber = 0;
            int postCode = 0;
            try {
                unit = Integer.parseInt(userFrame.unitField.getText());
                streetNumber = Integer.parseInt(userFrame.streetNumberField.getText());
                postCode = Integer.parseInt(userFrame.postcodeField.getText());
                String street = userFrame.streetNameField.getText();
            String suburb = userFrame.suburbField.getText();
            String city = userFrame.cityField.getText();
            User.findUserByUsername(library.getUsers(), currentLoginUser).setAddress(new Address(unit, streetNumber, street, suburb, city, (State)userFrame.stateComboBox.getSelectedItem(), postCode));
            System.out.println("Address after change "+User.findUserByUsername(library.getUsers(), currentLoginUser).getAddress());
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(userFrame, "Enter a number");
            }
            
        });
    }

    public void displayUserFullName() {
        staffFrame =  (StaffMenuFrame) userFrame;
        String text = "";
        class RadioButtonHandler implements ItemListener {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(staffFrame.ascendingRadioButton.isSelected()) {
                    staffFrame.studentsTextArea.setText(User.fullNamesToString(User.getAscendingListOfUserFullName(library.getUsers())));
                } else if(staffFrame.descendingRadioButton.isSelected()) {
                    staffFrame.studentsTextArea.setText(User.fullNamesToString(User.getDescendingListOfUserFullName(library.getUsers())));
                }
            }
        }
        RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
        staffFrame.ascendingRadioButton.addItemListener(radioButtonHandler);
        staffFrame.descendingRadioButton.addItemListener(radioButtonHandler);
    }

    public void editBookInfo() {
        
        libFrame.searchBook1Button.addActionListener((ActionEvent e) ->{
            Status[] bookStatus = {Status.Available, Status.Reserved, Status.Borrowed, Status.Damaged, Status.Deleted};
            String isbn = libFrame.isbnSearchField1.getText();
            //display books
            Book book = null;
            try {
                book = library.findBooks((Book b) -> b.getISBNNumber().equals(isbn)).stream().findFirst().get();
                libFrame.isbnEditField1.setText(book.getISBNNumber());
                libFrame.titleField1.setText(book.getTitle());
                libFrame.author1Field1.setText(book.getAuthorName().get(0));
                libFrame.author2Field1.setText(book.getAuthorName().get(1));
                libFrame.bookTypeField1.setText(book.getBookType());
                libFrame.locationField1.setText(book.getLocation());
                libFrame.bookStatusComboBox.setSelectedItem(book.getBookStatus());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(userFrame, "Muahaha: you can't find any books");
            }
        });
        libFrame.editBook1Button.addActionListener((ActionEvent e)-> {
            String isbn = libFrame.isbnSearchField1.getText();
            String editedISBN = libFrame.isbnEditField1.getText();
            String title = libFrame.titleField1.getText();
            String location = libFrame.locationField1.getText();
            String bookType = libFrame.bookTypeField1.getText();
            ArrayList<String> authors = new ArrayList<>();
            String author1 = libFrame.author1Field1.getText();
            String author2 = libFrame.author2Field1.getText();          
            
            for(Book book: library.findBookByISBN(isbn)) {
                book.editBook(title, author1, author2, location, bookType, currentLoginUser);
            }
        });
    }
    
    public void reserveBook() {
        ArrayList<Book> reserveableBooks = new ArrayList<>();    
        String[] bookToDisplay = new String[100] ;
        for(int i = 0; i < Book.findBookByStatus(library.getBooks(), Status.Borrowed).size(); i++) {
            reserveableBooks.add(Book.findBookByStatus(library.getBooks(), Status.Borrowed).get(i));
            bookToDisplay[i] = reserveableBooks.get(i).toString();
        }
        userFrame.reserveableBooks.setListData(bookToDisplay);
        userFrame.reserveButon.addActionListener((ActionEvent e) -> {
            Book newReservedBook = reserveableBooks.get(userFrame.reserveableBooks.getSelectedIndex());
            newReservedBook.setBookStatus(Status.Reserved);
            library.addReservedBookRecord(currentLoginUser, newReservedBook.getISBNNumber(), newReservedBook.getBookCopyNumber());
        });
    }


    
    public static void openOutFile() {
        try {
            studentOutput = new java.util.Formatter("Student.txt");
            staffOutput = new Formatter("Staff.txt");
            bookOutput = new java.util.Formatter("Book.txt");
        } catch (FileNotFoundException ex) {
            System.err.println("Filenotfound");
            System.exit(1);
        } catch(SecurityException se) {
            System.err.println("secur");
            System.exit(1);
        }
    }
    public  void addRecords() {
        for(User std : library.getUsers()) {
            if(std.getClass().getName().equals("assignment3.Student")) {
                Student student = (Student) std;
                studentOutput.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", student.getId(), student.getFirstName(), student.getLastName(), student.getUsername(), student.getPassword(), student.getUserType().name(), student.getPermissionType().name(), student.getCourse(), student.getDegree());
                if (student.hasNoAddress()) {
                    studentOutput.format("\n");
                } else if (!student.hasNoAddress()){
                    studentOutput.format(",%d,%d,%s,%s,%s,%s,%d\n",student.getAddress().getUnit(), student.getAddress().getStreetNumber(), student.getAddress().getStreet(), student.getAddress().getSuburb(),student.getAddress().getCity(), student.getAddress().state.name(), student.getAddress().getPostCode());
                }
            }
        }
        for(Book book: library.getBooks()) {
            bookOutput.format("%s,%s,%s;%s,%s,%s,%s,%d\n", book.getISBNNumber(), book.getTitle(), book.getAuthorName().get(0), book.getAuthorName().get(1),book.getLocation(), book.getBookType(), book.getBookStatus(), book.getBookCopyNumber());
        }
        for(User stf: library.getUsers()) {
            if(stf.getClass().getName().equals("assignment3.Staff")) {
                Staff staff = (Staff) stf;
                if (staff.hasNoAddress()) {
                    staffOutput.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%.0f\n", staff.getId(), staff.getFirstName(), staff.getLastName(), staff.getUsername(), staff.getPassword(), staff.getUserType().name(), staff.getPermissionType().name(), staff.getPosition(), staff.getDepartment().getName(), staff.getSalary());
                } else {
                    staffOutput.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%.0f,%d,%d,%s,%s,%s,%s,%d\n", staff.getId(), staff.getFirstName(), staff.getLastName(), staff.getUsername(), staff.getPassword(), staff.getUserType().name(), staff.getPermissionType().name(), staff.getPosition(), staff.getDepartment().getName(), staff.getSalary(), staff.getAddress().getUnit(),
                            staff.getAddress().getStreetNumber(), staff.getAddress().getStreet(), staff.getAddress().getSuburb(), staff.getAddress().getCity(), staff.getAddress().state.name(), staff.getAddress().getPostCode());
                }

            }
        }
        for(BorrowBookRecord borrowRecord: library.getBorrowRecords()) {
            borrowRecordOutput.format("%s,%s,%d,%s%s", borrowRecord.getBookISBN(),borrowRecord.getBookISBN(), borrowRecord.getBookCopyNumber(),"null",borrowRecord.getLibrarianUsernameBorrow());
        }
    }
    public static void closeFile() {
        studentOutput.close();
        bookOutput.close();
        staffOutput.close();
    }
}
