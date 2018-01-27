import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatchPlayers } from './tie-match-players.model';
import { TieMatchPlayersService } from './tie-match-players.service';

@Component({
    selector: 'fafi-tie-match-players-detail',
    templateUrl: './tie-match-players-detail.component.html'
})
export class TieMatchPlayersDetailComponent implements OnInit, OnDestroy {

    tieMatchPlayers: TieMatchPlayers;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tieMatchPlayersService: TieMatchPlayersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTieMatchPlayers();
    }

    load(id) {
        this.tieMatchPlayersService.find(id).subscribe((tieMatchPlayers) => {
            this.tieMatchPlayers = tieMatchPlayers;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTieMatchPlayers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tieMatchPlayersListModification',
            (response) => this.load(this.tieMatchPlayers.id)
        );
    }
}
