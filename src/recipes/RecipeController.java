package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        if (!recipeService.exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipeService.get(id);
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam(required = false) String category
            ,@RequestParam(required = false) String name) {
        if (null != category) {
            return recipeService.search("category", category);
        } else if (null != name) {
            return recipeService.search("name", name);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> addRecipe(@RequestBody @Valid Recipe recipe, Authentication auth) {
        recipe.setAuthor(userService.find(auth.getName()));
        int index = recipeService.create(recipe);
        return ResponseEntity.ok(Collections.singletonMap("id",index));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe, Authentication auth) {
        if (!recipeService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!auth.getName().equals(recipeService.get(id).getAuthor().getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        recipe.setAuthor(recipeService.get(id).getAuthor());
        recipeService.updateById(id, recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id, Authentication auth) {
        if (recipeService.exists(id) && !auth.getName().equals(recipeService.get(id).getAuthor().getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        boolean deleted = recipeService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
