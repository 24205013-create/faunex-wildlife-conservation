package com.faunex.faunex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "species")
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speciesId;

    @Column(nullable = false)
    private String commonName;

    private String scientificName;
    private String conservationStatus;
    private Integer avgLifespan;
    private String dietStatus;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL)
    private List<Animal> animals;

    public Species() {}

    public Long getSpeciesId() { return speciesId; }
    public void setSpeciesId(Long speciesId) { this.speciesId = speciesId; }

    public String getCommonName() { return commonName; }
    public void setCommonName(String commonName) { this.commonName = commonName; }

    public String getScientificName() { return scientificName; }
    public void setScientificName(String scientificName) { this.scientificName = scientificName; }

    public String getConservationStatus() { return conservationStatus; }
    public void setConservationStatus(String conservationStatus) { this.conservationStatus = conservationStatus; }

    public Integer getAvgLifespan() { return avgLifespan; }
    public void setAvgLifespan(Integer avgLifespan) { this.avgLifespan = avgLifespan; }

    public String getDietStatus() { return dietStatus; }
    public void setDietStatus(String dietStatus) { this.dietStatus = dietStatus; }

    public List<Animal> getAnimals() { return animals; }
    public void setAnimals(List<Animal> animals) { this.animals = animals; }
}
