package com.lucas.demorparkapi.web.dto.mapper;

import com.lucas.demorparkapi.entity.Usuario;
import com.lucas.demorparkapi.web.dto.UsuarioCreateDto;
import com.lucas.demorparkapi.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto){
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario){
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                String myRole = usuario.getRole().label;
                map().setRole(myRole);
            }
        };
       ModelMapper mapper =  new ModelMapper();
       mapper.addMappings(props);
       return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(UsuarioMapper::toDto).toList();
    }
}
