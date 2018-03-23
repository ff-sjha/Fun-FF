import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { SportInfo } from './sport-info.model';
import { SportInfoService } from './sport-info.service';

@Component({
    selector: 'fafi-sport-info-detail',
    templateUrl: './sport-info-detail.component.html'
})
export class SportInfoDetailComponent implements OnInit, OnDestroy {

    sportInfo: SportInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private sportInfoService: SportInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSportInfos();
    }

    load(id) {
        this.sportInfoService.find(id).subscribe((sportInfo) => {
            this.sportInfo = sportInfo;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSportInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sportInfoListModification',
            (response) => this.load(this.sportInfo.id)
        );
    }
}
