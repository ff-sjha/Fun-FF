import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TieTeam } from './tie-team.model';
import { TieTeamPopupService } from './tie-team-popup.service';
import { TieTeamService } from './tie-team.service';

@Component({
    selector: 'fafi-tie-team-delete-dialog',
    templateUrl: './tie-team-delete-dialog.component.html'
})
export class TieTeamDeleteDialogComponent {

    tieTeam: TieTeam;

    constructor(
        private tieTeamService: TieTeamService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tieTeamService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tieTeamListModification',
                content: 'Deleted an tieTeam'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-tie-team-delete-popup',
    template: ''
})
export class TieTeamDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieTeamPopupService: TieTeamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tieTeamPopupService
                .open(TieTeamDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
