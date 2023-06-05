package com.to.hibernateLibrary.repositories;




import com.to.hibernateLibrary.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllBySurname(String surname);
    List<User> findAll(Specification<User> hasValidCriteria);
}
