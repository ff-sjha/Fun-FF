import { BaseEntity } from './../../shared';

export const enum TieType {
    'SINGLES',
    ' DOUBLES',
    ' MIX_DOUBLES',
    ' MENS_SINGLES',
    ' WOMENS_SINGLES',
    ' MENS_DOUBLES',
    ' WOMENS_DOUBLES'
}

export class TieMatch implements BaseEntity {
    constructor(
        public id?: number,
        public tieType?: TieType,
        public match?: BaseEntity,
    ) {
    }
}
