package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.dto.UserDTO;
import br.com.icorrespondencia.api.mapper.UserMapper;
import br.com.icorrespondencia.api.repository.UserRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<UserDTO, Long> {

    private final UserRepository repo;

    private final UserMapper mapper;

    @Override
    public List<UserDTO> index() {
        return repo
                .findAllByExcludedAtIsNull()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO show(final Long id) {
        return repo
                .findById(id)
                .map(mapper::toDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public UserDTO store(final UserDTO userDTO) {
        final User savedInstance = repo.save(mapper.toDomain(userDTO));

        return mapper.toDTO(savedInstance);
    }

    @Override
    public void update(final UserDTO userDTO) {
        show(userDTO.getId());

        store(userDTO);
    }

    @Override
    public void destroy(Long id) {
        show(id);

        repo.excludeAndDeactivateById(id);
    }

}
