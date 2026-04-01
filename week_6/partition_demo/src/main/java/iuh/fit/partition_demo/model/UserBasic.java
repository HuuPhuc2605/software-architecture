package iuh.fit.partition_demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_basic")
public class UserBasic{
    @Id
    private int id;
    private String name;
    private String gender;
}