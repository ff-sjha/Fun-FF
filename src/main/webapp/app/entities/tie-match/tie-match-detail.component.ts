import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatch } from './tie-match.model';
import { TieMatchService } from './tie-match.service';

@Component({
    selector: 'fafi-tie-match-detail',
    templateUrl: './tie-match-detail.component.html'
})
export class TieMatchDetailComponent implements OnInit, OnDestroy {

    tieMatch: TieMatch;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tieMatchService: TieMatchService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTieMatches();
    }

    load(id) {
        this.tieMatchService.find(id).subscribe((tieMatch) => {
            this.tieMatch = tieMatch;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTieMatches() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tieMatchListModification',
            (response) => this.load(this.tieMatch.id)
        );
    }
}
