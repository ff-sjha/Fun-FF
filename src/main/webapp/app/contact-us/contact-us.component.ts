import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'fafi-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: [
    'contact-us.scss'
  ]
})
export class ContactUsComponent implements OnInit {

  message: string;

  constructor() {
    this.message = '';
  }

  ngOnInit() {
  }

}
