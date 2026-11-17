import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * The SocialNetworkGraph class represents a social network where people can be added,
 * friendships can be formed, and various operations can be performed such as finding the shortest path,
 * suggesting friends, and counting clusters.
 */
public class SocialNetworkGraph {
    Map<String, Person> people = new HashMap<>();
    Map<Person, List<Person>> friendships = new HashMap<>();
    
    /**
     * Converts a string to a LocalDateTime object.
     *
     * @param timestampStr the timestamp string in the format "yyyy-MM-dd HH:mm:ss"
     * @return the LocalDateTime object or null if parsing fails
     */
    public LocalDateTime stringToTimestamp(String timestampStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(timestampStr, formatter);
            return localDateTime;
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
public List<Person> getFriendships(String personName) {
    Person person = people.get(personName);
    if (person != null) {
        return friendships.getOrDefault(person, new ArrayList<>());
    } else {
        System.out.println("Person not found: " + personName);
        return Collections.emptyList();
    }
}
    /**
     * Adds a new person to the social network.
     *
     * @param name the name of the person
     * @param age the age of the person
     * @param hobbies the list of hobbies of the person
     */
    public void addPerson(String name, int age, List<String> hobbies) {
        Person person = new Person(name, age, hobbies);
        people.put(name, person);
        friendships.put(person, new ArrayList<>());
        System.out.println("Person added: " + name +  " (Timestamp: " + person.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")");
    }
    /**
     * Removes a person from the social network if the given timestamp matches.
     *
     * @param name the name of the person to remove
     * @param timestamp the timestamp of the person
     */
    public void removePerson(String name, String timestamp){
        Person personToRemove = people.get(name);

        if (personToRemove != null) {
            LocalDateTime ts = stringToTimestamp(timestamp);
            if(!people.get(name).getTimestamp().equals(ts)){
                System.out.println("Given timestamp is wrong. Please enter the correct timestamp for the given person and use the format yyyy-MM-dd HH:mm:ss.\n");
            }
            else{
            for (Person person : friendships.keySet()) {
                friendships.get(person).remove(personToRemove);
            }
            friendships.remove(personToRemove);
            people.remove(name);
            System.out.println("Person removed: " + personToRemove);
            }
        } else {
            System.out.println("Person not found: " + name);
        }
    }
    /**
     * Adds a friendship between two persons if the given timestamps match.
     *
     * @param name1 the name of the first person
     * @param timestamp1 the timestamp of the first person
     * @param name2 the name of the second person
     * @param timestamp2 the timestamp of the second person
     */
    public void addFriendship(String name1, String timestamp1, String name2, String timestamp2) {
        Person person1 = people.get(name1);
        Person person2 = people.get(name2);
        LocalDateTime ts1 = stringToTimestamp(timestamp1);
        LocalDateTime ts2 = stringToTimestamp(timestamp2);
        if (person1 != null && person2 != null) {
            if(!people.get(name1).getTimestamp().equals(ts1) || !people.get(name2).getTimestamp().equals(ts2)){
                System.out.println("One of the given timestamps is wrong. Please enter the correct timestamp for the given people and use the format yyyy-MM-dd HH:mm:ss.\n");
            }
            else {
                friendships.get(person1).add(person2);
                friendships.get(person2).add(person1);
                System.out.println("Friendship added between " + person1.name + " and " + person2.name + ".");
            }
        } else {
            System.out.println("One or both persons not found in the network.");
        }
    }
    /**
     * Removes a friendship between two persons if the given timestamps match.
     *
     * @param name1 the name of the first person
     * @param timestamp1 the timestamp of the first person
     * @param name2 the name of the second person
     * @param timestamp2 the timestamp of the second person
     */
    public void removeFriendship(String name1, String timestamp1, String name2, String timestamp2){
        Person person1 = people.get(name1);
        Person person2 = people.get(name2);
        LocalDateTime ts1 = stringToTimestamp(timestamp1);
        LocalDateTime ts2 = stringToTimestamp(timestamp2);

        if (person1 != null && person2 != null) {
            if (!people.get(name1).getTimestamp().equals(ts1) || !people.get(name2).getTimestamp().equals(ts2)) {
                System.out.println("One of the given timestamps is wrong. Please enter the correct timestamp for the given people and use the format yyyy-MM-dd HH:mm:ss.\n");
            } else {
                if (friendships.containsKey(person1) && friendships.get(person1).contains(person2)
                        && friendships.containsKey(person2) && friendships.get(person2).contains(person1)) {
                    friendships.get(person1).remove(person2);
                    friendships.get(person2).remove(person1);
                    System.out.println("Friendship removed between " + person1.name + " and " + person2.name);
                } else {
                    System.out.println("There is no friendship between " + person1.name + " and " + person2.name);
                }
            }
        } else {
            System.out.println("One or both persons not found in the network.");
        }
    }
    /**
     * Finds the shortest path between two persons in the social network if the given timestamps match.
     *
     * @param startName the name of the start person
     * @param timestamp1 the timestamp of the start person
     * @param endName the name of the end person
     * @param timestamp2 the timestamp of the end person
     */

public void findShortestPath(String startName, String timestamp1, String endName, String timestamp2) {
    // Retrieve the start and end persons
    Person startPerson = people.get(startName);
    Person endPerson = people.get(endName);
    if (startPerson == null || endPerson == null) {
        System.out.println("Start or end person not found in the network.");
        return;
    }

    LocalDateTime ts1 = stringToTimestamp(timestamp1);
    LocalDateTime ts2 = stringToTimestamp(timestamp2);
    if (!startPerson.getTimestamp().equals(ts1) || !endPerson.getTimestamp().equals(ts2)) {
        System.out.println("One of the given timestamps is wrong. Please enter the correct timestamp for the given people and use the format yyyy-MM-dd HH:mm:ss.\n");
        return;
    }

    // BFS to find the shortest path
    Queue<Person> queue = new LinkedList<>();
    Map<Person, Person> prev = new HashMap<>();
    Set<Person> visited = new HashSet<>();

    queue.add(startPerson);
    visited.add(startPerson);
    prev.put(startPerson, null);

    while (!queue.isEmpty()) {
        Person current = queue.poll();
        if (current.equals(endPerson)) {
            break;
        }

        List<Person> neighbors = getFriendships(current.getName()); // Using the getFriendships method to get a person's friends
        for (Person neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                queue.add(neighbor);
                visited.add(neighbor);
                prev.put(neighbor, current);
            }
        }
    }

    if (!prev.containsKey(endPerson)) {
        System.out.println("No path found between " + startName + " and " + endName);
        return;
    }

    printPath(startPerson, endPerson, prev);
}


