package assignment3;


import javax.swing.*;
public class StaffMenuFrame extends UserMenuFrame{
    JTabbedPane adminPane;
    JPanel staffMenu1;
    JTextArea studentsTextArea;
    JRadioButton ascendingRadioButton, descendingRadioButton; 
    ButtonGroup radioGroupButton;

    public StaffMenuFrame() {
        super("Staff Menu");
        adminPane = new JTabbedPane();
        staffMenu1 = new JPanel();
        studentsTextArea = new JTextArea(16, 16);
        studentsTextArea.setEditable(false);
        ascendingRadioButton = new JRadioButton("Ascending order");
        descendingRadioButton = new JRadioButton("Descending order");
        radioGroupButton = new ButtonGroup();
        
        radioGroupButton.add(ascendingRadioButton);
        radioGroupButton.add(descendingRadioButton);
        staffMenu1.add(ascendingRadioButton);
        staffMenu1.add(descendingRadioButton);
        staffMenu1.add(new JScrollPane(studentsTextArea));
        
        tabbedPane.addTab("Staff menu 1", null, staffMenu1, "List Student accounts");
        
    }
    
}
