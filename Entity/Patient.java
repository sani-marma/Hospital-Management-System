// Entity/Patient.java
package Entity;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String bloodGroup;
    private String disease;
    private String doctorName;

    // Constructor
    public Patient(int id, String name, int age, String gender, String phone, String bloodGroup, String disease, String doctorName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.disease = disease;
        this.doctorName = doctorName;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getBloodGroup() { return bloodGroup; }
    public String getDisease() { return disease; }
    public String getDoctorName() { return doctorName; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    @Override
    public String toString() {
        return name + " (ID: " + id + ") - " + bloodGroup;
    }
}