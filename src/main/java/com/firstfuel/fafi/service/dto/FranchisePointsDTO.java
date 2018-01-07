/**
 * 
 */
package com.firstfuel.fafi.service.dto;

/**
 * @author ameethp
 *
 */
public class FranchisePointsDTO {
    private Long id;
    private Long totalPoints;
    private String name;
    
    public FranchisePointsDTO(Long id, String name, Long totalPoints) {
        super();
        this.id = id;
        this.name = name;
        this.totalPoints = totalPoints;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
