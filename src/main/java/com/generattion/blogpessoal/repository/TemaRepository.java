package com.generattion.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generattion.blogpessoal.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
	
	// SELECT * FROM tb_temas WHERE descricao LIKE "%descricao%";
}
