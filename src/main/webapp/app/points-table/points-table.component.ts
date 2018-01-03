import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../shared';

import { PointsTablePlayer } from './points-table-player.model';
import { PointsTableService } from './points-table.service';

@Component({
  selector: 'fafi-points-table',
  templateUrl: './points-table.component.html',
  styleUrls: [
    'points-table.scss'
  ]
})

export class PointsTableComponent implements OnInit, OnDestroy {

    pointsTable: PointsTablePlayer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

  constructor(
        private pointsTableService: PointsTableService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
  ) {
    this.pointsTable = [];
  }

    loadAll() {
        this.pointsTableService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
    }

    ngOnDestroy() {
    }

    private onSuccess(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.pointsTable.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

   sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
}
