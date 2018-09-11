import {Component, OnInit} from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { SportInfo, SportInfoService } from '../../entities/sport-info';

@Component({
  selector: 'fafi-volleyball',
  templateUrl: './volleyball.component.html',
  styleUrls: [
    'volleyball.scss'
  ]
})
export class VolleyballComponent implements OnInit {

    sportInfo: SportInfo;
    constructor( private sportInfoService: SportInfoService,
          private jhiAlertService: JhiAlertService
        ) {
    }

    ngOnInit() {
      this.sportInfoService.findByGame('VOLLEY_BALL').subscribe((sportInfo) => {
          this.sportInfo =  sportInfo;
      });
    }

}
