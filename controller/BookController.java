package com.kks.Project.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kks.Project.entity.Book;
import com.kks.Project.service.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = {"http://localhost:4200"})
public class BookController {

    @Autowired
    private BookService bookService;

    // Retrieve all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookList = bookService.getAllBooks();
        if (!bookList.isEmpty())
            return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
        return new ResponseEntity<List<Book>>(bookList, HttpStatus.NOT_FOUND);
    }

    // Retrieve a book by bookId
    @GetMapping(value = "/{bookId}", produces = "application/json")
    public ResponseEntity<Book> getBookByBookId(@PathVariable int bookId) {
        Book book = bookService.getBookByBookId(bookId);
        if (book != null)
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        return new ResponseEntity<Book>(book, HttpStatus.NOT_FOUND);
    }

    // Insert a book
    @PostMapping(value = "/")
    public HttpStatus insertBook(@RequestBody Book book) {
        System.out.println(book.getBookID() + " " + book.getTitle());
        bookService.insertOrModifyBook(book);
        return HttpStatus.OK;
    }

    // Modify a book
    @PutMapping(value = "/")
    public HttpStatus modifyBook(@RequestBody Book book) {
        bookService.insertOrModifyBook(book);
        return HttpStatus.OK;
    }

    // Delete a book by bookId
    @DeleteMapping("/{bookId}")
    public HttpStatus deleteBook(@PathVariable int bookId) {
        if (bookService.deleteBookByBookId(bookId))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }

    // Search for books by name
    @GetMapping(value = "/search/{name}")
    public List<Book> searchBooks(@PathVariable String name) {
        return bookService.searchBooksBy(name);
    }
}