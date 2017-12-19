import { BaseEntity } from './../../shared';

export class Season implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public active?: boolean,
        public winner?: string,
        public tournaments?: BaseEntity[],
    ) {
        this.active = false;
    }
}
