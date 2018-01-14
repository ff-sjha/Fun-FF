import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MatchUmpire } from './match-umpire.model';
import { MatchUmpirePopupService } from './match-umpire-popup.service';
import { MatchUmpireService } from './match-umpire.service';

@Component({
    selector: 'fafi-match-umpire-delete-dialog',
    templateUrl: './match-umpire-delete-dialog.component.html'
})
export class MatchUmpireDeleteDialogComponent {

    matchUmpire: MatchUmpire;

    constructor(
        private matchUmpireService: MatchUmpireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchUmpireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'matchUmpireListModification',
                content: 'Deleted an matchUmpire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-match-umpire-delete-popup',
    template: ''
})
export class MatchUmpireDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchUmpirePopupService: MatchUmpirePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.matchUmpirePopupService
                .open(MatchUmpireDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
