import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SeasonsFranchisePlayer } from './seasons-franchise-player.model';
import { SeasonsFranchisePlayerPopupService } from './seasons-franchise-player-popup.service';
import { SeasonsFranchisePlayerService } from './seasons-franchise-player.service';
import { SeasonsFranchise, SeasonsFranchiseService } from '../seasons-franchise';
import { Player, PlayerService } from '../player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-seasons-franchise-player-dialog',
    templateUrl: './seasons-franchise-player-dialog.component.html'
})
export class SeasonsFranchisePlayerDialogComponent implements OnInit {

    seasonsFranchisePlayer: SeasonsFranchisePlayer;
    isSaving: boolean;

    seasonsfranchises: SeasonsFranchise[];

    players: Player[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seasonsFranchisePlayerService: SeasonsFranchisePlayerService,
        private seasonsFranchiseService: SeasonsFranchiseService,
        private playerService: PlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seasonsFranchiseService.query()
            .subscribe((res: ResponseWrapper) => { this.seasonsfranchises = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.playerService.query()
            .subscribe((res: ResponseWrapper) => { this.players = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seasonsFranchisePlayer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seasonsFranchisePlayerService.update(this.seasonsFranchisePlayer));
        } else {
            this.subscribeToSaveResponse(
                this.seasonsFranchisePlayerService.create(this.seasonsFranchisePlayer));
        }
    }

    private subscribeToSaveResponse(result: Observable<SeasonsFranchisePlayer>) {
        result.subscribe((res: SeasonsFranchisePlayer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SeasonsFranchisePlayer) {
        this.eventManager.broadcast({ name: 'seasonsFranchisePlayerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSeasonsFranchiseById(index: number, item: SeasonsFranchise) {
        return item.id;
    }

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-seasons-franchise-player-popup',
    template: ''
})
export class SeasonsFranchisePlayerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonsFranchisePlayerPopupService: SeasonsFranchisePlayerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seasonsFranchisePlayerPopupService
                    .open(SeasonsFranchisePlayerDialogComponent as Component, params['id']);
            } else {
                this.seasonsFranchisePlayerPopupService
                    .open(SeasonsFranchisePlayerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
