package iuh.fit.partition_demo.repository;

import iuh.fit.partition_demo.model.UserBasic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicRepository extends JpaRepository<UserBasic, Integer> {}