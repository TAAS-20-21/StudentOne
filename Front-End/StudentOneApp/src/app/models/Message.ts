export interface Message {
    idChat:string
    message: string,
    mittente:{
        id:string
        nome:string,
        cognome:string,
        mail:string,
        color
    },
    emailFromUser: string,
    date;
}
