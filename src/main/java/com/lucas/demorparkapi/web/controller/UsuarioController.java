package com.lucas.demorparkapi.web.controller;


import com.lucas.demorparkapi.entity.Usuario;
import com.lucas.demorparkapi.service.UsuarioService;
import com.lucas.demorparkapi.web.dto.UsuarioCreateDto;
import com.lucas.demorparkapi.web.dto.UsuarioResponseDto;
import com.lucas.demorparkapi.web.dto.UsuarioSenhaDTO;
import com.lucas.demorparkapi.web.dto.mapper.UsuarioMapper;
import com.lucas.demorparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastrado, edição e leitura de um usuario.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;


    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuário",
    responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Usuário e-mail ja cadastrado no sistema.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso nao processado por dados de entrada invalido.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDTO senhaDTO) {
        usuarioService.editarSenha(id, senhaDTO.getSenhaAtual(), senhaDTO.getNovaSenha(), senhaDTO.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
