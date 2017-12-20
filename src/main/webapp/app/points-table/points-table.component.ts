import { Component, OnInit } from '@angular/core';
import { Franchise } from '../entities/franchise';

@Component({
  selector: 'fafi-points-table',
  templateUrl: './points-table.component.html',
  styleUrls: [
    'points-table.scss'
  ]
})
export class PointsTableComponent implements OnInit {

    franchises: Franchise[];

  constructor() {
  }

  ngOnInit() {
  }

}
