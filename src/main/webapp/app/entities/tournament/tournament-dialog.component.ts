import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tournament } from './tournament.model';
import { TournamentPopupService } from './tournament-popup.service';
import { TournamentService } from './tournament.service';
import { Season, SeasonService } from '../season';
import { Franchise, FranchiseService } from '../franchise';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-tournament-dialog',
    templateUrl: './tournament-dialog.component.html'
})
export class TournamentDialogComponent implements OnInit {

    tournament: Tournament;
    isSaving: boolean;

    seasons: Season[];

    winners: Franchise[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tournamentService: TournamentService,
        private seasonService: SeasonService,
        private franchiseService: FranchiseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seasonService.query()
            .subscribe((res: ResponseWrapper) => { this.seasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.franchiseService
            .query({filter: 'tournament-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.tournament.winnerId) {
                    this.winners = res.json;
                } else {
                    this.franchiseService
                        .find(this.tournament.winnerId)
                        .subscribe((subRes: Franchise) => {
                            this.winners = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tournament.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tournamentService.update(this.tournament));
        } else {
            this.subscribeToSaveResponse(
                this.tournamentService.create(this.tournament));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tournament>) {
        result.subscribe((res: Tournament) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tournament) {
        this.eventManager.broadcast({ name: 'tournamentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSeasonById(index: number, item: Season) {
        return item.id;
    }

    trackFranchiseById(index: number, item: Franchise) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-tournament-popup',
    template: ''
})
export class TournamentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tournamentPopupService: TournamentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tournamentPopupService
                    .open(TournamentDialogComponent as Component, params['id']);
            } else {
                this.tournamentPopupService
                    .open(TournamentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
