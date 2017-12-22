import { BaseEntity } from './../../shared';

export class TieMatch implements BaseEntity {
    constructor(
        public id?: number,
        public matchId?: number,
        public team1Id?: number,
        public team2Id?: number,
        public winnerId?: number,
        public team1Name?: string,
        public team2Name?: string,
        public winnerName?: string,
    ) {
    }
}
