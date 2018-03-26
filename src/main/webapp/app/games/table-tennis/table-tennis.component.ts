import {Component, OnInit} from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { SportInfo, SportInfoService } from '../../entities/sport-info';

@Component({
  selector: 'fafi-table-tennis',
  templateUrl: './table-tennis.component.html',
  styleUrls: [
    'table-tennis.scss'
  ]
})
export class TableTennisComponent implements OnInit {

    sportInfo: SportInfo;
    constructor( private sportInfoService: SportInfoService,
          private jhiAlertService: JhiAlertService
        ) {
    }

    ngOnInit() {
      this.sportInfoService.findByGame('TABLE_TENNIS').subscribe((sportInfo) => {
          this.sportInfo =  sportInfo;
      });
    }

}
