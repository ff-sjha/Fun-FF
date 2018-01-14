import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MatchUmpire } from './match-umpire.model';
import { MatchUmpirePopupService } from './match-umpire-popup.service';
import { MatchUmpireService } from './match-umpire.service';
import { Match, MatchService } from '../match';
import { Player, PlayerService } from '../player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-match-umpire-dialog',
    templateUrl: './match-umpire-dialog.component.html'
})
export class MatchUmpireDialogComponent implements OnInit {

    matchUmpire: MatchUmpire;
    isSaving: boolean;

    matches: Match[];

    players: Player[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private matchUmpireService: MatchUmpireService,
        private matchService: MatchService,
        private playerService: PlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.playerService.query({
            page: 0,
            size: 1000 })
            .subscribe((res: ResponseWrapper) => { this.players = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.matchUmpire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matchUmpireService.update(this.matchUmpire));
        } else {
            this.subscribeToSaveResponse(
                this.matchUmpireService.create(this.matchUmpire));
        }
    }

    private subscribeToSaveResponse(result: Observable<MatchUmpire>) {
        result.subscribe((res: MatchUmpire) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MatchUmpire) {
        this.eventManager.broadcast({ name: 'matchUmpireListModification', content: 'OK'});
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

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-match-umpire-popup',
    template: ''
})
export class MatchUmpirePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchUmpirePopupService: MatchUmpirePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matchUmpirePopupService
                    .open(MatchUmpireDialogComponent as Component, params['id']);
            } else {
                this.matchUmpirePopupService
                    .open(MatchUmpireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
