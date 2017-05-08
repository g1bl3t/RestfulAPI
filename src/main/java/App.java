import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;

/**
 * Created by Tim Horner on 3/10/17.
 * "App" class: contains methods for Rest API interface
 * @author Tim Horner
 * @version 1.0
 */
public class App {

    private static Gson GSON = new GsonBuilder().create();

    public static void main(String[] args){

        //port(Integer.valueOf(System.getenv("PORT")));

        //HTTP Get method for testing Rest Service
        Spark.get("/hello", (request, response) -> "Hello World");


        /**
         * HTTP Get method for retrieving a User
         * @param id The ID number of the user you wish to retrieve
         * @return User object in JSON format
         */
        Spark.get("users/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            return GSON.toJson(User.get(id));
        });


        /**
         * HTTP Post method for storing a User
         * Tries to store User, checks to make sure User doesn't
         * already have ID and for valid JSON
         * @return the created user in JSON format
         */
        Spark.post("/users", (request, response) -> {
            User toStore = null;
            try{
                toStore = GSON.fromJson(request.body(), User.class);
            }
            catch(JsonSyntaxException e){
                response.status(400);
                return "INVALID JSON";
            }

            if(toStore.getId() != null){
                response.status(400);
                return "ID PROVIDED DURING CREATE";
            }
            else{
                User.store(toStore);
                return GSON.toJson(toStore);
            }
        });


        /**
         * HTTP Put method for updating a User
         * @param id The ID number of the User to edit
         * @return the edited user in JSON format
         */
        Spark.put("/users/:id", (request, response) -> {
            if(User.get(Integer.parseInt(request.params("id"))) == null){
                response.status(404);
                return "NOT_FOUND";
            }
            else{
                User toStore = null;
                try{
                    toStore = GSON.fromJson(request.body(), User.class);
                } catch(JsonSyntaxException e){
                    response.status(400);
                    return "INVALID JSON";
                }
                User.store(toStore);
                return GSON.toJson(toStore);
            }
        });

        /**
         * HTTP Delete method for removing a User
         * @param id The ID of the User to be deleted
         * @return a String indicating success or failure
         */

        Spark.delete("/user/:id", (request, response) -> {
            User user = User.get(Integer.parseInt(request.params("id"))) ;
            if(user == null){
                response.status(404);
                return "NOT_FOUND";
            } else{
                User.delete(user);
                return "USER DELETED";
            }
        });

        Spark.options("/*", (request, response) ->{

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if(accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if(accessControlRequestMethod != null){
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

    }
    
}
