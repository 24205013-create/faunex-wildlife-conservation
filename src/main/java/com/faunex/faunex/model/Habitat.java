package com.faunex.faunex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "habitats")
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitatId;

    @Column(nullable = false)
    private String habitatName;

    private String location;
    private Double areaSize;
    private String climateType;
    private String protectionLevel;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL)
    private List<Animal> animals;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL)
    private List<VisitRecord> visitRecords;

    public Habitat() {}

    public Long getHabitatId() { return habitatId; }
    public void setHabitatId(Long habitatId) { this.habitatId = habitatId; }

    public String getHabitatName() { return habitatName; }
    public void setHabitatName(String habitatName) { this.habitatName = habitatName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getAreaSize() { return areaSize; }
    public void setAreaSize(Double areaSize) { this.areaSize = areaSize; }

    public String getClimateType() { return climateType; }
    public void setClimateType(String climateType) { this.climateType = climateType; }

    public String getProtectionLevel() { return protectionLevel; }
    public void setProtectionLevel(String protectionLevel) { this.protectionLevel = protectionLevel; }

    public List<Animal> getAnimals() { return animals; }
    public void setAnimals(List<Animal> animals) { this.animals = animals; }

    public List<VisitRecord> getVisitRecords() { return visitRecords; }
    public void setVisitRecords(List<VisitRecord> visitRecords) { this.visitRecords = visitRecords; }
}
