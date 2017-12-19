import { BaseEntity } from './../../shared';

export class Franchise implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public logoPath?: string,
        public owner?: string,
        public iconPlayer?: string,
        public players?: BaseEntity[],
    ) {
    }
}
