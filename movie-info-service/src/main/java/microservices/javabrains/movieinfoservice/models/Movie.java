package microservices.javabrains.movieinfoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    private String movieId;
    private String name;
    private String desc;
}
