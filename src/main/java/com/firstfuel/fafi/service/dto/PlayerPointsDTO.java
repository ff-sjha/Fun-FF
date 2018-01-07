package com.firstfuel.fafi.service.dto;

public class PlayerPointsDTO {
    private Long id;
    private Long totalPoints;
    private String firstName;
    private String lastName;
    private Long totalMatches;
    
    
    public PlayerPointsDTO(Long id, String firstName, String lastName, Long totalPoints, Long totalMatches) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPoints = totalPoints;
        this.totalMatches = totalMatches;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Long getTotalMatches() {
        return totalMatches;
    }
    public void setTotalMatches(Long totalMatches) {
        this.totalMatches = totalMatches;
    }
    
}