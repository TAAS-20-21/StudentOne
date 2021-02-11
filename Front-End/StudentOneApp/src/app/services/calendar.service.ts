import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../models/calendar.model';
import { AppConstants } from '../common/app.constants';


const baseUrl = 'http://localhost:8080/StudentOne/calendarservice/api/calendar';

@Injectable({
	providedIn: 'root'
})
export class CalendarService {

	constructor(private http: HttpClient) { }

	getAll(): Observable<Event[]> {
		const url = baseUrl + '/getAllEvents'
		return this.http.get<Event[]>(url);
	}

	create(data: any): Observable<any> {
		const url = baseUrl + '/addEvent';
		return this.http.post(url, data);
	}

	delete(data: any): Observable<any> {
		const url = baseUrl + '/deleteEvent';
		return this.http.post(url, data);
	}

	changeTime(data:any): Observable<any> {
		const url = baseUrl + '/modify/time';
		return this.http.post(url,data);
	}

	findByAngularId(data: any): Observable<any>{
		const url = baseUrl + '/findByAngularId';
		return this.http.post(url, data);
	}
	
	getIsProfessor(data: any){
		const url = "http://localhost:8080/StudentOne/calendarservice/api/user/isProfessor";
		return this.http.post(url, data);
	}
	
	getCoursesById(data: any){
		const url = "http://localhost:8080/StudentOne/calendarservice/api/student/courses";
		return this.http.post(url, data);
	}
	
	getWorkingGroupsById(data: any){
		const url = "http://localhost:8080/StudentOne/calendarservice/api/student/working_groups";
		return this.http.post(url, data);
	}
	
	getCourse(data: any){
		const url = "http://localhost:8080/StudentOne/calendarservice/api/course/courseById";
		return this.http.post(url, data);
	}
	
	getWorkingGroup(data: any){
		const url = "http://localhost:8080/StudentOne/calendarservice/api/working_group/workingGroupById";
		return this.http.post(url, data);
	}
	/*
	update(id: any, data: any): Observable<any> {
		return this.http.put(`${baseUrl}/${id}`, data);
	}



  deleteAll(): Observable<any> {
    return this.http.delete(baseUrl);
  }

  findByTitle(title: any): Observable<Event[]> {
    return this.http.get<Event[]>(`${baseUrl}?title=${title}`);
  }
*/
}
