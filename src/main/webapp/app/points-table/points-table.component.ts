import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../shared';

import { PointsTablePlayer } from './points-table-player.model';
import { PointsTableFanchise } from './points-table-franchise.model';
import { PointsTableService } from './points-table.service';

@Component({
  selector: 'fafi-points-table',
  templateUrl: './points-table.component.html',
  styleUrls: [
    'points-table.scss'
  ]
})

export class PointsTableComponent implements OnInit, OnDestroy {

    pointsTablePlayer: PointsTablePlayer[];
    pointsTableFranchise: PointsTableFanchise[];
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
    this.pointsTablePlayer = [];
    this.pointsTableFranchise = [];
  }

    loadAll() {
        this.pointsTableService.queryPlayerPoints({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccessPlayer(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.pointsTableService.queryFranchisePoints({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccessFanchise(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
    }

    ngOnDestroy() {
    }

    private onSuccessPlayer(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.pointsTablePlayer.push(data[i]);
        }
    }

    private onSuccessFanchise(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.pointsTableFranchise.push(data[i]);
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
