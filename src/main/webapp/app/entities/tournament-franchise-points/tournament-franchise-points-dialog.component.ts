import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TournamentFranchisePoints } from './tournament-franchise-points.model';
import { TournamentFranchisePointsPopupService } from './tournament-franchise-points-popup.service';
import { TournamentFranchisePointsService } from './tournament-franchise-points.service';
import { Tournament, TournamentService } from '../tournament';
import { Franchise, FranchiseService } from '../franchise';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-tournament-franchise-points-dialog',
    templateUrl: './tournament-franchise-points-dialog.component.html'
})
export class TournamentFranchisePointsDialogComponent implements OnInit {

    tournamentFranchisePoints: TournamentFranchisePoints;
    isSaving: boolean;

    tournaments: Tournament[];

    franchises: Franchise[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tournamentFranchisePointsService: TournamentFranchisePointsService,
        private tournamentService: TournamentService,
        private franchiseService: FranchiseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tournamentService.query()
            .subscribe((res: ResponseWrapper) => { this.tournaments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.franchiseService.query()
            .subscribe((res: ResponseWrapper) => { this.franchises = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tournamentFranchisePoints.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tournamentFranchisePointsService.update(this.tournamentFranchisePoints));
        } else {
            this.subscribeToSaveResponse(
                this.tournamentFranchisePointsService.create(this.tournamentFranchisePoints));
        }
    }

    private subscribeToSaveResponse(result: Observable<TournamentFranchisePoints>) {
        result.subscribe((res: TournamentFranchisePoints) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TournamentFranchisePoints) {
        this.eventManager.broadcast({ name: 'tournamentFranchisePointsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTournamentById(index: number, item: Tournament) {
        return item.id;
    }

    trackFranchiseById(index: number, item: Franchise) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-tournament-franchise-points-popup',
    template: ''
})
export class TournamentFranchisePointsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tournamentFranchisePointsPopupService: TournamentFranchisePointsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tournamentFranchisePointsPopupService
                    .open(TournamentFranchisePointsDialogComponent as Component, params['id']);
            } else {
                this.tournamentFranchisePointsPopupService
                    .open(TournamentFranchisePointsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
