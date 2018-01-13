import { BaseEntity } from './../../shared';

export const enum Stage {
    'LEAGUE',
    ' QUARTER_FINAL',
    ' SEMI_FINAL',
    ' FINAL',
    ' PLAYOFF',
    ' KNOCK_OUT'
}

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public startDateTime?: any,
        public endDateTime?: any,
        public pointsEarnedByFranchise?: number,
        public matchName?: string,
        public stage?: Stage,
        public venue?: string,
        public completed?: boolean,
        public tournamentId?: number,
        public winningFranchiseId?: number,
    ) {
        this.completed = false;
    }
}
