import { Component, OnInit } from "@angular/core";
import { User } from "../models/User";
import { TokenStorageService } from "../services/token-storage.service";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { SocketCostants } from "../common/app.constants";
import { ChatService } from "./chat.service";
import { ChatInfo } from "../models/ChatInfo";
import { Chat } from "../models/Chat";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NewMessage } from "../models/NewMessage";
import { MessageInfo } from "../models/MessageInfo";



@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  textToSend:String;
  chats:ChatInfo[];
  loggedUser: User;
  isLogin:boolean;
  stompClient;
  isLoaded:boolean;
  isOnline;
  contentChat:Chat;
  listContentChatLoaded: Chat[] = new Array<Chat>();
  constructor(private token: TokenStorageService, private chatService : ChatService){}


  ngOnInit(): void{
    this.loggedUser = this.token.getUser();
    let verifyToken = this.token.getToken();
    if(verifyToken == null){
      this.isLogin = false;
    } else {
      this.isLogin = true;
    }
    this.initializeWebSocketConnection();
    this.chatService.geChats().subscribe( (response) =>{
      this.chats = response;
      this.sortChats();
      this.setContentChat(this.chats[0]);
      console.log(this.chats)
      },
    (error) => console.log(error)
    );
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
        this.handleResult(message);
      }
    );
  }

  getDate(timestamp){
    if(timestamp != null){
      let date:Date = new Date(timestamp)
      return date.getDate() + "/" + date.getMonth();
    } else {
      return null;
    }
  }

  getFullName(sender){
    return sender.name + " "+ sender.surname;
  }

  sortChats(){
    var dateA;
    this.chats.sort(function (a, b) {
      if(a.ultimoMessaggio !=null){
        dateA = new Date(a.ultimoMessaggio.date)
      } else {
        dateA=null;
      }
      var dateB;
      if(b.ultimoMessaggio !=null){
        dateB = new Date(b.ultimoMessaggio.date)
      }else{
        dateB = null;
      }
      if (!a.ultimoMessaggio && b.ultimoMessaggio) return 1;
      else if (a.ultimoMessaggio && !b.ultimoMessaggio) return -1;
      else if (dateA === dateB) return 0;
      else return (dateA > dateB) ? 1 : (dateB > dateA ? -1 : 0);
  });
  }

  setContentChat(chat){
    let c = this.checkChatsLoaded(chat.id);
    if(c!= null){
      this.contentChat = c;
    } else {
      this.chatService.getChatById(chat.id).subscribe( (response) =>{
        console.log(response);
        this.contentChat = response;
        this.listContentChatLoaded.push(this.contentChat);
      },
      (error) => console.log(error)
      );
    }
  }

  checkChatsLoaded(idChat:number){
    for (let chat of this.listContentChatLoaded) {
      if(chat.id == idChat){
        return chat;
      }
    }
    return null;
  }

  getHour(timestamp:string){
    const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"];
    if(timestamp != null){
      let date:Date = new Date(timestamp)
      return date.getHours() + ":" + date.getMinutes() +" | "+ date.getDate() + " " + monthNames[date.getMonth()];
    } else {
      return null;
    }
  }

  sendMessageUsingSocket() {
    if(this.textToSend != null){
      let newMessage:NewMessage = {
        emailFromUser: this.loggedUser.email,
        idChat: this.contentChat.id.toString(),
        text: this.textToSend
      };
      this.stompClient.send(
      "/socket-subscriber/send/message",
      {},
      JSON.stringify(newMessage));
      let message:MessageInfo = {
        text:this.textToSend.toString(),
        date: new Date(),
        sender : this.loggedUser,
        idChat: this.contentChat.id,
        id:null
      };
      this.contentChat.messages.push(message);
    }
    this.textToSend = null;
  }

  handleResult(message) {
    let messageResult:MessageInfo = JSON.parse(message.body);
    if(this.contentChat.id == messageResult.idChat){
      this.contentChat.messages.push(messageResult);
    }
    for(let chat of this.listContentChatLoaded){
      if(chat.id == messageResult.idChat && !(this.contentChat.id == messageResult.idChat)){
        chat.messages.push(messageResult);
      }
    }
  }

}
