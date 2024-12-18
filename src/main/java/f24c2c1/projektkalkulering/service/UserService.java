package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.User;
import f24c2c1.projektkalkulering.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        // Encrypt the password before saving
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    public void updateUser(User user) {
        // Encrypt the password if it is being updated
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(user.getPassword());
        }
        userRepository.update(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
