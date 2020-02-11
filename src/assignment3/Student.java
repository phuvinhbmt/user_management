/*
*NEED: validate in set degree, course, fname
*throw Exception: Student, setPermissionType
*/
package assignment3;

public class Student extends User{
    private String course, degree;

    public Student(String id, String firstName, String lastName, String username, String password, UserType userType, PermissionType permissionType,String course, String degree) {        
        super(id, firstName, lastName, username, password, userType, PermissionType.Reserve, true);
        try {
            this.setId(id);
        } catch(IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        this.course = course;
        this.degree = degree;
    }
    
    @Override
    protected void setId(String id) {
        if (Integer.parseInt(id) < 3000000 || Integer.parseInt(id) > 3999999) 
            throw new IllegalArgumentException("Student1 id ranges 3000000 - 3999999");
        else 
            super.setId(id);
    }
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    @Override
    public void setPermissiontype(PermissionType permissiontype) {
        System.out.println("Student"+permissiontype.name());
        if(permissiontype!=PermissionType.Reserve && permissiontype!=PermissionType.None)
            throw new IllegalArgumentException("Student permission type can only be reserve or none");
        super.setPermissiontype(permissiontype);
    }
    @Override
    public String toString() {
        return super.toString() + course + "  " + degree;
    }
}
