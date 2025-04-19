package mk.ukim.finki.emt.lab.listeners;

import mk.ukim.finki.emt.lab.events.BookCreatedEvent;
import mk.ukim.finki.emt.lab.service.domain.BookService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookEventHandlers {

    private final BookService bookService;

    public BookEventHandlers(BookService bookService) {
        this.bookService = bookService;
    }

    @EventListener
    public void onProductCreated(BookCreatedEvent event) {
        this.bookService.refreshMaterializedView();
    }
}
