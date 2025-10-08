package br.com.meli.technicalchallenge.repository.actor;

import br.com.meli.technicalchallenge.model.domain.actor.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
}