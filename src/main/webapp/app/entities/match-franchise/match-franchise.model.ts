import { BaseEntity } from './../../shared';

export class MatchFranchise implements BaseEntity {
    constructor(
        public id?: number,
        public matchId?: number,
        public seasonsFranchiseId?: number,
    ) {
    }
}
