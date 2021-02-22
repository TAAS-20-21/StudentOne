import { MessageInfo } from "./MessageInfo";

export interface Chat{
  id:number,
  dataCreazione:Date,
  nome:string,
  partecipanti:[],
  messages:MessageInfo[]
}
