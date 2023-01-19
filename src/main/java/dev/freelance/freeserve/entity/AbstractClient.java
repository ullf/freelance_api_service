package dev.freelance.freeserve.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;

/*
This code defines a Java class called "AbstractClient" which is an Entity class and implements UserDetails interface.
The class is annotated with several JPA annotations such as @Table, @AllArgsConstructor, @NoArgsConstructor, @Getter, @Setter, and @Entity.
The class has two fields, "id" and "name",which are annotated with JPA's @Id and @Column annotations respectively. The "id" field is set to
be generated automatically and the "name" field is mapped to the "abstract_name" column in the database table.
  The class also has getters and setters for these fields.
 */
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AbstractClient implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "abstract_name")
    private String name;
    @Column(name = "abstract_surname")
    private String surname;
    @Column(name = "indicator")
    private boolean indicator; // true: freelancer false: buyer
    @Column(unique = true)
    private String nickname;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
