import { BaseEntity } from './../../shared';

export const enum Games {
    'FOOTBALL',
    ' CHESS',
    ' BADMINTON',
    ' LUDO',
    ' TABLE_TENNIS',
    ' BOX_CRICKET',
    ' UNO',
    ' CARROM',
    'VOLLEY_BALL', 
    'TENNIS'
}

export class Tournament implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public type?: Games,
        public seasonId?: number,
        public matches?: BaseEntity[],
        public winningFranchiseId?: number,
        public playerOfTournamentId?: number,
    ) {
    }
}
