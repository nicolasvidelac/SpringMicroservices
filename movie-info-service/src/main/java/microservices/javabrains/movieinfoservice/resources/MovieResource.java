package microservices.javabrains.movieinfoservice.resources;

import microservices.javabrains.movieinfoservice.models.Movie;
import microservices.javabrains.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    private final RestTemplate restTemplate;
    @Value("${api.key}")
    private String apiKey;
    private String movieDBurl = "https://api.themoviedb.org/3/movie/";

    public MovieResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject(movieDBurl + movieId + "?api_key=" + apiKey,
                MovieSummary.class);

        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
