import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SeasonExpense } from './season-expense.model';
import { SeasonExpensePopupService } from './season-expense-popup.service';
import { SeasonExpenseService } from './season-expense.service';

@Component({
    selector: 'fafi-season-expense-delete-dialog',
    templateUrl: './season-expense-delete-dialog.component.html'
})
export class SeasonExpenseDeleteDialogComponent {

    seasonExpense: SeasonExpense;

    constructor(
        private seasonExpenseService: SeasonExpenseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seasonExpenseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seasonExpenseListModification',
                content: 'Deleted an seasonExpense'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-season-expense-delete-popup',
    template: ''
})
export class SeasonExpenseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonExpensePopupService: SeasonExpensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.seasonExpensePopupService
                .open(SeasonExpenseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
