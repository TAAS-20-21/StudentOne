import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';
import { AppConstants } from '../common/app.constants'
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';

import { Course } from "../models/course-main.model";
import { CalendarService } from 'src/app/services/calendar.service';
import { CourseService } from 'src/app/services/courses.service';
import { User } from "../models/User";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit{
	
	loggedUser: User;
	isLogin:boolean;
	isProfessor:boolean;
	courseList:Course[];
	columndefs : any[] = ['name','cfu', 'lesson_hours', 'actions'];
	@ViewChild('table', { static: false }) table: any;
	
	constructor(private calendarService: CalendarService, private courseService: CourseService, private token: TokenStorageService) {}
	
	ngOnInit(): void {
		this.loggedUser = this.token.getUser();
		let verifyToken = this.token.getToken();
		if(verifyToken == null){
			this.isLogin = false;
		} else {
			this.isLogin = true;
		}
		
		const _dataToUpload = {
			id:this.loggedUser.id
		}
		this.calendarService.getIsProfessor(_dataToUpload)
		.subscribe(
			response => {
				this.setIsProfessor(response);
			},
			error => {
				console.log(error)
		});
		
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
	
	setIsProfessor(response){
		this.isProfessor = response;
	}

	save(id) {
		const _dataToUpload = {
			id:id
		}
		console.log("CLICKATO CORSO CON ID: ", _dataToUpload);
		this.courseService.getCourseById(_dataToUpload)
		.subscribe(
			response => {
				console.log("RISPOSTA: ", response);
			},
			error => {
				console.log(error)
		});
        //this.dialogRef.close([this.form.value, this.isRecurrent]);
    }
}