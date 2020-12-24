package ru.andrienko.springboot.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "authority_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))

    private Set<Authority> authorities;

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public User() {
    }

    public User(long id, String login, String password, Set<Authority> authorities) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public User(String login, String password,Set<Authority> authorities) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
