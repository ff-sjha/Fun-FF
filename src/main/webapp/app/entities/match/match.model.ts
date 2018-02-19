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
        public team1Id?: number,
        public team2Id?: number,
        public team3Id?: number,
        public team4Id?: number,
        public team1Points?: number,
        public team2Points?: number,
        public team3Points?: number,
        public team4Points?: number,
    ) {
        this.completed = false;
    }
}
