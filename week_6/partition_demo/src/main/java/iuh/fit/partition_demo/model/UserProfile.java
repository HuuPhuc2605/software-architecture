package iuh.fit.partition_demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private int id;
    private String name;
    private String gender;
    private String email;
}