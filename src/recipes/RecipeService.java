package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
