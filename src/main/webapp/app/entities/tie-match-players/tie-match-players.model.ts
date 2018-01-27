import { BaseEntity } from './../../shared';

export class TieMatchPlayers implements BaseEntity {
    constructor(
        public id?: number,
        public tieMatch?: BaseEntity,
        public seasonsFranchisePlayer?: BaseEntity,
    ) {
    }
}
