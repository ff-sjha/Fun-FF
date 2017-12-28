package com.firstfuel.fafi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SeasonsFranchisePlayer entity.
 */
public class SeasonsFranchisePlayerDTO implements Serializable {

    private Long id;

    @NotNull
    private Double bidPrice;

    private Long seasonsFranchiseId;

    private String seasonsFranchiseName;

    private Long playerId;

    private String playerFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getSeasonsFranchiseId() {
        return seasonsFranchiseId;
    }

    public void setSeasonsFranchiseId(Long seasonsFranchiseId) {
        this.seasonsFranchiseId = seasonsFranchiseId;
    }

    public String getSeasonsFranchiseName() {
		return seasonsFranchiseName;
	}

	public void setSeasonsFranchiseName(String seasonsFranchiseName) {
		this.seasonsFranchiseName = seasonsFranchiseName;
	}

	public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerFirstName() {
        return playerFirstName;
    }

    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = (SeasonsFranchisePlayerDTO) o;
        if(seasonsFranchisePlayerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonsFranchisePlayerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonsFranchisePlayerDTO{" +
            "id=" + getId() +
            ", bidPrice=" + getBidPrice() +
            "}";
    }
}
