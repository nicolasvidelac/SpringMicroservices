package microservices.javabrains.moviecatalogservice.resources;

import microservices.javabrains.moviecatalogservice.models.CatalogItem;
import microservices.javabrains.moviecatalogservice.models.UserRating;
import microservices.javabrains.moviecatalogservice.services.MovieInfoService;
import microservices.javabrains.moviecatalogservice.services.UserRatingInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/catalog")
@RestController
@RefreshScope
public class MovieCatalogResource {

    private final MovieInfoService movieInfoService;
    private final UserRatingInfoService userRatingInfoService;
    private final Environment env;

    @Value("${gordita.bonita}")
    private String texto;

    public MovieCatalogResource(MovieInfoService movieInfoService, UserRatingInfoService userRatingInfoService, Environment env) {
        this.movieInfoService = movieInfoService;
        this.userRatingInfoService = userRatingInfoService;
        this.env = env;
    }

    @GetMapping
    public String getProperties() {
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            return env.getProperty("configuracion.autor.nombre") + "\n" + env.getProperty("gordita.bonita");
        }
        return texto;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        //get all movies IDs
        UserRating userRating = userRatingInfoService.getUserRating(userId);

        //for each movie ID, call info service and get details
        List<CatalogItem> result = userRating.getRatings().stream().map(movieInfoService::getCatalogItem)

                //put them all together
                .collect(Collectors.toList());


        return result;
    }

}
