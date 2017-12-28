import { BaseEntity } from './../../shared';

export class SeasonsFranchisePlayer implements BaseEntity {
    constructor(
        public id?: number,
        public bidPrice?: number,
        public seasonsFranchiseId?: number,
        public playerId?: number,
    ) {
    }
}
