import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SeasonsFranchise } from './seasons-franchise.model';
import { SeasonsFranchiseService } from './seasons-franchise.service';

@Component({
    selector: 'fafi-seasons-franchise-detail',
    templateUrl: './seasons-franchise-detail.component.html'
})
export class SeasonsFranchiseDetailComponent implements OnInit, OnDestroy {

    seasonsFranchise: SeasonsFranchise;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seasonsFranchiseService: SeasonsFranchiseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeasonsFranchises();
    }

    load(id) {
        this.seasonsFranchiseService.find(id).subscribe((seasonsFranchise) => {
            this.seasonsFranchise = seasonsFranchise;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeasonsFranchises() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seasonsFranchiseListModification',
            (response) => this.load(this.seasonsFranchise.id)
        );
    }
}
