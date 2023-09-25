package domain;

public class User {
    private int Id;
    private String Name;
    private String Password;
    private String Gender;
    private int Age;
    private String Phone;
    private String Email;

    public User() {

    }

    public User(String name, String password, String gender, int age, String phone, String email) {
        Name = name;
        Password = password;
        Gender = gender;
        Age = age;
        Phone = phone;
        Email = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + Id +
                ", name='" + Name + '\'' +
                ", gender='" + Gender + '\'' +
                ", age=" + Age +
                ", phone='" + Phone + '\'' +
                ", email='" + Email + '\'' +
                '}';
    }
}
