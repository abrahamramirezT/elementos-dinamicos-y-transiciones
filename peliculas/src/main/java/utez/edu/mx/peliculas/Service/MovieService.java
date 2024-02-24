package utez.edu.mx.peliculas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.peliculas.Model.Category;
import utez.edu.mx.peliculas.Model.Movie;
import utez.edu.mx.peliculas.Repository.CategoryRepository;
import utez.edu.mx.peliculas.Repository.MovieRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repo;
    @Autowired
    private CategoryRepository repo2;

    public List<Movie> getAllMovies() {
        return repo.findAll();
    }

    public Movie saveMovie(Movie bean){
        return repo.save(bean);
    }

    public boolean deleteMovie(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    public Movie updateMovie(Long id, Movie movie) {
        Optional<Movie> existingMovieOptional = repo.findById(id);
        if (existingMovieOptional.isPresent()) {
            Movie existingMovie = existingMovieOptional.get();
            existingMovie.setName(movie.getName());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setCategory(movie.getCategory());
            existingMovie.setDirectorName(movie.getDirectorName());
            existingMovie.setPublicationDate(movie.getPublicationDate());
            return repo.save(existingMovie);
        } else {
            return null;
        }
    }

    public Set<Movie> searchMovies(String searchWord){
        List<Movie> moviesByName = repo.findByNameContaining(searchWord);
        List<Movie> moviesByDirector = repo.findByDirectorNameContaining(searchWord);
        Category category = repo2.findByName(searchWord);
        List<Movie> moviesByCategory = new ArrayList<>();
        if (category != null) {
            moviesByCategory = repo.findByCategory(category);
        }
        Set<Movie> searchResults = new HashSet<>();
        searchResults.addAll(moviesByName);
        searchResults.addAll(moviesByDirector);
        searchResults.addAll(moviesByCategory);
        return searchResults;
    }

    public List<Movie> findMoviesBetweenPublicationDates(LocalDate startDate, LocalDate endDate) {
        return repo.findByPublicationDateBetween(startDate, endDate);
    }

    public List<Movie> getMoviesOrderedByPublicationDateDesc() {
        return repo.findByOrderByPublicationDateDesc();
    }
}
