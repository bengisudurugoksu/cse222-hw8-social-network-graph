import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * The Person class represents a person with a name, age, hobbies, and a timestamp.
 * The timestamp is generated at the moment of object creation and formatted to exclude nanoseconds.
 */
public class Person {
    String name;
    int age;
    List<String> hobbies;
    LocalDateTime timestamp;
    /**
     * Constructs a new Person with the specified name, age, and hobbies.
     * Initializes the timestamp to the current time formatted to exclude nanoseconds.
     *
     * @param name the name of the person
     * @param age the age of the person
     * @param hobbies the list of hobbies of the person
     */
    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>(hobbies);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().withNano(0);
        String formattedTimestamp = this.timestamp.format(formatter);
        this.timestamp = LocalDateTime.parse(formattedTimestamp, formatter);

    }
    /**
     * Returns the timestamp of when the person object was created.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getName(){
    	return name;
    }
    /**
     * Returns a string representation of the person, including their name, age, hobbies, and timestamp.
     *
     * @return a string representation of the person
     */
    @Override
    public String toString() {
        return name + " (Age: " + age + ", Hobbies: " + hobbies + " Timestamp: " + this.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";
    }
}
