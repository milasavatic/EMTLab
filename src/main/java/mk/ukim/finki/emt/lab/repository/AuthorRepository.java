package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.projections.AuthorNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<AuthorNameProjection> findAllBy();
}
