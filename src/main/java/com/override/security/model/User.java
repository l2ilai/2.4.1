package com.override.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @NotEmpty(message = "Имя не должно быть пустым!")
    @Size(min = 3, max = 20 , message = "Имя должно быть от 3 до 20 символов!")
    private String nick;

    @Column
    @NotEmpty(message = "Пароль не должнен быть пустым!")
    @Size(min = 3, max = 61 , message = "Пароль должно быть от 3 до 61 символов!")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotEmpty(message = "Должна быть хотя бы одна роль!")
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String nick, String password, Set<Role> roles) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String name) {
        this.nick = name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nick;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + nick + '\'' +
                '}';
    }
}
