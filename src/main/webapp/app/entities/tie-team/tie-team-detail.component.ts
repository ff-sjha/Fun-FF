import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TieTeam } from './tie-team.model';
import { TieTeamService } from './tie-team.service';

@Component({
    selector: 'fafi-tie-team-detail',
    templateUrl: './tie-team-detail.component.html'
})
export class TieTeamDetailComponent implements OnInit, OnDestroy {

    tieTeam: TieTeam;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tieTeamService: TieTeamService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTieTeams();
    }

    load(id) {
        this.tieTeamService.find(id).subscribe((tieTeam) => {
            this.tieTeam = tieTeam;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTieTeams() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tieTeamListModification',
            (response) => this.load(this.tieTeam.id)
        );
    }
}
