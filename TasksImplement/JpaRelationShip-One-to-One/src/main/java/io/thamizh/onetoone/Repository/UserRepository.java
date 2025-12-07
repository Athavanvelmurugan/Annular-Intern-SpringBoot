package io.thamizh.onetoone.Repository;


import io.thamizh.onetoone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
