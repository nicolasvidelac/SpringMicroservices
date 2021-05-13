package microservices.javabrains.moviacatalogservice.resources;

import lombok.AllArgsConstructor;
import microservices.javabrains.moviacatalogservice.models.CatalogItem;
import microservices.javabrains.moviacatalogservice.models.UserRating;
import microservices.javabrains.moviacatalogservice.services.MovieInfo;
import microservices.javabrains.moviacatalogservice.services.UserRatingInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class MovieCatalogResource {

    private final MovieInfo movieInfo;
    private final UserRatingInfo userRatingInfo;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        //get all movies IDs
        UserRating userRating = userRatingInfo.getUserRating(userId);

        //for each movie ID, call info service and get details
        return userRating.getRatings().stream().map(movieInfo::getCatalogItem)
                //put them all together
                .collect(Collectors.toList());
    }


}
