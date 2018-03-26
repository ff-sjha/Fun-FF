import {Component, OnInit} from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { SportInfo, SportInfoService } from '../../entities/sport-info';

@Component({
  selector: 'fafi-carrom',
  templateUrl: './carrom.component.html',
  styleUrls: [
    'carrom.scss'
  ]
})
export class CarromComponent implements OnInit {

    sportInfo: SportInfo;
    constructor( private sportInfoService: SportInfoService,
          private jhiAlertService: JhiAlertService
        ) {
    }

    ngOnInit() {
      this.sportInfoService.findByGame('CARROM').subscribe((sportInfo) => {
          this.sportInfo =  sportInfo;
      });
    }

}
