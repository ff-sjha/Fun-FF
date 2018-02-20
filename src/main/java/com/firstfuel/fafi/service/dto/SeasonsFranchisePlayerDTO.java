package com.firstfuel.fafi.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the SeasonsFranchisePlayer entity.
 */
public class SeasonsFranchisePlayerDTO implements Serializable {

	private Long id;

	@NotNull
	private Double bidPrice;

	private Long seasonsFranchiseId;

	private String seasonsFranchiseFranchiseName;
	
	private String seasonsFranchiseSeasonName;

	private Long playerId;

	private String playerFirstName;

	private String playerLastName;

	private Boolean active;
	
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


	public String getSeasonsFranchiseFranchiseName() {
		return seasonsFranchiseFranchiseName;
	}

	public void setSeasonsFranchiseFranchiseName(String seasonsFranchiseFranchiseName) {
		this.seasonsFranchiseFranchiseName = seasonsFranchiseFranchiseName;
	}

	public String getSeasonsFranchiseSeasonName() {
		return seasonsFranchiseSeasonName;
	}

	public void setSeasonsFranchiseSeasonName(String seasonsFranchiseSeasonName) {
		this.seasonsFranchiseSeasonName = seasonsFranchiseSeasonName;
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

	public String getPlayerLastName() {
		return playerLastName;
	}

	public void setPlayerLastName(String playerLastName) {
		this.playerLastName = playerLastName;
	}

	public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
		if (seasonsFranchisePlayerDTO.getId() == null || getId() == null) {
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
		return "SeasonsFranchisePlayerDTO{" + "id=" + getId() + ", bidPrice=" + getBidPrice() + "}";
	}
}
