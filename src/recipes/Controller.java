package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class Controller {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        if (!recipeService.exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipeService.get(id);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> addRecipe(@RequestBody @Valid Recipe recipe) {
        int index = recipeService.create(recipe);
        return ResponseEntity.ok(Collections.singletonMap("id",index));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        boolean deleted = recipeService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
