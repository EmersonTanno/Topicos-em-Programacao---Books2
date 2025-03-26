package com.crud.demo.book;

import com.crud.demo.book.dto.BookRequestDTO;
import com.crud.demo.book.dto.BookResponseDTO;
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
    public ResponseEntity<List<BookResponseDTO>> listarLivros() {
        List<BookResponseDTO> list = bookService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> criarLivro(@RequestBody BookRequestDTO bookDTO) {
        if (bookDTO.getNome() == null || bookDTO.getCategoria() == null) {
            return ResponseEntity.badRequest().body("Nome ou Categoria do Livro inválido ou não informado.");
        }

        BookResponseDTO response = bookService.criarLivro(bookDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long id) {
        Optional<BookResponseDTO> livroExistente = bookService.findById(id);
        if (livroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BookRequestDTO bookDTO) {
        Optional<BookResponseDTO> livroExistente = bookService.findById(id);
        if (livroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BookResponseDTO response = bookService.update(id, bookDTO);
        return ResponseEntity.ok(response);
    }
}
