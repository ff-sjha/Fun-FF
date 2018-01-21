import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../shared';

import { SeasonsFranchise, SeasonsFranchiseService } from '../entities/seasons-franchise';
import { FranchiseTeamComponent } from './';

@Component({
  selector: 'fafi-franchise-info',
  templateUrl: './franchise-info.component.html',
  styleUrls: [
    'franchise-info.scss'
  ]
})

export class FranchiseInfoComponent implements OnInit, OnDestroy {

    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    seasonsFranchises: SeasonsFranchise[];
  constructor(
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private seasonsFranchiseService: SeasonsFranchiseService
  ) {
  }

    loadAll() {
            this.seasonsFranchiseService.queryActiveSeason({}).subscribe(
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
        // this.page = pagingParams.page;
        this.seasonsFranchises = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackId(index: number, item: SeasonsFranchise) {
        return item.id;
    }

   sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
}
