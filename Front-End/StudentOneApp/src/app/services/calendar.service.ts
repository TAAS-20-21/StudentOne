import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../models/calendar.model';


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
