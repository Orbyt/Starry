package orbyt.starry.api;

import java.util.List;

import orbyt.starry.models.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by calebchiesa on 3/24/17.
 */

public interface ApiService {

    /**
     * Gets repositories for user.
     */
    @GET("/users/{user}/repos")
    Observable<List<Response>> getRepositories(@Path("user") String user);



}
