
public abstract class Person {

    private String personNumber;
    private String name;
    private int age;

    public Person(String personNumber, String name, int age) {
        this.personNumber = personNumber;
        this.name = name;
        this.age = age;
    }

    public Person() {
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

//    public int getPersonID() {
//        return personID;
//    }

//    public void setPersonID(int personID) {
//        this.personID = personID;
//    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
