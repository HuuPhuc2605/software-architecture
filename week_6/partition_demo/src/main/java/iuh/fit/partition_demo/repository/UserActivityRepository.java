package iuh.fit.partition_demo.repository;

import iuh.fit.partition_demo.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {}
