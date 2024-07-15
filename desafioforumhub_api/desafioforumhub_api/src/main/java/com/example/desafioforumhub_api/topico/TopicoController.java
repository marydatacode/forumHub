package com.example.desafioforumhub_api.topico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<Topico> criarTopico(@Valid @RequestBody Topico topico) {
        Topico novoTopico = topicoService.criarTopico(topico);
        return ResponseEntity.created(URI.create("/topicos/" + novoTopico.getId())).body(novoTopico);
    }

    @GetMapping
    public List<Topico> listarTopicos() {
        return topicoService.listarTopicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalharTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoService.detalharTopico(id);
        return topico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico topico) {
        Topico topicoAtualizado = topicoService.atualizarTopico(id, topico);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTopico(@PathVariable Long id) {
        topicoService.excluirTopico(id);
        return ResponseEntity.noContent().build();
    }
}
