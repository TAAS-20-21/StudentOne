import { Component, OnInit } from '@angular/core';
import { HomePageService } from './home-page.service';

@Component({
  selector: 'app-home-page',
  template: `
    <p>
      home-page works!
    </p>
  `,
  styles: [
  ]
})
export class HomePageComponent implements OnInit {

  constructor(private homePageService: HomePageService) { }

  ngOnInit(): void {
    this.homePageService.getAllEvents().subscribe(response => {
      console.log("visualizza", response)
      }, error => {
      console.error(error)
    })
  }

}
