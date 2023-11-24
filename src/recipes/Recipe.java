package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    String name;
    @NotBlank
    String description;
    @NotNull
    @Size(min = 1)
    @ElementCollection
    @OrderColumn(name = "id")
    String[] ingredients;
    @NotNull
    @Size(min = 1)
    @ElementCollection
    @OrderColumn(name = "id")
    String[] directions;
}
