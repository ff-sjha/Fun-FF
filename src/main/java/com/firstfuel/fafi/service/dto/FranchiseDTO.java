package com.firstfuel.fafi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Franchise entity.
 */
public class FranchiseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String logoPath;

    private String owner;

    private String iconPlayer;

    private Long seasonId;

    private String seasonName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIconPlayer() {
        return iconPlayer;
    }

    public void setIconPlayer(String iconPlayer) {
        this.iconPlayer = iconPlayer;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId( Long seasonId ) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName( String seasonName ) {
        this.seasonName = seasonName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FranchiseDTO franchiseDTO = (FranchiseDTO) o;
        if(franchiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), franchiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FranchiseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logoPath='" + getLogoPath() + "'" +
            ", owner='" + getOwner() + "'" +
            ", iconPlayer='" + getIconPlayer() + "'" +
            "}";
    }
}
