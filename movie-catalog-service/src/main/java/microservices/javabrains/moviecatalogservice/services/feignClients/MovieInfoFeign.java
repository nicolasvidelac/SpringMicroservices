package microservices.javabrains.moviecatalogservice.services.feignClients;

import microservices.javabrains.moviecatalogservice.models.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-info-service")
public interface MovieInfoFeign {

    @GetMapping("/movies/{movieId}")
    Movie getMovie(@PathVariable String movieId);

}
