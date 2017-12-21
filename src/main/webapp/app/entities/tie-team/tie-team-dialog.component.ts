import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TieTeam } from './tie-team.model';
import { TieTeamPopupService } from './tie-team-popup.service';
import { TieTeamService } from './tie-team.service';

@Component({
    selector: 'fafi-tie-team-dialog',
    templateUrl: './tie-team-dialog.component.html'
})
export class TieTeamDialogComponent implements OnInit {

    tieTeam: TieTeam;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tieTeamService: TieTeamService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tieTeam.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tieTeamService.update(this.tieTeam));
        } else {
            this.subscribeToSaveResponse(
                this.tieTeamService.create(this.tieTeam));
        }
    }

    private subscribeToSaveResponse(result: Observable<TieTeam>) {
        result.subscribe((res: TieTeam) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TieTeam) {
        this.eventManager.broadcast({ name: 'tieTeamListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'fafi-tie-team-popup',
    template: ''
})
export class TieTeamPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieTeamPopupService: TieTeamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tieTeamPopupService
                    .open(TieTeamDialogComponent as Component, params['id']);
            } else {
                this.tieTeamPopupService
                    .open(TieTeamDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
