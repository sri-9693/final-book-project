package com.kks.Project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kks.Project.entity.Author;
import com.kks.Project.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Retrieve all authors
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();
        if (!authorList.isEmpty())
            return new ResponseEntity<List<Author>>(authorList, HttpStatus.OK);
        return new ResponseEntity<List<Author>>(authorList, HttpStatus.NOT_FOUND);
    }

    // Retrieve an author by authorId
    @GetMapping(value = "/{authorId}", produces = "application/json")
    public ResponseEntity<Author> getAuthorByAuthorId(@PathVariable int authorId) {
        Author author = authorService.getAuthorByAuthorId(authorId);
        if (author != null)
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        return new ResponseEntity<Author>(author, HttpStatus.NOT_FOUND);
    }

    // Insert an author
    @PostMapping(value = "/", consumes = "application/json")
    public HttpStatus insertAuthor(@RequestBody Author author) {
        authorService.insertOrModifyAuthor(author);
        return HttpStatus.OK;
    }

    // Modify an author
    @PutMapping(value = "/", consumes = "application/json")
    public HttpStatus modifyAuthor(@RequestBody Author author) {
        authorService.insertOrModifyAuthor(author);
        return HttpStatus.OK;
    }

    // Delete an author by authorId
    @DeleteMapping("/{authorId}")
    public HttpStatus deleteAuthor(@PathVariable int authorId) {
        if (authorService.deleteAuthorByAuthorId(authorId))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }

    // @ExceptionHandler(ResourceNotModifiedException.class)
    // public HttpStatus notModifiedExceptionHandler() {
    //     return HttpStatus.NOT_MODIFIED;
    // }
}
