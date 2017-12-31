import { BaseEntity } from './../../shared';

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public startDateTime?: any,
        public endDateTime?: any,
        public pointsEarnedByFranchise?: number,
        public matchName?: string,
        public tournamentId?: number,
        public winningFranchiseId?: number,
    ) {
    }
}
