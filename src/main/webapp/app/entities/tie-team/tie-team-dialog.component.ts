import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { TieTeam } from './tie-team.model';
import { TieTeamPopupService } from './tie-team-popup.service';
import { TieTeamService } from './tie-team.service';
import { Player, PlayerService } from '../player';
import { ITEMS_PER_PAGE, ResponseWrapper} from '../../shared';
import {Franchise, FranchiseService} from '../franchise';

@Component({
    selector: 'fafi-tie-team-dialog',
    templateUrl: './tie-team-dialog.component.html'
})
export class TieTeamDialogComponent implements OnInit {

    tieTeam: TieTeam;
    isSaving: boolean;
    availablePlayers: Player[] = [];
    selAvailList: Player[] = [];
    selSelectList: Player[] = [];
    franchises: Franchise[] = [];
    franchiseId: number;

    constructor(
        public activeModal: NgbActiveModal,
        private tieTeamService: TieTeamService,
        private playerService: PlayerService,
        private franchiseService: FranchiseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.franchiseService.query()
            .subscribe((res: ResponseWrapper) => {
                this.franchises = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        /*this.playerService
            .query()
            .subscribe((res: ResponseWrapper) => {
                this.availablePlayers = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));*/
        this.playerService.query({
            page: -1,
            size: ITEMS_PER_PAGE,
            sort: ['id,asc']}).subscribe(
            (res: ResponseWrapper) => this.availablePlayers = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.tieTeam.tiePlayers = [];
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

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPlayerById(index: number, item: Player) {
        return item.id;
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

    btnRight() {
        this.selAvailList.forEach((value) => {
            this.tieTeam.tiePlayers.push(value);
            this.availablePlayers.splice(this.availablePlayers.indexOf(value, 0), 1);
        });
        this.selAvailList = [];
    };

    btnLeft() {
        this.selSelectList.forEach((value) => {
            this.availablePlayers.push(value);
            this.tieTeam.tiePlayers.splice(this.tieTeam.tiePlayers.indexOf(value, 0), 1);
        });
        this.selSelectList = [];
    };

    loadFranchisePlayers() {
        console.log('franchiseId.equals=' + this.franchiseId);
        this.playerService
            .query({filter: 'franchiseId.equals=' + this.franchiseId})
            .subscribe((res: ResponseWrapper) => {
                this.availablePlayers = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
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
