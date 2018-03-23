import { BaseEntity } from './../../shared';

export const enum Games {
    'FOOTBALL',
    '  CHESS',
    '  BADMINTON',
    '  LUDO',
    '  TABLE_TENNIS',
    '  BOX_CRICKET',
    ' UNO',
    ' CARROM'
}

export class SportInfo implements BaseEntity {
    constructor(
        public id?: number,
        public game?: Games,
        public rules?: any,
        public scoring_system?: any,
        public points_system?: any,
        public match_system?: any,
    ) {
    }
}
