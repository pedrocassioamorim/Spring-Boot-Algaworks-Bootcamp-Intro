package org.pedroamorim.projetobootcamp.api.controllers;

import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.domain.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.domain.model.Cidade;
import org.pedroamorim.projetobootcamp.domain.service.CadastroCidadeService;
import org.pedroamorim.projetobootcamp.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cidadeService;


    @GetMapping
    public ResponseEntity<List<Cidade>> listar (){
        List<Cidade> cidades = cidadeService.listar();
        return ResponseEntity.ok().body(cidades);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long Id){
        try{
            Cidade cidade = cidadeService.buscar(Id);
            return ResponseEntity.ok().body(cidade);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cidade> adicionar (@RequestBody Cidade cidade){
        Cidade cidadeSalvar = cidadeService.salvar(cidade);
        return ResponseEntity.created(URI.create("URI_simulated")).body(cidadeSalvar);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Cidade> atualizar (@RequestBody Cidade cidade, @PathVariable Long Id){
        try{
            Cidade cidadeAtualizar = cidadeService.atualizar(Id, cidade);
            return ResponseEntity.ok().body(cidadeAtualizar);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Cidade> excluir (@PathVariable Long Id){
        try{
            cidadeService.excluir(Id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException f){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}