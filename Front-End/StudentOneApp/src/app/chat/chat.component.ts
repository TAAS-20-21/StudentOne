import { Component, OnInit } from "@angular/core";
import { User } from "../models/User";
import { TokenStorageService } from "../services/token-storage.service";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { SocketCostants } from "../common/app.constants";


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  loggedUser: User;
  isLogin:boolean;
  stompClient;
  isLoaded:boolean;
  isOnline;

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
    let ws = new SockJS(SocketCostants.SOCKET_URL);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function (frame) {
      that.isLoaded = true;
      that.isOnline = 1;
      that.openSocket();
    }, function(message){
      that.isOnline = 2;
    });
  }

  openSocket() {
    this.stompClient.subscribe(
      "/socket-publisher/" + this.loggedUser.email,
      (message) => {
        //this.handleResult(message);
      }
    );
  }


}
