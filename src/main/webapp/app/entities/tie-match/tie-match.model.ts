import { BaseEntity } from './../../shared';

export class TieMatch implements BaseEntity {
    constructor(
        public id?: number,
        public matchId?: number,
        public team1Id?: number,
        public team2Id?: number,
        public winnerId?: number,
    ) {
    }
}
