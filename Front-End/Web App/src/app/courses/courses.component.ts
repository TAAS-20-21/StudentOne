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
	isProfessor:Object;
	courseList:Course[];
	columndefs : any[] = ['name','cfu', 'lesson_hours', 'actions'];
	courses:Course[];
	@ViewChild('table', { static: false }) table: any;
	
	constructor(private calendarService: CalendarService, private courseService: CourseService, private token: TokenStorageService) {}
	
	async ngOnInit(){
		this.loggedUser = this.token.getUser();
		let verifyToken = this.token.getToken();
		if(verifyToken == null){
			this.isLogin = false;
		} else {
			this.isLogin = true;
		}
		
		let _dataToUpload = {
			id:this.loggedUser.id
		}
		this.isProfessor = await this.calendarService.getIsProfessor(_dataToUpload).toPromise();
		
		this.courseService.getAll()
		.subscribe(
			response => {
			//Metodo per aggiungere in fullcalendar tutti gli eventi trovati.
			this.initializeCourses(response);
			},
			error => {
				console.log(error);
			});
			
			
			
		_dataToUpload = {
			id:this.loggedUser.id
		}
		if(this.isProfessor){
			console.log("Professore", this.isProfessor);
			this.courseService.getCoursesByIdTeacher(_dataToUpload)
			.subscribe(
				response => {
					this.setCourses(response);
				},
				error => {
					console.log(error)
			});
		}else{
			console.log("Studente", this.isProfessor);
			this.courseService.getCoursesByIdStudent(_dataToUpload)
			.subscribe(
				response => {
					this.setCourses(response);
				},
				error => {
					console.log(error)
			});
		}
	}
	
	initializeCourses(response){
		this.courseList = response;
		console.log(this.courseList);
	}
	
	setIsProfessor(response){
		this.isProfessor = response;
	}

	save(id) {
		if(!this.isAssignedOrLiked(id)){
			const _dataToUpload = {
				courseId:id,
				personId:this.loggedUser.id
			}
			console.log("Insegno CORSO CON ID: ", _dataToUpload);
			if(this.isProfessor){
				this.courseService.addAssignedCourse(_dataToUpload)
				.subscribe(
					response => {
						console.log("RISPOSTA: ");
					},
					error => {
						console.log(error)
				});
			}else{
				this.courseService.addLikedCourse(_dataToUpload)
				.subscribe(
					response => {
						console.log("RISPOSTA: ");
					},
					error => {
						console.log(error)
				});
			}
			this.courses.push(id);
			console.log(this.courses);
		}
    }
	
	delete(id) {
		if(this.isAssignedOrLiked(id)){
			const _dataToUpload = {
				courseId:id,
				personId:this.loggedUser.id
			}
			console.log("Cancello CORSO CON ID: ", _dataToUpload);
			if(this.isProfessor){
				this.courseService.deleteAssignedCourse(_dataToUpload)
				.subscribe(
					response => {
						console.log("RISPOSTA: ");
					},
					error => {
						console.log(error)
				});
			}else{
				this.courseService.deleteLikedCourse(_dataToUpload)
				.subscribe(
					response => {
						console.log("RISPOSTA: ");
					},
					error => {
						console.log(error)
				});
			}
			this.courses.splice(this.courses.indexOf(id),1);
			//myFish.splice(3, 1)
			console.log(this.courses);
		}else{
			console.log("FALSO");
		}
	}
	
	
	//PER DOCENTE E STUDENTE
	isAssignedOrLiked(id){
		return this.courses.indexOf(id) > -1;
	}
	
	setCourses(response){
		this.courses = response;
	}
	

}