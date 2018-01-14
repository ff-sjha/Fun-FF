import { BaseEntity } from './../../shared';

export class MatchUmpire implements BaseEntity {
    constructor(
        public id?: number,
        public match?: BaseEntity,
        public umpire?: BaseEntity,
    ) {
    }
}