    /**
     * Prints the shortest path between two persons.
     *
     * @param start the start person
     * @param end the end person
     * @param prev the map containing the previous person in the path for each person
     */
    private void printPath(Person start, Person end, Map<Person, Person> prev) {
        List<Person> path = new ArrayList<>();
        for (Person at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        StringBuilder shortestPath = new StringBuilder("Shortest path: ");
        for (int i = 0; i < path.size(); i++) {
            shortestPath.append(path.get(i).name);
            if (i < path.size() - 1) {
                shortestPath.append(" -> ");
            }
        }
        System.out.println(shortestPath.toString());
    }
    /**
     * Suggests potential friends for a person based on mutual friends and common hobbies.
     *
     * @param name the name of the person
     * @param timestamp the timestamp of the person
     * @param n the number of suggested friends to display
     */
    public void suggestFriends(String name, String timestamp, int n){
        Person person = people.get(name);

        if (person == null) {
            System.out.println("Person not found: " + name);
            return;
        }
        LocalDateTime ts = stringToTimestamp(timestamp);
        if (!person.getTimestamp().equals(ts)) {
            System.out.println("Timestamp does not match the person.");
            return;
        }

        Map<Person, Double> scores = new HashMap<>();
        Set<Person> currentFriends = new HashSet<>(friendships.get(person));
        for (Person candidate : people.values()) {
            if (!candidate.equals(person) && !currentFriends.contains(candidate)) {
                List<Person> mutualFriends = new ArrayList<>(friendships.get(person));
                mutualFriends.retainAll(friendships.get(candidate));
                List<String> commonHobbies = new ArrayList<>(person.hobbies);
                commonHobbies.retainAll(candidate.hobbies);
                double score = mutualFriends.size() + commonHobbies.size() * 0.5;
                scores.put(candidate, score);
            }
        }

        List<Map.Entry<Person, Double>> sortedEntries = new ArrayList<>(scores.entrySet());
        sortedEntries.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        System.out.println("Suggested friends for " + name + ":");
        for (int i = 0; i < Math.min(n, sortedEntries.size()); i++) {
            Map.Entry<Person, Double> entry = sortedEntries.get(i);
            Person candidate = entry.getKey();
            double score = entry.getValue();
            List<Person> mutualFriends = new ArrayList<>(friendships.get(person));
            mutualFriends.retainAll(friendships.get(candidate));
            List<String> commonHobbies = new ArrayList<>(person.hobbies);
            commonHobbies.retainAll(candidate.hobbies);
            System.out.println(candidate.name + " (Score: " + score + ", " + mutualFriends.size() + " mutual friends, " + commonHobbies.size() + " common hobbies)");
        }

    }
    /**
     * Counts the number of clusters (connected components) in the social network.
     */
    public void countClusters() {
        Set<Person> visited = new HashSet<>();
        int clusterCount = 0;

        System.out.println("Counting clusters in the social network...");

        for (Person person : people.values()) {
            if (!visited.contains(person)) {
                List<Person> cluster = new ArrayList<>();
                bfs(person, visited, cluster);
                clusterCount++;

                System.out.println("Cluster " + clusterCount + ":");
                for (Person p : cluster) {
                    System.out.println(p.name);
                }
            }
        }

        System.out.println("Number of clusters found: " + clusterCount);
    }
    /**
     * Performs a breadth-first search (BFS) from the start person to explore their connected component.
     *
     * @param start the start person
     * @param visited the set of visited persons
     * @param cluster the list to store the persons in the current cluster
     */
    private void bfs(Person start, Set<Person> visited, List<Person> cluster) {
        Queue<Person> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Person current = queue.poll();
            cluster.add(current);

            for (Person neighbor : friendships.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }
}
