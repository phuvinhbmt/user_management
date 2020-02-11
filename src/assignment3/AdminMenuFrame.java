package assignment3;
import java.awt.*;
import javax.swing.*;

public class AdminMenuFrame extends UserMenuFrame{
    JTabbedPane adminPane;
    JPanel adminMenu1, adminMenu2, adminMenu3;
    //Menu1
    JPanel userInfoPanel;
    JTextField firstNameField, lastNameField;
    JLabel firstNameLabel, lastNameLabel;
    JTextField idDisplayField, fnameDisplayField, lnameDisplayField, usernameDisplayField, courseDisplayField, degreeDisplayField, salaryDisplayField, userTypeDisplayField, positionDisplayField ;
    JComboBox<String> permissionTypeComboBox, departmentComboBox1;
    static String[] permissionTypeList = {"BorrowAndEdit", "Borrow", "Reserve", "Edit", "None", "All"};
    
    JButton searchButton, reportButton;
    JList<String> userList1, userList2;
    JTextArea activeUserTextArea;
    JTextField totalActiveUsers;
    //menu2
    JRadioButton staffRadioButton, studentRadioButton;
    ButtonGroup radioGroup;
    JButton createStudentButton, createStaffButton;
    JButton  activeButton, deactiveButton, updateListButton;
    JTextField totalUsersField;
    JTextField idDisplayField2, fnameDisplayField2, lnameDisplayField2, usernameDisplayField2, passwordDisplayField2, courseDisplayField2, degreeDisplayField2, salaryDisplayField2, userTypeDisplayField2, departmentField2, positionField2 ;
    JComboBox permissionTypeComboBox2, userTypeComboBox2;
    PermissionType[] pTypeList = {PermissionType.BorrowAndEdit, PermissionType.Borrow, PermissionType.Reserve, PermissionType.Edit, PermissionType.None, PermissionType.All};
    UserType[] uTypeList = {UserType.AcademicStaff, UserType.Admin, UserType.AdminStaff, UserType.Librarian, UserType.PostStudent, UserType.Staff, UserType.Student};
    Department[] departmentList = new Department[Department.values().length];
    JComboBox departmentComboBox;
    JButton editUserButon;

    public AdminMenuFrame() {
        super("Admin Menu");

        for(int i = 0; i < Department.values().length; i++) {
            departmentList[i] = Department.values()[i];
        }
        adminPane = new JTabbedPane();
        adminMenu1 = new JPanel();
        adminMenu2 = new JPanel();
        adminMenu3 = new JPanel();
        
        tabbedPane.addTab("Admin menu 1", null, adminMenu1, "Search and display user information");
        tabbedPane.addTab("Admin menu 2", null, adminMenu2, "Create or delete account");
        tabbedPane.addTab("Admin menu 3", null, adminMenu3, "List active users");
        
        adminMenu1();
        adminMenu2();
        adminMenu3();
    }
 
