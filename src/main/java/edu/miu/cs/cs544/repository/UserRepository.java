package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.domain.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("Select u from User u where u.userName = :username")
    Optional<User> finByUsername(@Param("username") String username);
}
