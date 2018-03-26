import {Component, OnInit} from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { SportInfo, SportInfoService } from '../../entities/sport-info';

@Component({
  selector: 'fafi-box-cricket',
  templateUrl: './box-cricket.component.html',
  styleUrls: [
    'box-cricket.scss'
  ]
})
export class BoxCricketComponent implements OnInit {

    sportInfo: SportInfo;
    constructor( private sportInfoService: SportInfoService,
          private jhiAlertService: JhiAlertService
        ) {
    }

    ngOnInit() {
      this.sportInfoService.findByGame('BOX_CRICKET').subscribe((sportInfo) => {
          this.sportInfo =  sportInfo;
      });
    }

}
