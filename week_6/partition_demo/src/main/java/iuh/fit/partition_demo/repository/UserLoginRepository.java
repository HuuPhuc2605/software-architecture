package iuh.fit.partition_demo.repository;

import iuh.fit.partition_demo.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {}