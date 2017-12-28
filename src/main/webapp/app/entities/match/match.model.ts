import { BaseEntity } from './../../shared';

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public startDateTime?: any,
        public endDateTime?: any,
        public matchNumber?: number,
        public pointsEarnedByFranchise?: number,
        public tournamentId?: number,
        public winningFranchiseId?: number,
    ) {
    }
}
