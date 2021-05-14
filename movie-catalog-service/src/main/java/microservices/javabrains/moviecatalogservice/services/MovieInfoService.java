package microservices.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import microservices.javabrains.moviecatalogservice.models.CatalogItem;
import microservices.javabrains.moviecatalogservice.models.Movie;
import microservices.javabrains.moviecatalogservice.models.Rating;
import microservices.javabrains.moviecatalogservice.services.feignClients.MovieInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieInfoService {

    private final MovieInfoFeign movieInfoFeign;

    @Autowired
    public MovieInfoService(MovieInfoFeign movieInfoFeign) {
        this.movieInfoFeign = movieInfoFeign;
    }

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            })
    public CatalogItem getCatalogItem(Rating rating) {

        Movie movie = movieInfoFeign.getMovie(rating.getMovieId());

        assert movie != null;
        return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }

}
