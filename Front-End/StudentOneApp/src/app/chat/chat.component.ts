import { Component, OnInit } from "@angular/core";
import { User } from "../models/User";
import { TokenStorageService } from "../services/token-storage.service";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  loggedUser: User;
  isLogin:boolean;
  stompClient;

  constructor(private token: TokenStorageService){}

  ngOnInit(): void{
    this.loggedUser = this.token.getUser();
    let verifyToken = this.token.getToken();
    if(verifyToken == null){
      this.isLogin = false;
    } else {
      this.isLogin = true;
    }
    this.initializeWebSocketConnection()
  }

  initializeWebSocketConnection(){
    let ws = new SockJS("/socket");
    this.stompClient = Stomp.over(ws);

  }


}
