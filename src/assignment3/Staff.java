package assignment3;
public class Staff extends User{
    private String position;
    private double salary;
    private Department department;

    public Staff(String id, String firstName, String lastName, String username, String password, UserType userType, PermissionType permissionType, String position, double salary, Department department) {
        super(id, firstName, lastName, username, password, userType, permissionType, true);
        if (userType == UserType.Librarian) {
            try {
//                this.setIdLibrarian(id);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            this.setPermissiontype(PermissionType.Reserve);
            if ((Integer.parseInt(id) < 3000000 || Integer.parseInt(id) > 3999999)) {
                try {
//                    this.setIdLibrarian(id);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        this.position = position;
        this.salary = salary;
        this.department = department;
    }

    protected void setIdLibrarian(String id) {
        if ((Integer.parseInt(id) < 4000000 || Integer.parseInt(id) > 4999999))
            throw new IllegalArgumentException("Libraian id ranges 4000000 - 4999999");
        else 
            super.setId(id);
    }
    
    protected void setIdStaff(String id) {
        if ((Integer.parseInt(id) < 3000000 || Integer.parseInt(id) > 3999999))
            throw new IllegalArgumentException("Libraian id ranges 4000000 - 4999999");
        else 
            super.setId(id);
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if(position.equals("Librarian"))
            throw new IllegalArgumentException("Can not set position to librarian");
        else 
            this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if(salary <= 0) 
            throw new IllegalArgumentException("Salary must not be less than 0");
        else if(salary > 0) 
            this.salary = salary;        
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return super.toString() + "  "+position + "  "+salary + "  "+ department;
    }
}

