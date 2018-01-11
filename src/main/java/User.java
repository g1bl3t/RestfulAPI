
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

#test branch commit

/**
 * Created by Tim Horner on 3/10/17.
 * "User" class for storing player data
 * Each user has a unique ID and 2 floats(latitude and longitude)
 * indicating their most current location
 * @author Tim Horner
 * @version 1.0
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //ArrayList that stores all Users
    private static List<User> users = new ArrayList<User>();

    //dummy Users for test purposes
    static{
        users.add(new User(0, 10.3, 203.2));
        users.add(new User(1, 11.5, 132.1));
        users.add(new User(2, 45.5, 66.6));
        users.add(new User(3, 77.7, 88.8));
        users.add(new User(4, 10.952584, 20.165222));
    }

    private Integer id;
    private Double latitude;
    private Double longitude;

    /**
     * Constructor for class User
     * @param id Unique ID for user
     * @param latitude Double for latitude
     * @param longitude Double for longitude
     */
    public User(Integer id, Double latitude, Double longitude){
        super();
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Empty default constructor
     */
    public User() {}


    /**
     * All getters and setters for User class
     * @return
     */
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Double getLatitude(){
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public static List<User> getAll(){
        return users;
    }

    public static User get(final Integer id){
        return users.stream().filter((p)->p.getId().equals(id)).findFirst().get();    }


    /**
     * Method for storing a User in the users ArrayList
     * Assigns User the next available ID number and stores
     * the object in the ArrayList
     * If ID is already assigned it will overwrite the User and
     * act as a Put method
     * @param p the User to be stored
     * @return the stored User
     */
    public static User store(User p){
        if(p.getId() == null){
            User maxIdPerson = users.stream().max((p1,p2)->Integer.compare(p1.getId(), p2.getId())).get();
            p.setId(maxIdPerson.getId()+1);
            users.add(p);
        }
        else{
            users.set(p.getId(), p);
        }

        return p;
    }

    /**
     * Delete a User from the ArrayList
     * @param p The User to be deleted
     */
    public static void delete(User p){
        users.remove(p);
    }

}
