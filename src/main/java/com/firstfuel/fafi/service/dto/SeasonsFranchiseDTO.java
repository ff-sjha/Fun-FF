package com.firstfuel.fafi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SeasonsFranchise entity.
 */
public class SeasonsFranchiseDTO implements Serializable {

    private Long id;

    private Long seasonId;

    private String displayName;
    
    private String seasonName;

    private Long franchiseId;

    private String franchiseName;

    private Long ownerId;

    private String ownerFirstName;

    private Long iconPlayerId;

    private String iconPlayerFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long playerId) {
        this.ownerId = playerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String playerFirstName) {
        this.ownerFirstName = playerFirstName;
    }

    public Long getIconPlayerId() {
        return iconPlayerId;
    }

    public void setIconPlayerId(Long playerId) {
        this.iconPlayerId = playerId;
    }

    public String getIconPlayerFirstName() {
        return iconPlayerFirstName;
    }

    public void setIconPlayerFirstName(String playerFirstName) {
        this.iconPlayerFirstName = playerFirstName;
    }

    public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeasonsFranchiseDTO seasonsFranchiseDTO = (SeasonsFranchiseDTO) o;
        if(seasonsFranchiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonsFranchiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonsFranchiseDTO{" +
            "id=" + getId() +
            "}";
    }
}
