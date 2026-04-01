package iuh.fit.partition_demo.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity")
public class UserActivity {
    @Id
    private int id;
    private LocalDateTime lastLogin;
    private String actions;
}