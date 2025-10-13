package com.generattion.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generattion.blogpessoal.model.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

}
