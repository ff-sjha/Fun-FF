import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SeasonExpense } from './season-expense.model';
import { SeasonExpenseService } from './season-expense.service';

@Injectable()
export class SeasonExpensePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seasonExpenseService: SeasonExpenseService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.seasonExpenseService.find(id).subscribe((seasonExpense) => {
                    if (seasonExpense.incurredDate) {
                        seasonExpense.incurredDate = {
                            year: seasonExpense.incurredDate.getFullYear(),
                            month: seasonExpense.incurredDate.getMonth() + 1,
                            day: seasonExpense.incurredDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.seasonExpenseModalRef(component, seasonExpense);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.seasonExpenseModalRef(component, new SeasonExpense());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    seasonExpenseModalRef(component: Component, seasonExpense: SeasonExpense): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seasonExpense = seasonExpense;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
