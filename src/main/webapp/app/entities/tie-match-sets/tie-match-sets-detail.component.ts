import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatchSets } from './tie-match-sets.model';
import { TieMatchSetsService } from './tie-match-sets.service';

@Component({
    selector: 'fafi-tie-match-sets-detail',
    templateUrl: './tie-match-sets-detail.component.html'
})
export class TieMatchSetsDetailComponent implements OnInit, OnDestroy {

    tieMatchSets: TieMatchSets;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tieMatchSetsService: TieMatchSetsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTieMatchSets();
    }

    load(id) {
        this.tieMatchSetsService.find(id).subscribe((tieMatchSets) => {
            this.tieMatchSets = tieMatchSets;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTieMatchSets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tieMatchSetsListModification',
            (response) => this.load(this.tieMatchSets.id)
        );
    }
}
