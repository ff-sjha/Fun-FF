import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MatchPlayers } from './match-players.model';
import { MatchPlayersPopupService } from './match-players-popup.service';
import { MatchPlayersService } from './match-players.service';
import { Match, MatchService } from '../match';
import { SeasonsFranchisePlayer, SeasonsFranchisePlayerService } from '../seasons-franchise-player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-match-players-dialog',
    templateUrl: './match-players-dialog.component.html'
})
export class MatchPlayersDialogComponent implements OnInit {

    matchPlayers: MatchPlayers;
    isSaving: boolean;

    matches: Match[];

    seasonsfranchiseplayers: SeasonsFranchisePlayer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private matchPlayersService: MatchPlayersService,
        private matchService: MatchService,
        private seasonsFranchisePlayerService: SeasonsFranchisePlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.seasonsFranchisePlayerService.query({
            page: 0,
            size: 1000})
            .subscribe((res: ResponseWrapper) => { this.seasonsfranchiseplayers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.matchPlayers.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matchPlayersService.update(this.matchPlayers));
        } else {
            this.subscribeToSaveResponse(
                this.matchPlayersService.create(this.matchPlayers));
        }
    }

    private subscribeToSaveResponse(result: Observable<MatchPlayers>) {
        result.subscribe((res: MatchPlayers) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MatchPlayers) {
        this.eventManager.broadcast({ name: 'matchPlayersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }

    trackSeasonsFranchisePlayerById(index: number, item: SeasonsFranchisePlayer) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-match-players-popup',
    template: ''
})
export class MatchPlayersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchPlayersPopupService: MatchPlayersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matchPlayersPopupService
                    .open(MatchPlayersDialogComponent as Component, params['id']);
            } else {
                this.matchPlayersPopupService
                    .open(MatchPlayersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
