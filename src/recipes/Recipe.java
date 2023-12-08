package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @UpdateTimestamp
    private LocalDateTime date;

    @NotNull
    @Size(min = 1) @ElementCollection
    @OrderColumn(name = "id")
    private String[] ingredients;

    @NotNull
    @Size(min = 1) @ElementCollection

    @OrderColumn(name = "id")
    private String[] directions;

    @JsonIgnore
    @ManyToOne
    private User author;
}
