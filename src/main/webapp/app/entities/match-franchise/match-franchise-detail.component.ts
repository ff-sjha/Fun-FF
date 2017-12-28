import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MatchFranchise } from './match-franchise.model';
import { MatchFranchiseService } from './match-franchise.service';

@Component({
    selector: 'fafi-match-franchise-detail',
    templateUrl: './match-franchise-detail.component.html'
})
export class MatchFranchiseDetailComponent implements OnInit, OnDestroy {

    matchFranchise: MatchFranchise;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private matchFranchiseService: MatchFranchiseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMatchFranchises();
    }

    load(id) {
        this.matchFranchiseService.find(id).subscribe((matchFranchise) => {
            this.matchFranchise = matchFranchise;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMatchFranchises() {
        this.eventSubscriber = this.eventManager.subscribe(
            'matchFranchiseListModification',
            (response) => this.load(this.matchFranchise.id)
        );
    }
}
