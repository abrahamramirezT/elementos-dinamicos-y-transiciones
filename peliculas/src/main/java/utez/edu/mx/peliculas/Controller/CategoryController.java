package utez.edu.mx.peliculas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.peliculas.Model.Category;
import utez.edu.mx.peliculas.Service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public Category saveCategory(@RequestBody(required = true) Category category) {
        return service.saveCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return service.getAllCategories();
    }
}
