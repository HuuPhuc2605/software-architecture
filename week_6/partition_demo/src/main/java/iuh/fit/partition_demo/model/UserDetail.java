package iuh.fit.partition_demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_detail")
public class UserDetail {
    @Id
    private int id;
    private String address;
    private String phone;
    private String email;
}