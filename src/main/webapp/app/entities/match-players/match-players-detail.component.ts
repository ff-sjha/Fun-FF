import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MatchPlayers } from './match-players.model';
import { MatchPlayersService } from './match-players.service';

@Component({
    selector: 'fafi-match-players-detail',
    templateUrl: './match-players-detail.component.html'
})
export class MatchPlayersDetailComponent implements OnInit, OnDestroy {

    matchPlayers: MatchPlayers;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private matchPlayersService: MatchPlayersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMatchPlayers();
    }

    load(id) {
        this.matchPlayersService.find(id).subscribe((matchPlayers) => {
            this.matchPlayers = matchPlayers;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMatchPlayers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'matchPlayersListModification',
            (response) => this.load(this.matchPlayers.id)
        );
    }
}
