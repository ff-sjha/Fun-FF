/**
 * 
 */
package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author ameethp
 *
 */
public class FranchisePlayersDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5171879280907344724L;
    private Long franchiseId;
    private String franchiseName;
    private List<MatchPlayersDTO> players;
    
    
    public FranchisePlayersDTO(Long franchiseId, String franchiseName, List<MatchPlayersDTO> players) {
        super();
        this.franchiseId = franchiseId;
        this.franchiseName = franchiseName;
        this.players = players;
    }
    public Long getFranchiseId() {
        return franchiseId;
    }
    public void setFranchiseId(Long franchiseId) {
        this.franchiseId = franchiseId;
    }
    public String getFranchiseName() {
        return franchiseName;
    }
    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }
    public List<MatchPlayersDTO> getPlayers() {
        return players;
    }
    public void setPlayers(List<MatchPlayersDTO> players) {
        this.players = players;
    }
    
}
