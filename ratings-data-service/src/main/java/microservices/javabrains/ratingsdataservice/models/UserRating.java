package microservices.javabrains.ratingsdataservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRating {
    private String userId;
    private List<Rating> ratings;
}
