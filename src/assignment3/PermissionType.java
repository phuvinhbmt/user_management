package assignment3;
public enum PermissionType {
    BorrowAndEdit("Allow to borrow or edit", 1),
    Borrow("Only allow to borrow", 2),
    Reserve("Only allow to reserve", 3),
    Edit("Allow to edit only", 4),
    None("Don't have any permission", 5),
    All("Have all permissions", 6);
    private final String name;
    private final int id;
    PermissionType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return name;
    }
}
