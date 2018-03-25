import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { SportInfo, SportInfoService } from '../../entities/sport-info';

@Component({
  selector: 'fafi-badminton',
  templateUrl: './badminton.component.html',
  styleUrls: [
    'badminton.scss'
  ]
})
export class BadmintonComponent implements OnInit {

    sportInfo: SportInfo;
    constructor( private sportInfoService: SportInfoService,
          private jhiAlertService: JhiAlertService
        ) {
    }

  ngOnInit() {
      this.sportInfoService.findByGame('BADMINTON').subscribe((sportInfo) => {
          this.sportInfo =  sportInfo;
      });
  }
}
