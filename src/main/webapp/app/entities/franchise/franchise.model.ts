import { BaseEntity } from './../../shared';

export class Franchise implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public logoPath?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
