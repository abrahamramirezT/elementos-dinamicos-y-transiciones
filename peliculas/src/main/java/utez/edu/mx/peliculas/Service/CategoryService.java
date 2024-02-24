package utez.edu.mx.peliculas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.peliculas.Model.Category;
import utez.edu.mx.peliculas.Repository.CategoryRepository;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public Category saveCategory(Category bean){
        return repo.save(bean);
    }

    public List<Category> getAllCategories() {
        return repo.findAll();
    }
}
