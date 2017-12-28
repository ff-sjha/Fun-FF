import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SeasonsFranchisePlayer } from './seasons-franchise-player.model';
import { SeasonsFranchisePlayerPopupService } from './seasons-franchise-player-popup.service';
import { SeasonsFranchisePlayerService } from './seasons-franchise-player.service';

@Component({
    selector: 'fafi-seasons-franchise-player-delete-dialog',
    templateUrl: './seasons-franchise-player-delete-dialog.component.html'
})
export class SeasonsFranchisePlayerDeleteDialogComponent {

    seasonsFranchisePlayer: SeasonsFranchisePlayer;

    constructor(
        private seasonsFranchisePlayerService: SeasonsFranchisePlayerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seasonsFranchisePlayerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seasonsFranchisePlayerListModification',
                content: 'Deleted an seasonsFranchisePlayer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-seasons-franchise-player-delete-popup',
    template: ''
})
export class SeasonsFranchisePlayerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonsFranchisePlayerPopupService: SeasonsFranchisePlayerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.seasonsFranchisePlayerPopupService
                .open(SeasonsFranchisePlayerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
