package ru.andrienko.springboot.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role", unique = true)
    private String role;

    public Authority() {
    }

    public Authority(String role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return id == authority.id &&
                Objects.equals(role, authority.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "role=" + role;
    }
}
