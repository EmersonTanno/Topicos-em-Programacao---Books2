package com.crud.demo.book;

import com.crud.demo.book.dto.BookRequestDTO;
import com.crud.demo.book.dto.BookResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(book -> new BookResponseDTO(book.getId(), book.getNome(), book.getCategoria()))
                .collect(Collectors.toList());
    }

    public Optional<BookResponseDTO> findById(Long id) {
        return bookRepository.findById(id)
                .map(book -> new BookResponseDTO(book.getId(), book.getNome(), book.getCategoria()));
    }

    public BookResponseDTO criarLivro(BookRequestDTO bookDTO) {
        BookModel book = new BookModel();
        book.setNome(bookDTO.getNome());
        book.setCategoria(bookDTO.getCategoria());
        book = bookRepository.save(book);
        return new BookResponseDTO(book.getId(), book.getNome(), book.getCategoria());
    }

    public void deletarLivro(Long id) {
        bookRepository.deleteById(id);
    }

    public BookResponseDTO update(Long id, BookRequestDTO bookDTO) {
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (bookDTO.getNome() != null) {
            book.setNome(bookDTO.getNome());
        }
        if (bookDTO.getCategoria() != null) {
            book.setCategoria(bookDTO.getCategoria());
        }

        book = bookRepository.save(book);
        return new BookResponseDTO(book.getId(), book.getNome(), book.getCategoria());
    }
}
