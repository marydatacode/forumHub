package com.example.desafioforumhub_api.topico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    public Topico criarTopico(@Valid Topico topico) {
        Optional<Topico> existingTopico = topicoRepository.findByTituloAndMensagem(topico.getTitulo(), topico.getMensagem());
        if (existingTopico.isPresent()) {
            throw new IllegalArgumentException("Tópico duplicado.");
        }
        return topicoRepository.save(topico);
    }

    public List<Topico> listarTopicos() {
        return topicoRepository.findAll();
    }

    public Optional<Topico> detalharTopico(Long id) {
        return topicoRepository.findById(id);
    }

    @Transactional
    public Topico atualizarTopico(Long id, @Valid Topico topicoAtualizado) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado."));
        topico.setTitulo(topicoAtualizado.getTitulo());
        topico.setMensagem(topicoAtualizado.getMensagem());
        topico.setAutor(topicoAtualizado.getAutor());
        topico.setCurso(topicoAtualizado.getCurso());
        return topicoRepository.save(topico);
    }

    @Transactional
    public void excluirTopico(Long id) {
        topicoRepository.deleteById(id);
    }
}
