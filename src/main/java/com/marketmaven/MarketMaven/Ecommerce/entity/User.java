package com.marketmaven.MarketMaven.Ecommerce.entity;

import com.marketmaven.MarketMaven.Ecommerce.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data //Lombok annotation that generates getters, setters, toString, equals, and hashCode methods for the class.
@Entity //Marks this class as a JPA(Java Persistence API) entity, which means it will be mapped to a database table.
@Table(name="users") //Specifies the table name in database is Users.
@Builder //Lombok annotation that provides a builder pattern for creating instances of this class.
// This allows for more readable and flexible object creation.
@AllArgsConstructor //Lombok annotation that generates a constructor with all fields as parameters.
@NoArgsConstructor //Lombok annotation that generates a no-argument constructor.
// This is useful for JPA, which requires a no-argument constructor to instantiate the entity when retreiveing from databse.

public class User {
    @Id //Marks id as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autogenerates the primary key value.
    private Long id;

    @NotBlank(message = "Name is required") //Validation annotation that field should not be blank.
    private String name;

    @NotBlank(message = "Email is Required")
    @Column(unique = true) //Ensures the email field is unique in the database.
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    @Column(name="phone_number") //Specifies the column name in the database for phoneNumber field.
    @NotBlank(message = "Phone number is Required")
    private String phoneNumber;

    private UserRole role; //field named role of type UserRole ,Store user's role.

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL) //defines one-to-many relationship with OrderItem entity.(one user,many orders).
    private List<OrderItem> orderItemList;

    @OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="user" ) //User is going to have address and address is going to belong to a user,
    // fetch type lazy that is going to get the address when it is needed,
    // Cascade type:whenever a user is deleted the address of that users is going to be deleted too and whenever the user is update the address is updated too.
    private  Address address;

    @Column(name = "created_at")//Maps createdAt to the "created_at" coloumn in the database.
    private final LocalDateTime createdAt=LocalDateTime.now();//sets the creation time of the user.
}
