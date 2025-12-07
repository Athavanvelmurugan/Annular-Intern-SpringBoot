package io.thamizh.onetoone.Service;


import io.thamizh.onetoone.Entity.User;
import io.thamizh.onetoone.Entity.UserProfile;
import io.thamizh.onetoone.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user, UserProfile profile) {
        user.setProfile(profile);
        return userRepo.save(user); // cascade saves profile also
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
