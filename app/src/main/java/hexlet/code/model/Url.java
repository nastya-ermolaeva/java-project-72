package hexlet.code.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
