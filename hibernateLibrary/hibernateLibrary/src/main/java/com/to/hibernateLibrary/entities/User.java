package com.to.hibernateLibrary.entities;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;



@Entity
@Table(name = "\"user\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Check(constraints = "(email)::text ~ '^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$'::text")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_of_registration", nullable = false)
    private Date dateOfRegistration;

    @Column(name = "birthday")
    private Date birthday;

}
