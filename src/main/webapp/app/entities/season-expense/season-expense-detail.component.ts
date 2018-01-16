import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SeasonExpense } from './season-expense.model';
import { SeasonExpenseService } from './season-expense.service';

@Component({
    selector: 'fafi-season-expense-detail',
    templateUrl: './season-expense-detail.component.html'
})
export class SeasonExpenseDetailComponent implements OnInit, OnDestroy {

    seasonExpense: SeasonExpense;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seasonExpenseService: SeasonExpenseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeasonExpenses();
    }

    load(id) {
        this.seasonExpenseService.find(id).subscribe((seasonExpense) => {
            this.seasonExpense = seasonExpense;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeasonExpenses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seasonExpenseListModification',
            (response) => this.load(this.seasonExpense.id)
        );
    }
}
