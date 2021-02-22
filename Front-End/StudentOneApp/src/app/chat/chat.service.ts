import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SocketCostants } from "../common/app.constants";
import { Chat } from "../models/Chat";
import { ChatInfo } from "../models/ChatInfo";
import { User } from "../models/User";


const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json", "Accept": "application/json" })
};

@Injectable({
	providedIn: 'root'
})
export class ChatService {
  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
		return this.http.get<User[]>(SocketCostants.GETTUSERS, httpOptions);
  }
  geChats():Observable<ChatInfo[]>{
    return this.http.get<ChatInfo[]>(SocketCostants.GETCHATS, httpOptions);
  }

  getChatById(idChat):Observable<Chat>{
    return this.http.post<Chat>(SocketCostants.GETCHATBYID, idChat, httpOptions);
  }
}
