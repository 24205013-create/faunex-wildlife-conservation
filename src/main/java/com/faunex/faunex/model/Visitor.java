package com.faunex.faunex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;

    @Column(nullable = false)
    private String name;

    private String contact;
    private String purpose;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private List<VisitRecord> visitRecords;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Visitor() {}

    public Long getVisitorId() { return visitorId; }
    public void setVisitorId(Long visitorId) { this.visitorId = visitorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public List<VisitRecord> getVisitRecords() { return visitRecords; }
    public void setVisitRecords(List<VisitRecord> visitRecords) { this.visitRecords = visitRecords; }
}
