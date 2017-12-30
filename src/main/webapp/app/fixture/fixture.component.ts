import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'fafi-fixture',
  templateUrl: './fixture.component.html',
  styleUrls: [
    'fixture.scss'
  ]
})
export class FixtureComponent implements OnInit {

  message: string;

  constructor() {
    this.message = '';
  }

  ngOnInit() {
  }

}
