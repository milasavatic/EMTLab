package mk.ukim.finki.emt.lab.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM books_by_author_per_user")
@Immutable
public class BooksPerAuthorPerWishlistView {
    @Id
    @Column(name = "author_id")
    private Long AuthorId;

    @Column(name = "wishlist_id")
    private Long WishlistId;

    @Column(name = "num_books")
    private Integer numBooks;
}
