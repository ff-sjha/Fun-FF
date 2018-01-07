import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TournamentFranchisePoints } from './tournament-franchise-points.model';
import { TournamentFranchisePointsService } from './tournament-franchise-points.service';

@Component({
    selector: 'fafi-tournament-franchise-points-detail',
    templateUrl: './tournament-franchise-points-detail.component.html'
})
export class TournamentFranchisePointsDetailComponent implements OnInit, OnDestroy {

    tournamentFranchisePoints: TournamentFranchisePoints;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tournamentFranchisePointsService: TournamentFranchisePointsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTournamentFranchisePoints();
    }

    load(id) {
        this.tournamentFranchisePointsService.find(id).subscribe((tournamentFranchisePoints) => {
            this.tournamentFranchisePoints = tournamentFranchisePoints;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTournamentFranchisePoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tournamentFranchisePointsListModification',
            (response) => this.load(this.tournamentFranchisePoints.id)
        );
    }
}
