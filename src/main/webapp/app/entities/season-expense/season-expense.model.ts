import { BaseEntity } from './../../shared';

export class SeasonExpense implements BaseEntity {
    constructor(
        public id?: number,
        public incurredDate?: any,
        public description?: string,
        public amount?: number,
        public season?: BaseEntity,
    ) {
    }
}
