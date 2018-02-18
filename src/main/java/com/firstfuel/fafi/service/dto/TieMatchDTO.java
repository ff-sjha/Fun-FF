package com.firstfuel.fafi.service.dto;

import java.util.List;

import com.firstfuel.fafi.domain.TieMatch;
import com.firstfuel.fafi.domain.TieMatchPlayers;
import com.firstfuel.fafi.domain.TieMatchSets;

public class TieMatchDTO {
    private TieMatch tieMatch;
    private List<TieMatchSets> tieMatchSet;
    private List<TieMatchPlayers> tieMatchPlayers;
    
    
    public TieMatchDTO(TieMatch tieMatch, List<TieMatchSets> tieMatchSet, List<TieMatchPlayers> tieMatchPlayers) {
        super();
        this.tieMatch = tieMatch;
        this.tieMatchSet = tieMatchSet;
        this.tieMatchPlayers = tieMatchPlayers;
    }
    
    public TieMatch getTieMatch() {
        return tieMatch;
    }
    public void setTieMatch(TieMatch tieMatch) {
        this.tieMatch = tieMatch;
    }
    public List<TieMatchSets> getTieMatchSet() {
        return tieMatchSet;
    }
    public void setTieMatchSet(List<TieMatchSets> tieMatchSet) {
        this.tieMatchSet = tieMatchSet;
    }
    public List<TieMatchPlayers> getTieMatchPlayers() {
        return tieMatchPlayers;
    }
    public void setTieMatchPlayers(List<TieMatchPlayers> tieMatchPlayers) {
        this.tieMatchPlayers = tieMatchPlayers;
    }
    
}
