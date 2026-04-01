package iuh.fit.partition_demo.repository;

import iuh.fit.partition_demo.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {}
