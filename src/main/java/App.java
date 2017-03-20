import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;

/**
 * Created by gibl3t on 3/10/17.
 */
public class App {

    private static Gson GSON = new GsonBuilder().create();

    public static void main(String[] args){

        Spark.get("/hello", (request, response) -> "Hello World");

        Spark.get("users/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            return GSON.toJson(User.get(id));
        });

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
