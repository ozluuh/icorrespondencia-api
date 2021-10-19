package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Delivery;
import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.repository.UserRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<User, UUID> {

    private final UserRepository repo;

    @Override
    public List<User> index() {
        return repo.findAllByExcludedAtIsNull();
    }

    @Override
    public User show(final UUID id) {
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
    public void destroy(final UUID id) {
        show(id);

        repo.excludeAndDeactivateById(id);
    }

    /**
     * Deactivate user by id
     *
     * @param UUID id
     * @return true if user deactivated with success; else false.
     */
    public boolean deactivate(final UUID id){
        show(id);

        int status = repo.deactivateById(id);

        return status != 0;
    }

    /**
     * Activate user by id
     *
     * @param UUID id
     * @return true if user successfully activated; else false.
     */
    public boolean activate(final UUID id){
        show(id);

        int status = repo.activateById(id);

        return status != 0;
    }

    public User userExists(String username, String password) {
        return repo
                .findByUsernameAndPassword(username, password)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<Delivery> mailings(UUID id) {
        User user = show(id);

        return repo.findAllDeliveriesByRoomId(user.getRole().getRoom().getId());
    }

    public Delivery mailing(UUID id, Long mailingId) {
        List<Delivery> mailings = mailings(id);

        return mailings
                .stream()
                .filter(delivery -> Objects.equals(delivery.getId(), mailingId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    public boolean mailingRead(UUID id, Long mailingId) {
        mailing(id, mailingId);

        return repo.setDeliveryRead(mailingId) > 0;
    }
}
