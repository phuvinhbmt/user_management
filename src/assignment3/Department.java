package assignment3;
public enum Department {
    IT("Information Technology"),
    MNG("Management"),
    CS("Computer Science"),
    ACC("Accounting"),
    ENG("Engineering"),
    MKT("Marketing"),
    HR("Human Resources"),
    LAW("Law"),
    RAD("Research and Development"),
    TRN("Training"),
    BUS("Business"),
    BND("Business Development"),
    LIB("Library"),
    SER("Services"),
    LEG("Legal");
    
    private final String name;
    Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static Department initDepartment(String name) {
        
        Department matchedDepartment = null;
        Department[] departmentList = {Department.ACC, Department.BND, Department.BUS, Department.CS, Department.ENG, Department.HR, Department.IT, Department.LAW, Department.LEG, Department.LIB, Department.MKT, Department.MNG, Department.RAD, Department.SER, Department.TRN};
        String[] nameList = {"Accounting","Business Development","Business","Computer Science","Engineering","Human Resources","Information Technology","Law","Legal","Library","Marketing","Management","Research and Development","Services","Training"};
        for(int i = 0; i < nameList.length; i++) {
            if (name.equals(nameList[i])) {
                matchedDepartment = departmentList[i];
                break;
            }
        }
        return matchedDepartment;
    }
    @Override
    public String toString() {
        return name;
    }
}
