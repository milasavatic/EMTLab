package mk.ukim.finki.emt.lab.service.domain.impl;

import mk.ukim.finki.emt.lab.model.domain.Book;
import mk.ukim.finki.emt.lab.model.exceptions.BookNotFoundException;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidAuthorId;
import mk.ukim.finki.emt.lab.model.exceptions.UnfilledArgumentsException;
import mk.ukim.finki.emt.lab.repository.BookRepository;
import mk.ukim.finki.emt.lab.repository.BooksPerAuthorViewRepository;
import mk.ukim.finki.emt.lab.service.domain.AuthorService;
import mk.ukim.finki.emt.lab.service.domain.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    @Override
    public Optional<Book> save(Book book) {
        Optional<Book> savedBook = Optional.empty();
        if(book.getName().isEmpty()){
            throw new UnfilledArgumentsException();
        }
        if(this.authorService.findById(book.getAuthor().getId()).isEmpty()){
            throw new InvalidAuthorId(book.getAuthor().getId());
        }
        if(authorService.findById(book.getAuthor().getId()).isPresent()){
            savedBook = Optional.of(this.bookRepository.save(new Book(book.getName(),
                    book.getCategory(), authorService.findById(book.getAuthor().getId()).get(), book.getAvailableCopies())));
        }
        this.refreshMaterializedView();
        return savedBook;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        if(this.authorService.findById(id).isEmpty()){
            throw new InvalidAuthorId(book.getAuthor().getId());
        }
        if(this.bookRepository.findById(id).isEmpty()){
            throw new BookNotFoundException(id);
        }
        return this.bookRepository.findById(id)
                .map(existingBook -> {
                    if(book.getName() != null) {
                        existingBook.setName(book.getName());
                    }
                    if(book.getCategory() != null) {
                        existingBook.setCategory(book.getCategory());
                    }
                    if(book.getAuthor() != null && authorService.findById(book.getAuthor().getId()).isPresent()) {
                        existingBook.setAuthor(authorService.findById(book.getAuthor().getId()).get());
                    }
                    if(book.getAvailableCopies() != null) {
                        existingBook.setAvailableCopies(book.getAvailableCopies());
                    }
                    Book updatedBook = this.bookRepository.save(existingBook);
                    this.refreshMaterializedView();
                    return updatedBook;
                });

    }

    @Override
    public void deleteById(Long id) {
        if(this.findById(id).isEmpty())
            throw new BookNotFoundException(id);
        this.bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> markAsRented(Long id, String person) {
        return this.bookRepository.findById(id)
                .flatMap(book -> {
                    if(!book.getCondition()){
                        return Optional.empty();
                    }
                    if(book.getAvailableCopies() <= 0){
                        return Optional.empty();
                    }

                    book.setRented(true);
                    book.setAvailableCopies(book.getAvailableCopies() - 1);
                    book.setPersonWhoRented(person);
                    this.bookRepository.save(book);
                    return Optional.of(book);
                });
    }

    @Override
    public List<String> allWhoRented(Long id){
        Optional<Book> book = this.findById(id);
        if(book.isEmpty())
            throw new BookNotFoundException(id);

        Book b = book.get();
        return b.getPersonWhoRented();
    }

    @Override
    public Optional<Book> markAsBadCondition(Long id){
        return this.bookRepository.findById(id)
                .flatMap(book -> {
                    book.setCondition(false);
                    if(!book.getCondition()){
                        deleteById(id);
                    }
                    return Optional.of(book);
                });
    }

    @Override
    public void refreshMaterializedView(){
        booksPerAuthorViewRepository.refreshMaterializedView();
    }
}
