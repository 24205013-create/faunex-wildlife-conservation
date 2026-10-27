package com.faunex.faunex.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rangers")
public class Ranger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rangerId;

    @Column(nullable = false)
    private String name;

    private String phone;
    @Column(name = "`rank`")
    private String rangerRank;
    
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ranger", cascade = CascadeType.ALL)
    private List<Incident> incidents;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Incident> getIncidents() { return incidents; }
    public void setIncidents(List<Incident> incidents) { this.incidents = incidents; }

    public Ranger() {}

    public Long getRangerId() { return rangerId; }
    public void setRangerId(Long rangerId) { this.rangerId = rangerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRangerRank() { return rangerRank; }
    public void setRangerRank(String rangerRank) { this.rangerRank = rangerRank; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
}
