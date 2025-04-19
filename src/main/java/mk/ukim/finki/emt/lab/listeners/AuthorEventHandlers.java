package mk.ukim.finki.emt.lab.listeners;

import mk.ukim.finki.emt.lab.events.BookCreatedEvent;
import mk.ukim.finki.emt.lab.service.domain.AuthorService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventHandlers {

    private final AuthorService authorService;

    public AuthorEventHandlers(AuthorService authorService) {
        this.authorService = authorService;
    }

    @EventListener
    public void onProductCreated(BookCreatedEvent event) {
        this.authorService.refreshMaterializedView();
    }
}
