import { BaseEntity } from './../../shared';

export class SeasonsFranchise implements BaseEntity {
    constructor(
        public id?: number,
        public seasonId?: number,
        public franchiseId?: number,
        public ownerId?: number,
        public iconPlayerId?: number,
    ) {
    }
}
