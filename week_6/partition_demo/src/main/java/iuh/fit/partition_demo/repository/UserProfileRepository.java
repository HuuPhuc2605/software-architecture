package iuh.fit.partition_demo.repository;

import iuh.fit.partition_demo.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {}