package assignment3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class User implements BorrowInfo {
    private String id, firstName,lastName, username, password ;
    private UserType userType;
    PermissionType permissionType;
    private boolean status;
    private Address address;
    ArrayList<String> passwordList = new ArrayList<>();
    public User(String id, String firstName, String lastName, String username, String password, UserType userType, PermissionType permissionType, boolean status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        passwordList.add(password);
        this.userType = userType;
        this.permissionType = permissionType;
        this.status = status;
        address = null;
    }
    
    private boolean checkPattern(String pattern, String input) {
        return Pattern.compile(pattern).matcher(input).matches();
    }
    
    protected void setId(String id) {
        if (id.length() == 7)
            this.id = id;
        else 
            throw new IllegalArgumentException("The size of id is 7 characters");
    }

    public void setFirstName(String firstName) throws IllegalArgumentException{
        if(this.checkPattern(".*\\d.*", firstName))
            throw new IllegalArgumentException("contain some number in the first name");
        else
            this.firstName = firstName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    

    public void setLastName(String lastName) {
        if (this.checkPattern(".*\\d.*", lastName)) {
            throw new IllegalArgumentException("contain some number in the first name");
        } else {
            this.lastName = lastName;
        }
    }

    public void setPassword(String newPassword) throws ClassNotFoundException  {
        if ((password.length() < 8)) {
            throw new IllegalArgumentException("Password must contain more than 7 characters");
        } else if(passwordList.contains(newPassword)) {
            throw new ClassNotFoundException("Can not use old password");
        } else {
            this.password = newPassword;
            passwordList.add(newPassword);
        }
        
    }

    public void setUsername(String username) {
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username is need to be more than 3 characters");
        } else {
            this.username = username;
        }
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setPermissiontype(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getFullname() {
        return this.firstName + " " + this.lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public boolean getStatus() {
        return status;
    }

    public boolean verifyUsernameAndPassword(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password)) ;
    }
    
    public boolean verifyUsername(String username) {
        return (this.username.equals(username));
    }

    public boolean verifyUserType(UserType userType) {
        return (this.userType.equals(userType));
    }
    
    public boolean verifyPermission(PermissionType permissionType) {        
        return (this.permissionType.equals(permissionType) ) ;
    }
    @Override
    public String toString() {        
        return String.format("%s %s %s %s %s %s %s", id, firstName, lastName, username, userType, permissionType, status);
//        return id + firstName + lastName + username + userType + permissionType + status;
    }
    @Override
    public int getMaxNumberOfAllowedBook() {
        return this.getUserType().getNumberOfAllowedBooksToBorrow();
    }
    @Override
    public User clone() throws CloneNotSupportedException {
        return  (User) super.clone();
    }
    @Override
    public int compareTo(User other) {
        return this.lastName.compareTo(other.lastName);
    }
    public static User findUserByUsername(ArrayList<User> userList, String username) {
        User matchUser = null;
        for(User user: userList) {
            if (user.getUsername().equals(username)) {
                matchUser = user;
                break;
            }
        }
        
        userList.stream()
                .map(user -> user.getUsername().equals(username));
//        if(matchUser == null) 
//            throw new NullPointerException();
        return matchUser;
    }
    
    public static boolean verifyLoginByUsernameAndPassword(ArrayList<User> userList, String username, String password) {
        boolean result = false;
        for (User user : userList) {
            if (user.verifyUsernameAndPassword(username, password)) {
                result = true;
                break;
            }
        }
        return result;
    }
    public static ArrayList<String> getAscendingListOfUserFullName( ArrayList<User> users) { // not sort
        ArrayList<String> userFullNames = new ArrayList<>();
//        for(User user: users) 
//            userFullNames.add(user.getFullname());
//        Collections.sort(userFullNames);
        
        users.stream().forEach((user) -> {
            userFullNames.add(user.getFullname());
            Collections.sort(userFullNames);
        });
        return userFullNames;
    }
    
    public static ArrayList<String> getDescendingListOfUserFullName( ArrayList<User> users) { // not sort
        ArrayList<String> userFullNames = new ArrayList<>();
        for(User user: users) 
            userFullNames.add(user.getFullname());
        Collections.sort(userFullNames, Collections.reverseOrder());
        return userFullNames;
    }
    
    public static String fullNamesToString(ArrayList<String> fullNames) {
        String output = "";
        output = fullNames.stream().map((fullName) -> String.format("%s\n", fullName)).reduce(output, String::concat);
        return output;
    }
    public boolean verifyString(String needCheck) {
        boolean result = true;
        String[] specialCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "("};
        for(int i = 0; i < specialCharacters.length; i++) {
            if (needCheck.contains(specialCharacters[i])) {
                result = false; 
                break;
            }                
        }            
        return true;
    }
    public boolean hasNoAddress() {
        return this.address == null;
    }
}

