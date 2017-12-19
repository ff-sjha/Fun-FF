import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'fafi-points-table',
  templateUrl: './points-table.component.html',
  styleUrls: [
    'points-table.scss'
  ]
})
export class PointsTableComponent implements OnInit {

  message: string;

  constructor() {
    this.message = '';
  }

  ngOnInit() {
  }

}
