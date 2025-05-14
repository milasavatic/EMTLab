package mk.ukim.finki.emt.lab.service.application.impl;

import mk.ukim.finki.emt.lab.dto.create.CreateBookDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayBookDto;
import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.domain.Book;
import mk.ukim.finki.emt.lab.model.exceptions.BookNotFoundException;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidAuthorId;
import mk.ukim.finki.emt.lab.service.application.BookApplicationService;
import mk.ukim.finki.emt.lab.service.domain.AuthorService;
import mk.ukim.finki.emt.lab.service.domain.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookApplicationServiceImpl(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public List<DisplayBookDto> findAll() {
        return this.bookService.findAll().stream().map(DisplayBookDto::from).toList();
    }

    @Override
    public Page<DisplayBookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable)
                .map(DisplayBookDto::from);
    }

    @Override
    public Optional<DisplayBookDto> save(CreateBookDto bookDto) {
        Optional<Author> author = authorService.findById(bookDto.authorId());
        if(author.isEmpty()) {
            throw new InvalidAuthorId(bookDto.authorId());
        }
        return this.bookService.save(bookDto.toBook(author.get()))
                .map(DisplayBookDto::from);
    }

    @Override
    public Optional<DisplayBookDto> findById(Long id) {
        return this.bookService.findById(id).map(DisplayBookDto::from);
    }

    @Override
    public Optional<DisplayBookDto> update(Long id, CreateBookDto bookDto) {
        Optional<Author> author = authorService.findById(bookDto.authorId());
        return bookService.update(id, bookDto.toBook(author.orElse(null)))
                .map(DisplayBookDto::from);
    }

    @Override
    public void deleteById(Long id) {
        this.bookService.deleteById(id);
    }

    @Override
    public Optional<DisplayBookDto> markAsRented(Long id, String person) {
        return this.bookService.findById(id)
                .flatMap(book -> {
                    if (!book.getCondition() || book.getAvailableCopies() <= 0) {
                        return Optional.empty();
                    }

                    book.setRented(true);
                    book.setAvailableCopies(book.getAvailableCopies() - 1);
                    book.setPersonWhoRented(person);
                    this.bookService.save(book);

                    DisplayBookDto displayBookDto = new DisplayBookDto(book.getId(),
                            book.getName(), book.getCategory(),
                            book.getAuthor().getId(), book.getAvailableCopies(), book.getRented(),
                            book.getCondition(), book.getPersonWhoRented());
                    return Optional.of(displayBookDto);
                });
    }

    @Override
    public List<String> allWhoRented(Long id) {
        Optional<Book> book = bookService.findById(id);
        if(book.isEmpty())
            throw new BookNotFoundException(id);

        Book b = book.get();
        return b.getPersonWhoRented();
    }

    @Override
    public Optional<DisplayBookDto> markAsBadCondition(Long id) {
        return this.bookService.findById(id)
                .flatMap(book -> {
                    book.setCondition(false);
                    if(!book.getCondition()){
                        deleteById(id);
                    }
                    DisplayBookDto displayBookDto = new DisplayBookDto(book.getId(),
                            book.getName(), book.getCategory(),
                            book.getAuthor().getId(), book.getAvailableCopies(), book.getRented(),
                            book.getCondition(), book.getPersonWhoRented());
                    return Optional.of(displayBookDto);
                });
    }
}
