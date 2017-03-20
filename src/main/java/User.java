
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gibl3t on 3/10/17.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private static List<User> users = new ArrayList<User>();

    static{
        users.add(new User(0, 10.3, 203.2));
        users.add(new User(1, 11.5, 132.1));
        users.add(new User(2, 45.5, 66.6));
        users.add(new User(3, 77.7, 88.8));
        users.add(new User(4, 101.1, 102.2));
    }

    private Integer id;
    private Double latitude;
    private Double longitude;

    public User(Integer id, Double latitude, Double longitude){
        super();
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User() {}

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

    public static void delete(User p){
        users.remove(p);
    }

}
