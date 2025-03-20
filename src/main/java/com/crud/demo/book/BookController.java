package com.crud.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookModel>> listarLivros() {
        List<BookModel> list = bookService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> criarLivro(@RequestBody BookModel bookModel) {
        if (bookModel == null || bookModel.getId() != null) {
            return ResponseEntity.badRequest().body("Livro inválido ou ID não deve ser informado na criação.");
        }
        if(bookModel.getNome() == null || bookModel.getCategoria() == null){
            return ResponseEntity.badRequest().body("Nome ou Categoria do Livro inválido ou  não informado na criação.");
        }
        BookModel response = bookService.criarLivro(bookModel);
        if (response == null) {
            return ResponseEntity.internalServerError().body("Erro ao criar o livro.");
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long id) {
        Optional<BookModel> livroExistente = bookService.findById(id);
        if (livroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BookModel bookModel) {
        Optional<BookModel> livroExistente = bookService.findById(id);
        if (livroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if(bookModel.getNome() == null){
            bookModel.setNome(livroExistente.get().getNome());
        }
        if(bookModel.getCategoria() == null){
            bookModel.setCategoria(livroExistente.get().getCategoria());
        }
        BookModel response = bookService.update(id, bookModel);
        return ResponseEntity.ok(response);
    }
}
