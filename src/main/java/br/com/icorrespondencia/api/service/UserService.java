package br.com.icorrespondencia.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.repository.UserRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<User, Long> {

    private final UserRepository repo;

    @Override
    public List<User> index() {
        return repo.findAllByExcludedAtIsNull();
    }

    @Override
    public User show(final Long id) {
        return repo
                .getOneByIdAndExcludedAtIsNull(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User store(final User user) {
        return repo.save(user);
    }

    @Override
    public void update(final User user) {
        show(user.getId());

        store(user);
    }

    @Override
    public void destroy(final Long id) {
        show(id);

        repo.excludeAndDeactivateById(id);
    }

}
