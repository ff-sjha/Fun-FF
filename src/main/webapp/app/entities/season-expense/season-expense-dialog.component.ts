import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SeasonExpense } from './season-expense.model';
import { SeasonExpensePopupService } from './season-expense-popup.service';
import { SeasonExpenseService } from './season-expense.service';
import { Tournament, TournamentService } from '../tournament';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-season-expense-dialog',
    templateUrl: './season-expense-dialog.component.html'
})
export class SeasonExpenseDialogComponent implements OnInit {

    seasonExpense: SeasonExpense;
    isSaving: boolean;

    tournaments: Tournament[];
    incurredDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seasonExpenseService: SeasonExpenseService,
        private tournamentService: TournamentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tournamentService.query()
            .subscribe((res: ResponseWrapper) => { this.tournaments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seasonExpense.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seasonExpenseService.update(this.seasonExpense));
        } else {
            this.subscribeToSaveResponse(
                this.seasonExpenseService.create(this.seasonExpense));
        }
    }

    private subscribeToSaveResponse(result: Observable<SeasonExpense>) {
        result.subscribe((res: SeasonExpense) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SeasonExpense) {
        this.eventManager.broadcast({ name: 'seasonExpenseListModification', content: 'OK'});
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
}

@Component({
    selector: 'fafi-season-expense-popup',
    template: ''
})
export class SeasonExpensePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonExpensePopupService: SeasonExpensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seasonExpensePopupService
                    .open(SeasonExpenseDialogComponent as Component, params['id']);
            } else {
                this.seasonExpensePopupService
                    .open(SeasonExpenseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
