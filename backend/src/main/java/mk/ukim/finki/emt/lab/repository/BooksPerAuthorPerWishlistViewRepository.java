package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.views.BooksPerAuthorPerWishlistView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BooksPerAuthorPerWishlistViewRepository extends JpaRepository<BooksPerAuthorPerWishlistView, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.books_by_author_per_user", nativeQuery = true)
    void refreshMaterializedView();
}
