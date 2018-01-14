/**
 * 
 */
package com.firstfuel.fafi.service.dto;

/**
 * @author ameethp
 *
 */
public class MatchUmpireDTO {
    private Long id;
    private Long matchId;
    private Long umpireId;
    private String firstName;
    private String lastName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMatchId() {
        return matchId;
    }
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
    public Long getUmpireId() {
        return umpireId;
    }
    public void setUmpireId(Long umpireId) {
        this.umpireId = umpireId;
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
    
}
