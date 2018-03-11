import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import {SeasonExpense, SeasonExpenseService} from '../entities/season-expense';

import {Account, ITEMS_PER_PAGE, LoginModalService, Principal, ResponseWrapper} from '../shared';

@Component({
  selector: 'fafi-expense',
  templateUrl: './expense.component.html',
  styleUrls: [
    'expense.scss'
  ]
})
export class ExpenseComponent implements OnInit {

    account: Account;
    modalRef: NgbModalRef;
    totalItems: any;
    queryCount: any;
    links: any;
    error: any;
    success: any;
    routeData: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    expenses: SeasonExpense[];

  constructor(
    private principal: Principal,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private parseLinks: JhiParseLinks,
    private jhiAlertService: JhiAlertService,
    private router: Router,
    private seasonExpenseService: SeasonExpenseService,
  ) {
  }

  ngOnInit() {
      this.loadAll();
  }

  loadAll() {
      this.seasonExpenseService.query().subscribe(
              (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
              (res: ResponseWrapper) => this.onError(res.json)
      );
  }

  getTotal() {
      let total = 0;
      if (this.expenses) {
          for (let i = 0; i < this.expenses.length; i++) {
              total += this.expenses[i].amount;
          }
      }
      return total;
  }
  private onSuccess(data, headers) {
      this.expenses = data;
  }

  private onError(error) {
      this.jhiAlertService.error(error.message, null, null);
  }
}