    public void adminMenu1() {
        firstNameField = new JTextField(11);
        lastNameField = new JTextField(11);
        
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        
        searchButton = new JButton("Search");
        editUserButon = new JButton("Edit");
        
        initUserInfoLabel();
        idDisplayField = new JTextField(18); 
        fnameDisplayField = new JTextField(18); 
        permissionTypeComboBox = new JComboBox<>(permissionTypeList);
        lnameDisplayField = new JTextField(18);
        usernameDisplayField = new JTextField(18);
        userTypeDisplayField = new JTextField(18);
        userTypeDisplayField.setEditable(false);
        courseDisplayField = new JTextField(18);
        degreeDisplayField = new JTextField(18);
        salaryDisplayField = new JTextField(18);
        positionDisplayField = new JTextField(18);
        departmentComboBox1 = new JComboBox(departmentList);
        
        adminMenu1.setLayout(new FlowLayout());
        userInfoPanel = new JPanel();
        adminMenu1.add(firstNameLabel);
        adminMenu1.add(firstNameField);
        adminMenu1.add(lastNameLabel);
        adminMenu1.add(lastNameField);
        adminMenu1.add(searchButton);
        adminMenu1.add(editUserButon);
        
        userList1 = new JList<>();
        userList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adminMenu1.add(new JScrollPane(userList1));
        
        userInfoPanel.setLayout(new GridLayout(12,2));
        userInfoPanel.add(idDisplayLabel);
        userInfoPanel.add(idDisplayField);
        userInfoPanel.add(fnameDisplayLabel);
        userInfoPanel.add(fnameDisplayField);
        userInfoPanel.add(lnameDisplayLabel);
        userInfoPanel.add(lnameDisplayField);
        userInfoPanel.add(usernameDisplayLabel);
        userInfoPanel.add(usernameDisplayField);
        userInfoPanel.add(userTypeDisplayLabel);
        userInfoPanel.add(userTypeDisplayField);
        userInfoPanel.add(permissionTypeDisplayLabel);
        userInfoPanel.add(permissionTypeComboBox);
        userInfoPanel.add(courseDisplayLabel);
        userInfoPanel.add(courseDisplayField);
        userInfoPanel.add(degreeDisplayLabel);
        userInfoPanel.add(degreeDisplayField);
        userInfoPanel.add(departmentDisplayLabel);
        userInfoPanel.add(departmentComboBox1);
        userInfoPanel.add(positionDisplayLabel);
        userInfoPanel.add(positionDisplayField);
        userInfoPanel.add(salaryDisplayLabel);
        userInfoPanel.add(salaryDisplayField);
        adminMenu1.add(userInfoPanel);
    }
    public void displayUserInfo(User user) {
        idDisplayField.setText(user.getId());
        fnameDisplayField.setText(user.getFirstName());
        lnameDisplayField.setText(user.getLastName());
        usernameDisplayField.setText(user.getUsername());
        userTypeDisplayField.setText(user.getUserType().getName());
        permissionTypeComboBox.setSelectedItem(user.getPermissionType().name());
        // need modification to work with formatter 
        if(user.getClass().getName().equals("assignment3.Staff")) {
            Staff staff = (Staff) user;
            courseDisplayField.setText("");
            degreeDisplayField.setText("");
            positionDisplayField.setText(staff.getPosition());
            departmentComboBox1.setSelectedItem(staff.getDepartment());
            salaryDisplayField.setText(""+staff.getSalary());
        } else if (user.getClass().getName().equals("assignment3.Student")) {
            Student student = (Student) user;
            positionDisplayField.setText("");
            courseDisplayField.setText(student.getCourse());
            degreeDisplayField.setText(student.getDegree());
            salaryDisplayField.setText("");
        }
    }
    public void adminMenu2() {
        updateListButton = new JButton("See updated list");
        activeButton = new JButton("Active");
        deactiveButton = new JButton("Deactive");
        totalUsersField = new JTextField(9);
        totalUsersField.setEditable(false);
        userList2 = new JList<>();
        userList2.setSize(30, 10);
        createStudentButton = new JButton("Create Student");
        createStaffButton = new JButton("Create Staff");
        staffRadioButton = new JRadioButton("Create staff");
        studentRadioButton = new JRadioButton("Create student");
        radioGroup = new ButtonGroup();
        
        
        this.initUserInfoLabel();

        idDisplayField2 = new JTextField(18); 
        fnameDisplayField2 = new JTextField(18); 
        permissionTypeComboBox2 = new JComboBox<>(pTypeList);
        userTypeComboBox2 = new JComboBox<>(uTypeList);
        userTypeComboBox2.setEditable(false);
        lnameDisplayField2 = new JTextField(18);
        usernameDisplayField2 = new JTextField(18);
        passwordDisplayField2 = new JTextField(18);
        courseDisplayField2 = new JTextField(18);
        degreeDisplayField2 = new JTextField(18);
        salaryDisplayField2 = new JTextField(18);
        positionField2 = new JTextField(18);
        
        departmentComboBox = new JComboBox(departmentList);
                
        adminMenu2.add(updateListButton);
        adminMenu2.add(activeButton);
        adminMenu2.add(deactiveButton);
        userList2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        adminMenu2.add(new JScrollPane(userList2));
        adminMenu2.add(totalUsersField);
        radioGroup.add(staffRadioButton);
        radioGroup.add(studentRadioButton);
        adminMenu2.add(staffRadioButton);
        adminMenu2.add(studentRadioButton);
    }
    public void adminMenu3() {
        reportButton = new JButton("report");
        activeUserTextArea = new JTextArea(16,16);
        activeUserTextArea.setEditable(false);
        totalActiveUsers = new JTextField(7);
        
        adminMenu3.add(reportButton);
        adminMenu3.add(new JScrollPane(activeUserTextArea));
        adminMenu3.add(totalActiveUsers);
    }
    
}
