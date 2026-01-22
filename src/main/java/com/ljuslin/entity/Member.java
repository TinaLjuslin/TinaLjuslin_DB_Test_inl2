package com.ljuslin.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Member, holds name, id, level and history of a member
 *
 * @author Tina Ljuslin
 */
@Entity
@Table(name= "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;
    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;
    @Column(name = "email", length = 100, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "level", length = 20, nullable = false)
    private Level level;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> history = new ArrayList<>();
    @Column(name = "active", nullable = false)
    private boolean active;
    public Member() {

    }

    public Member(String firstName, String lastName, Level level) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Level getMemberLevel() {
        return level;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMemberLevel(Level level) {
        this.level = level;
    }

    public void addToHistory(String description) {
        History newHistory = new History(description, this);
        this.history.add(newHistory);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + ", ID: " + getMemberId() + ", Level: "
                + this.level;
    }
}

