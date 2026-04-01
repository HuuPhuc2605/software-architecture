package iuh.fit.partition_demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "user_login")
public class UserLogin {
    @Id
    private int id;
    private String username;
    private String password;
}