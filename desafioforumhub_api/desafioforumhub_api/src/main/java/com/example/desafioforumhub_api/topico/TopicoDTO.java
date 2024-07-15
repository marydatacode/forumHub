package com.example.desafioforumhub_api.topico;


import com.example.desafioforumhub_api.topico.Topico;
import com.example.forumhub.repository.TopicoRepository;
import com.example.desafioforumhub_api.usuario.UsuarioRepository;
import com.example.forumhub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Validated TopicoForm form) {
        var autor = usuarioRepository.findByEmail(form.autorEmail())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
        var curso = cursoRepository.findByNome(form.nomeCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(form.titulo());
        topico.setMensagem(form.mensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);

        URI uri = URI.create("/topicos/" + topico.getId());
        return ResponseEntity.created(uri).body(new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        ));
    }

    @GetMapping
    public List<TopicoDTO> listar() {
        return topicoRepository.findAll().stream()
                .map(topico -> new TopicoDTO(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensagem(),
                        topico.getDataCriacao(),
                        topico.getStatus(),
                        topico.getAutor().getNome(),
                        topico.getCurso().getNome()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> detalhar(@PathVariable Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
        return ResponseEntity.ok(new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Validated TopicoForm form) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
        var autor = usuarioRepository.findByEmail(form.autorEmail())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
        var curso = cursoRepository.findByNome(form.nomeCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        topico.setTitulo(form.titulo());
        topico.setMensagem(form.mensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);

        return ResponseEntity.ok(new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build
