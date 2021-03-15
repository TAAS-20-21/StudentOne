import { Message } from "./Message";

export interface ChatInfo {
  id:number,
  datacreazione: Date,
  nome:string;
  ultimoMessaggio:Message;
}
