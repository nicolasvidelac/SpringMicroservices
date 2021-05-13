package microservices.javabrains.ratingsdataservice.resources;

import microservices.javabrains.ratingsdataservice.models.Rating;
import microservices.javabrains.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 2);
    }

    @GetMapping("/user/{userId}")
    public UserRating getUserMovies(@PathVariable String userId) throws InterruptedException {
        List<Rating> userRatings = Arrays.asList(
                new Rating("100", 3),
                new Rating("200", 4)
        );

        UserRating userRating = new UserRating();
        userRating.setRatings(userRatings);
        return userRating;
    }


}
