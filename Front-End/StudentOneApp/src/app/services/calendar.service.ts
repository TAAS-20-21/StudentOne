import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../models/calendar.model';

const baseUrl = 'http://localhost:8082/StudentOne/api/calendar';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Event[]> {
	const url = baseUrl + '/getAllEvents'
    return this.http.get<Event[]>(url);
  }
/*
  get(id: any): Observable<Event> {
    return this.http.get(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(baseUrl);
  }

  findByTitle(title: any): Observable<Event[]> {
    return this.http.get<Event[]>(`${baseUrl}?title=${title}`);
  }
*/
}