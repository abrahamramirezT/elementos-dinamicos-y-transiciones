package utez.edu.mx.peliculas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.peliculas.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String searchWord);
}
