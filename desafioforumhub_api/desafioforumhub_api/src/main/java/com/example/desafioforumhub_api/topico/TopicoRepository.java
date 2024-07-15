package com.example.desafioforumhub_api.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);
}
