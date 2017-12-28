import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MatchPlayers } from './match-players.model';
import { MatchPlayersPopupService } from './match-players-popup.service';
import { MatchPlayersService } from './match-players.service';

@Component({
    selector: 'fafi-match-players-delete-dialog',
    templateUrl: './match-players-delete-dialog.component.html'
})
export class MatchPlayersDeleteDialogComponent {

    matchPlayers: MatchPlayers;

    constructor(
        private matchPlayersService: MatchPlayersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchPlayersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'matchPlayersListModification',
                content: 'Deleted an matchPlayers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-match-players-delete-popup',
    template: ''
})
export class MatchPlayersDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchPlayersPopupService: MatchPlayersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.matchPlayersPopupService
                .open(MatchPlayersDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
