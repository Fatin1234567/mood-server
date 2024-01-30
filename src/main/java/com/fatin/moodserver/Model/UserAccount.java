package com.fatin.moodserver.Model;




import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MoodEntry> moodEntries;

    // Getters and Setters for moodEntries
    public Set<MoodEntry> getMoodEntries() {
        return moodEntries;
    }

    public void setMoodEntries(Set<MoodEntry> moodEntries) {
        this.moodEntries = moodEntries;
    }

    // Add convenience methods for adding and removing MoodEntry
    public void addMoodEntry(MoodEntry moodEntry) {
        moodEntries.add(moodEntry);
        moodEntry.setUser(this);
    }

    public void removeMoodEntry(MoodEntry moodEntry) {
        moodEntries.remove(moodEntry);
        moodEntry.setUser(null);
    }

    // Standard getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override equals and hashCode to use the primary key for equality checks

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    // Constructors

    public UserAccount() {
    }

    public UserAccount(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
