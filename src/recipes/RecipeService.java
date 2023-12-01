package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RecipeService {
    @Autowired RecipeRepository repository;

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public Recipe get(int id) {
        return repository.findById(id).orElse(null);
    }

    public int create(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        return repository.save(recipe).getId();
    }

    public boolean deleteById(int id) {
        boolean deleted = false;
        if (repository.existsById(id)) {
            repository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    public boolean updateById(int id, Recipe recipe) {
        Optional<Recipe> existedOptional = repository.findById(id);
        if (existedOptional.isEmpty()) {
            return false;
        }
        recipe.setId(existedOptional.get().getId());
        repository.save(recipe);
        return true;
    }

    public List<Recipe> search(String criteria, String value) {
        return switch (criteria) {
            case "category" -> repository.findByCategoryIgnoreCaseOrderByDateDesc(value);
            case "name" -> repository.findByNameContainingIgnoreCaseOrderByDateDesc(value);
            default -> Collections.emptyList();
        };
    }
}
