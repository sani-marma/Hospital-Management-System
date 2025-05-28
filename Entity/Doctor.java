// Entity/Doctor.java
package Entity;

public class Doctor {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String specialization;
    private String department;

    // Constructor
    public Doctor(int id, String name, int age, String gender, String phone, String specialization, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.specialization = specialization;
        this.department = department;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getSpecialization() { return specialization; }
    public String getDepartment() { return department; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Dr. " + name + " (ID: " + id + ") - " + specialization;
    }
}