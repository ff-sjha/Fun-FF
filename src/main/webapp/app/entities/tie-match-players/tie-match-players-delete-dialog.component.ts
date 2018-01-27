import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatchPlayers } from './tie-match-players.model';
import { TieMatchPlayersPopupService } from './tie-match-players-popup.service';
import { TieMatchPlayersService } from './tie-match-players.service';

@Component({
    selector: 'fafi-tie-match-players-delete-dialog',
    templateUrl: './tie-match-players-delete-dialog.component.html'
})
export class TieMatchPlayersDeleteDialogComponent {

    tieMatchPlayers: TieMatchPlayers;

    constructor(
        private tieMatchPlayersService: TieMatchPlayersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tieMatchPlayersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tieMatchPlayersListModification',
                content: 'Deleted an tieMatchPlayers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-tie-match-players-delete-popup',
    template: ''
})
export class TieMatchPlayersDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieMatchPlayersPopupService: TieMatchPlayersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tieMatchPlayersPopupService
                .open(TieMatchPlayersDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
