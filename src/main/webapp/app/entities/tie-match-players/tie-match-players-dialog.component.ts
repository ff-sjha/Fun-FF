import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TieMatchPlayers } from './tie-match-players.model';
import { TieMatchPlayersPopupService } from './tie-match-players-popup.service';
import { TieMatchPlayersService } from './tie-match-players.service';
import { TieMatch, TieMatchService } from '../tie-match';
import { SeasonsFranchisePlayer, SeasonsFranchisePlayerService } from '../seasons-franchise-player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-tie-match-players-dialog',
    templateUrl: './tie-match-players-dialog.component.html'
})
export class TieMatchPlayersDialogComponent implements OnInit {

    tieMatchPlayers: TieMatchPlayers;
    isSaving: boolean;

    tiematches: TieMatch[];

    seasonsfranchiseplayers: SeasonsFranchisePlayer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tieMatchPlayersService: TieMatchPlayersService,
        private tieMatchService: TieMatchService,
        private seasonsFranchisePlayerService: SeasonsFranchisePlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tieMatchService.query()
            .subscribe((res: ResponseWrapper) => { this.tiematches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.seasonsFranchisePlayerService.query( { 'size' : '1000' } )
            .subscribe((res: ResponseWrapper) => { this.seasonsfranchiseplayers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tieMatchPlayers.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tieMatchPlayersService.update(this.tieMatchPlayers));
        } else {
            this.subscribeToSaveResponse(
                this.tieMatchPlayersService.create(this.tieMatchPlayers));
        }
    }

    private subscribeToSaveResponse(result: Observable<TieMatchPlayers>) {
        result.subscribe((res: TieMatchPlayers) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TieMatchPlayers) {
        this.eventManager.broadcast({ name: 'tieMatchPlayersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTieMatchById(index: number, item: TieMatch) {
        return item.id;
    }

    trackSeasonsFranchisePlayerById(index: number, item: SeasonsFranchisePlayer) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-tie-match-players-popup',
    template: ''
})
export class TieMatchPlayersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieMatchPlayersPopupService: TieMatchPlayersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tieMatchPlayersPopupService
                    .open(TieMatchPlayersDialogComponent as Component, params['id']);
            } else {
                this.tieMatchPlayersPopupService
                    .open(TieMatchPlayersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
