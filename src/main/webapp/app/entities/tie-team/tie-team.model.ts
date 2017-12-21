import { BaseEntity } from './../../shared';

export class TieTeam implements BaseEntity {
    constructor(
        public id?: number,
        public points?: number,
        public tiePlayers?: BaseEntity[],
    ) {
    }
}
