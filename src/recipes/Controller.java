package recipes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {
    private Recipe storage;

    @GetMapping("/recipe")
    public Recipe getRecipe() {
        return storage;
    }

    @PostMapping("/recipe")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipeRequest) {
        storage = recipeRequest;
        return ResponseEntity.ok(storage);
    }
}
