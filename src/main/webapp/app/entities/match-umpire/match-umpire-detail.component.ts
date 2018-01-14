import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MatchUmpire } from './match-umpire.model';
import { MatchUmpireService } from './match-umpire.service';

@Component({
    selector: 'fafi-match-umpire-detail',
    templateUrl: './match-umpire-detail.component.html'
})
export class MatchUmpireDetailComponent implements OnInit, OnDestroy {

    matchUmpire: MatchUmpire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private matchUmpireService: MatchUmpireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMatchUmpires();
    }

    load(id) {
        this.matchUmpireService.find(id).subscribe((matchUmpire) => {
            this.matchUmpire = matchUmpire;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMatchUmpires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'matchUmpireListModification',
            (response) => this.load(this.matchUmpire.id)
        );
    }
}
