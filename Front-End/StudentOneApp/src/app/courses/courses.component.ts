import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';
import { AppConstants } from '../common/app.constants'
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';

import { Course } from "../models/course-main.model";
import { CalendarService } from 'src/app/services/calendar.service';
import { CourseService } from 'src/app/services/courses.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit{
	
	courseList:Course[];
	
	constructor(private calendarService: CalendarService, private courseService: CourseService, private token: TokenStorageService) {}
	
	ngOnInit(): void {
		this.courseService.getAll()
		.subscribe(
			response => {
			//Metodo per aggiungere in fullcalendar tutti gli eventi trovati.
			this.initializeCourses(response);
			},
			error => {
				console.log(error);
			});
	}
	
	initializeCourses(response){
		this.courseList = response;
		console.log(this.courseList);
	}
}