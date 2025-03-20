package com.crud.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
     private BookRepository bookRepository;


    public List<BookModel> findAll(){
        return  bookRepository.findAll();
    }

    public Optional<BookModel> findById(Long id) { return bookRepository.findById(id); }

    public BookModel criarLivro(BookModel bookModel){
        return bookRepository.save(bookModel);
    }

    public void deletarLivro(Long id){
        bookRepository.deleteById(id);
    }

    public BookModel update(Long id, BookModel bookMode){
       BookModel newbook =  bookRepository.findById(id).get();
       newbook.setCategoria(bookMode.getCategoria());
       newbook.setNome(bookMode.getNome());
       return bookRepository.save(newbook);

    }

}
