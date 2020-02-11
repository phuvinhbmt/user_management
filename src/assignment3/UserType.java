package assignment3;
public enum UserType {
    Student("UnderGraduate Student", 1, 20),
    PostStudent("PostGraduate Student", 2, 30),
    AdminStaff("Administrative Staff", 3, 30),
    Librarian("Librarian", 4, 40),
    AcademicStaff("Academic Staff", 5, 40),
    Admin("System Administrator", 6, 30),
    Staff("Staff", 7, 23);
    private String name;
    private int id;
    private int numberOfAllowedBooksToBorrow;
    
    UserType(String name, int id, int numberOfAllowedBooksToBorrow) {
        this.name = name;
        this.id = id;
        this.numberOfAllowedBooksToBorrow = numberOfAllowedBooksToBorrow;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfAllowedBooksToBorrow() {
        return numberOfAllowedBooksToBorrow;
    }
    
    @Override
    public String toString() {
        return name;
    }
    public UserType getUserType(String userType) {
        UserType typeOfUser = null;
        for(UserType type: UserType.values()) {
            if(type == UserType.valueOf(userType)) 
                typeOfUser = type;
        }
        return typeOfUser;
    }
}

