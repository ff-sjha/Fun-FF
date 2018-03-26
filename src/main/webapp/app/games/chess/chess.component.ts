import {Component, OnInit} from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { SportInfo, SportInfoService } from '../../entities/sport-info';

@Component({
  selector: 'fafi-chess',
  templateUrl: './chess.component.html',
  styleUrls: [
    'chess.scss'
  ]
})
export class ChessComponent implements OnInit {

    sportInfo: SportInfo;
    constructor( private sportInfoService: SportInfoService,
          private jhiAlertService: JhiAlertService
        ) {
    }

    ngOnInit() {
      this.sportInfoService.findByGame('CHESS').subscribe((sportInfo) => {
          this.sportInfo =  sportInfo;
      });
    }

}
