package microservices.javabrains.movieinfoservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MovieSummary {
    private String title;
    private String overview;

}
