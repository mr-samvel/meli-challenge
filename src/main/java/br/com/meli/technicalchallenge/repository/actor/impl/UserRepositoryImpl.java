package br.com.meli.technicalchallenge.repository.actor.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.actor.User;
import br.com.meli.technicalchallenge.repository.actor.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserRepositoryImpl extends BaseJsonRepository<User, String> implements UserRepository {

    public UserRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                              @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "users.json";
    }

    @Override
    protected TypeReference<List<User>> getTypeReference() {
        return new TypeReference<List<User>>() {};
    }

    @Override
    protected Function<User, String> getKeyExtractor() {
        return User::getId;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(dataMap.get(id));
    }
}