import { BaseEntity } from './../../shared';

export class Tournament implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public winner?: string,
        public seasonId?: number,
        public matches?: BaseEntity[],
    ) {
    }
}
