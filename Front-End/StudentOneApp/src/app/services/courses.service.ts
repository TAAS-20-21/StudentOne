import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConstants } from '../common/app.constants';
import { Course } from "../models/course-main.model";

const baseUrl = 'http://localhost:8080/StudentOne/courseservice/api/course';

@Injectable({
	providedIn: 'root'
})
export class CourseService {

	constructor(private http: HttpClient) { }

	getAll(): Observable<Course[]> {
		const url = baseUrl;
		return this.http.get<Course[]>(url);
	}
	
	getCourseById(data: any){
		const url = "http://localhost:8080/StudentOne/courseservice/api/course/courseById";
		return this.http.post(url, data);
	}
	
}