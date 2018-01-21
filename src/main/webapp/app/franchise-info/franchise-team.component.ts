import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../shared';

import {SeasonsFranchisePlayer, SeasonsFranchisePlayerService} from '../entities/seasons-franchise-player';

@Component({
  selector: 'fafi-franchise-team',
  templateUrl: './franchise-team.component.html',
  styleUrls: [
    'franchise-info.scss'
  ]
})

export class FranchiseTeamComponent  implements OnInit, OnDestroy  {
  @Input() id: string;
  seasonsFranchisePlayers: SeasonsFranchisePlayer[];

  constructor(
          private seasonsFranchisePlayerService: SeasonsFranchisePlayerService,
          private jhiAlertService: JhiAlertService
    ) {
    }
   loadAll() {
       this.seasonsFranchisePlayerService.queryByFranchiseId(this.id, {
           page: 0,
           size: 1000
       }).subscribe(
           (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
           (res: ResponseWrapper) => this.onError(res.json)
       );
   }

   ngOnInit() {
      this.loadAll();
   }

   ngOnDestroy() {
   }

   private onSuccess(data, headers) {
       // this.page = pagingParams.page;
       this.seasonsFranchisePlayers = data;
   }
   private onError(error) {
       this.jhiAlertService.error(error.message, null, null);
   }
}
