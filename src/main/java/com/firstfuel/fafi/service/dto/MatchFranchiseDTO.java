package com.firstfuel.fafi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MatchFranchise entity.
 */
public class MatchFranchiseDTO implements Serializable {

    private Long id;

    private Long matchId;

    private String matchName;

    private Long seasonsFranchiseId;

    private String seasonsFranchiseFranchise;

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

   
    public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public Long getSeasonsFranchiseId() {
        return seasonsFranchiseId;
    }

    public void setSeasonsFranchiseId(Long seasonsFranchiseId) {
        this.seasonsFranchiseId = seasonsFranchiseId;
    }

    public String getSeasonsFranchiseFranchise() {
        return seasonsFranchiseFranchise;
    }

    public void setSeasonsFranchiseFranchise(String seasonsFranchiseFranchise) {
        this.seasonsFranchiseFranchise = seasonsFranchiseFranchise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchFranchiseDTO matchFranchiseDTO = (MatchFranchiseDTO) o;
        if(matchFranchiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchFranchiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchFranchiseDTO{" +
            "id=" + getId() +
            "}";
    }
}
