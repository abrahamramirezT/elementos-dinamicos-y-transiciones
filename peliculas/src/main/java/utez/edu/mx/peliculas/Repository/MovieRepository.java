package utez.edu.mx.peliculas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.peliculas.Model.Category;
import utez.edu.mx.peliculas.Model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByNameContaining(String name);
    List<Movie> findByDirectorNameContaining(String director_name);
    List<Movie> findByCategory(Category category);
    List<Movie> findByPublicationDateBetween(LocalDate startDate, LocalDate endDate);
    List<Movie> findByOrderByPublicationDateDesc();
}
