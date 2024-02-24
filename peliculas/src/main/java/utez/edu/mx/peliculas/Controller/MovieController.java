package utez.edu.mx.peliculas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.peliculas.Model.Movie;
import utez.edu.mx.peliculas.Service.MovieService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {
    @Autowired
    private MovieService service;
    @PostMapping
    public Movie saveMovie(@RequestBody(required = true) Movie movie) {
        return service.saveMovie(movie);
    }
    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        boolean deleted = service.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updatedMovie = service.updateMovie(id, movie);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public Set<Movie> searchMovies(@RequestParam("search") String searchWord){
        return service.searchMovies(searchWord);
    }

    @GetMapping("/searchDateRange")
    public List<Movie> searchMoviesBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return service.findMoviesBetweenPublicationDates(startDate, endDate);
    }

    @GetMapping("/latest")
    public List<Movie> getLatestMovies() {
        return service.getMoviesOrderedByPublicationDateDesc();
    }
}