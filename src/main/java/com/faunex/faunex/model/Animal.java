package com.faunex.faunex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    @Column(nullable = false)
    private String name;

    private String tagNumber;

    @PrePersist
    public void generateTagNumber() {
        if (this.tagNumber == null || this.tagNumber.trim().isEmpty()) {
            this.tagNumber = "TAG-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    private String gender;
    private Integer age;
    private String healthStatus;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    @ManyToOne
    @JoinColumn(name = "habitat_id")
    private Habitat habitat;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<Incident> incidents;

    public Animal() {}

    public Long getAnimalId() { return animalId; }
    public void setAnimalId(Long animalId) { this.animalId = animalId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTagNumber() { return tagNumber; }
    public void setTagNumber(String tagNumber) { this.tagNumber = tagNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public Species getSpecies() { return species; }
    public void setSpecies(Species species) { this.species = species; }

    public Habitat getHabitat() { return habitat; }
    public void setHabitat(Habitat habitat) { this.habitat = habitat; }

    public List<Incident> getIncidents() { return incidents; }
    public void setIncidents(List<Incident> incidents) { this.incidents = incidents; }
}
