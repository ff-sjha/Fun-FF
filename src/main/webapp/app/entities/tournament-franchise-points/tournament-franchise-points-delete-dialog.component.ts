import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TournamentFranchisePoints } from './tournament-franchise-points.model';
import { TournamentFranchisePointsPopupService } from './tournament-franchise-points-popup.service';
import { TournamentFranchisePointsService } from './tournament-franchise-points.service';

@Component({
    selector: 'fafi-tournament-franchise-points-delete-dialog',
    templateUrl: './tournament-franchise-points-delete-dialog.component.html'
})
export class TournamentFranchisePointsDeleteDialogComponent {

    tournamentFranchisePoints: TournamentFranchisePoints;

    constructor(
        private tournamentFranchisePointsService: TournamentFranchisePointsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tournamentFranchisePointsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tournamentFranchisePointsListModification',
                content: 'Deleted an tournamentFranchisePoints'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-tournament-franchise-points-delete-popup',
    template: ''
})
export class TournamentFranchisePointsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tournamentFranchisePointsPopupService: TournamentFranchisePointsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tournamentFranchisePointsPopupService
                .open(TournamentFranchisePointsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
