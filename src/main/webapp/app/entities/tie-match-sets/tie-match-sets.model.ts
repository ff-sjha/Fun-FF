import { BaseEntity } from './../../shared';

export class TieMatchSets implements BaseEntity {
    constructor(
        public id?: number,
        public setNumber?: number,
        public team1Points?: number,
        public team2Points?: number,
        public tieMatch?: BaseEntity,
    ) {
    }
}
