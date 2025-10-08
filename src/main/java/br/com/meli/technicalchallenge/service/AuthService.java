package br.com.meli.technicalchallenge.service;

import br.com.meli.technicalchallenge.model.domain.actor.User;
import br.com.meli.technicalchallenge.repository.actor.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Mock method to get the logged-in user.
     * In a real application, this would retrieve the user from the security context.
     *
     * @return the mocked logged-in user
     */
    public Optional<User> getLoggedUser() {
        String mockUserId = "user-1";
        return userRepository.findById(mockUserId);
    }
}