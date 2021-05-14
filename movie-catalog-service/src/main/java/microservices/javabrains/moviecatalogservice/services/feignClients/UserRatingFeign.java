package microservices.javabrains.moviecatalogservice.services.feignClients;

import microservices.javabrains.moviecatalogservice.models.Rating;
import microservices.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ratings-data-service")
public interface UserRatingFeign {

    @GetMapping("/{movieId}")
    Rating getRating(@PathVariable String movieId);

    @GetMapping("/user/{userId}")
    UserRating getUserMovies(@PathVariable String userId);
}
