package microservices.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import microservices.javabrains.moviecatalogservice.models.Rating;
import microservices.javabrains.moviecatalogservice.models.UserRating;
import microservices.javabrains.moviecatalogservice.services.feignClients.UserRatingFeign;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserRatingInfoService {

    private final UserRatingFeign userRatingFeign;

    public UserRatingInfoService(UserRatingFeign userRatingFeign) {
        this.userRatingFeign = userRatingFeign;
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            })
    public UserRating getUserRating(String userId) {
        return userRatingFeign.getUserMovies(userId);
    }

    public UserRating getFallbackUserRating(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Collections.singletonList(new Rating("1", 1)));

        return userRating;
    }
}
