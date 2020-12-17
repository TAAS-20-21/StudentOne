import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';



const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json", "Accept": "application/json" })
};

@Injectable({
  providedIn: 'root'
})
export class HomePageService {

  constructor(private http: HttpClient) { }

  getAllEvents(): Observable<any> {
    return this.http.get("api/calendar/getAllEvents", httpOptions)
  }
  errorHandler(error: Response){
    return Observable.throw(error || "SERVER ERROR");
  }

}
