package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    private final HashMap<Integer, Recipe> storage = new HashMap<>();

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        if (storage.get(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return storage.get(id);
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Map<String, Integer>> addRecipe(@RequestBody Recipe recipeRequest) {
        int index = storage.size();
        Recipe recipe = storage.put(index, recipeRequest);
        return ResponseEntity.ok(Collections.singletonMap("id",index));
    }
}
