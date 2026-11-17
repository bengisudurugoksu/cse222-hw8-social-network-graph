import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
/**
 * The Main class serves as the entry point for the Social Network application.
 * It provides a menu-driven interface to interact with the social network,
 * allowing users to add and remove persons, manage friendships, find shortest paths,
 * suggest friends, and count clusters.
 */
public class Main {
    /**
     * The main method serves as the entry point of the application.
     *
     * @param args command-line arguments (not used)
     */

    public static void main(String[] args) {
        SocialNetworkGraph network = new SocialNetworkGraph();
        Scanner scanner = new Scanner(System.in);
        int choice=0, exit=0;
        System.out.println("Welcome to the Social Network!\n");
        while(exit==0){
            System.out.print("===== Social Network Analysis Menu =====\n1. Add person\n2. Remove Person\n3. Add Friendship\n4. Remove Friendship\n5. Find Shortest Path\n6. Suggest Friends\n7. Count Clusters\n8. Exit\nChoice: ");
            choice = scanner.nextInt();
            switch(choice){
                case 1:
                    scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter hobbies (comma-separated): ");
                    String input = scanner.nextLine();
                    List<String> hobbies = new ArrayList<>(Arrays.asList(input.split(",")));
                    network.addPerson(name, age, hobbies);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name2 = scanner.nextLine();
                    System.out.print("Enter timestamp: ");
                    String timestamp = scanner.nextLine();
                    network.removePerson(name2,timestamp);
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Enter first person's name: ");
                    String firstperson = scanner.nextLine();
                    System.out.print("Enter first person's timestamp: ");
                    String firsttimestamp = scanner.nextLine();
                    System.out.print("Enter second person's name: ");
                    String secondperson = scanner.nextLine();
                    System.out.print("Enter second person's timestamp: ");
                    String secondtimestamp = scanner.nextLine();
                    network.addFriendship(firstperson,firsttimestamp,secondperson,secondtimestamp);
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Enter first person's name: ");
                    String firstperson2 = scanner.nextLine();
                    System.out.print("Enter first person's timestamp: ");
                    String firsttimestamp2 = scanner.nextLine();
                    System.out.print("Enter second person's name: ");
                    String secondperson2 = scanner.nextLine();
                    System.out.print("Enter second person's timestamp: ");
                    String secondtimestamp2 = scanner.nextLine();
                    network.removeFriendship(firstperson2,firsttimestamp2,secondperson2,secondtimestamp2);
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.print("Enter first person's name: ");
                    String firstperson3 = scanner.nextLine();
                    System.out.print("Enter first person's timestamp: ");
                    String firsttimestamp3 = scanner.nextLine();
                    System.out.print("Enter second person's name: ");
                    String secondperson3 = scanner.nextLine();
                    System.out.print("Enter second person's timestamp: ");
                    String secondtimestamp3 = scanner.nextLine();
                    network.findShortestPath(firstperson3,firsttimestamp3,secondperson3,secondtimestamp3);
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.print("Enter person's name: ");
                    String name4 = scanner.nextLine();
                    System.out.print("Enter person's timestamp: ");
                    String timestamp4 = scanner.nextLine();
                    System.out.print("Enter maximum number of friends to suggest: ");
                    int maxn = scanner.nextInt();
                    scanner.nextLine();
                    network.suggestFriends(name4, timestamp4, maxn);
                    break;
                case 7:
                    network.countClusters();
                    break;
                case 8:
                    System.out.println("Leaving the Social Network. Goodbye!");
                    exit = 1;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose from the menu choices.\n");
                    break;
            }
        }
        // Adding some people for demonstration
        network.addPerson("John Doe", 25, Arrays.asList("reading", "hiking", "cooking"));
        network.addPerson("Jane Smith", 22, Arrays.asList("swimming", "cooking"));
        network.addPerson("Alice Johnson", 27, Arrays.asList("hiking", "painting"));
        network.addPerson("Bob Brown", 30, Arrays.asList("reading", "swimming"));
        network.addPerson("Emily Davis", 28, Arrays.asList("running", "swimming"));
        network.addPerson("Frank Wilson", 26, Arrays.asList("reading", "hiking"));
        network.addPerson("mehmet", 26, Arrays.asList("reading", "hiking"));
        LocalDateTime tsjd = network.people.get("John Doe").getTimestamp();
        LocalDateTime tsjs = network.people.get("Jane Smith").getTimestamp();
        LocalDateTime tsaj = network.people.get("Alice Johnson").getTimestamp();
        LocalDateTime tsbb = network.people.get("Bob Brown").getTimestamp();
        LocalDateTime tsed = network.people.get("Emily Davis").getTimestamp();
        LocalDateTime tsfw = network.people.get("Frank Wilson").getTimestamp();
        LocalDateTime m = network.people.get("mehmet").getTimestamp();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String jdstring = tsjd.format(formatter);
        String jsstring = tsjs.format(formatter);
        String ajstring = tsaj.format(formatter);
        String bbstring = tsbb.format(formatter);
        String edstring = tsed.format(formatter);
        String fwstring = tsfw.format(formatter);
        String mst = m.format(formatter);
        // Adding friendships for demonstration
        network.addFriendship("John Doe",jdstring,  "Jane Smith", jsstring);
        network.addFriendship("John Doe", jdstring,"Alice Johnson", ajstring);
        network.addFriendship("Jane Smith", jsstring, "Bob Brown", bbstring);
        network.addFriendship("Emily Davis", edstring, "Frank Wilson", fwstring);
   
    

        // Finding shortest path for demonstration
        network.findShortestPath("John Doe", jdstring, "Bob Brown", bbstring);

        // Counting clusters for demonstration
        network.countClusters();


        scanner.close();
    }
}
