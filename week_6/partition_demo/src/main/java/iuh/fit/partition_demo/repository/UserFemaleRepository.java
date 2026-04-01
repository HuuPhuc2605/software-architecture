package iuh.fit.partition_demo.repository;

import iuh.fit.partition_demo.model.UserFemale;
import iuh.fit.partition_demo.model.UserMale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFemaleRepository extends JpaRepository<UserFemale, Integer> {}