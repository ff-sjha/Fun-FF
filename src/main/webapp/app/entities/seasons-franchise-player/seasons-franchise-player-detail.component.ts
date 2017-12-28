import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SeasonsFranchisePlayer } from './seasons-franchise-player.model';
import { SeasonsFranchisePlayerService } from './seasons-franchise-player.service';

@Component({
    selector: 'fafi-seasons-franchise-player-detail',
    templateUrl: './seasons-franchise-player-detail.component.html'
})
export class SeasonsFranchisePlayerDetailComponent implements OnInit, OnDestroy {

    seasonsFranchisePlayer: SeasonsFranchisePlayer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seasonsFranchisePlayerService: SeasonsFranchisePlayerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeasonsFranchisePlayers();
    }

    load(id) {
        this.seasonsFranchisePlayerService.find(id).subscribe((seasonsFranchisePlayer) => {
            this.seasonsFranchisePlayer = seasonsFranchisePlayer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeasonsFranchisePlayers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seasonsFranchisePlayerListModification',
            (response) => this.load(this.seasonsFranchisePlayer.id)
        );
    }
}
