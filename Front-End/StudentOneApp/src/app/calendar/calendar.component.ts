import { Component, OnInit, ViewChild, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import {
  startOfDay,
  endOfDay,
  subDays,
  addDays,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
} from 'date-fns';
import { Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
//import { RapportinoMensileService } from '../../../services/rapportino-mensile.service';
import { CalendarEventActionsComponent } from 'angular-calendar/modules/common/calendar-event-actions.component';
//import { element } from '@angular/core/src/render3';
//import { TokenStorageService } from '../../../auth/token-storage.service';
//import { UserService } from '../../../services/user.service';
//import { MatDialog, MatSnackBar } from '@angular/material';
//import { OreLavorativeDialogComponent } from './ore-lavorative-dialog/ore-lavorative-dialog.component';


const colors: any = {
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
  green: {
    primary: '#00FF00' //ore ordinarie
  },
  darkorange: {
    primary: '#FF8C00' //straordinario
  },
  red: {
    primary: '#ad2121', //straordinario festivo
    secondary: '#FAE3E3',
  },
  midnight: {
    primary: '#33FFE8' //straordinarie notturne
  },
  darkviolet: {
    primary: '#9400D3' //reperibilità attiva
  },
  purple: {
    primary: '#FF1493' //reperibilità passiva
  },
  darkblue: {
    primary: '#00008B' //reperibilità festiva
  },
  mediumpurple: {
    primary: '#FFB6C1' // reperibilità notturna
  }

};

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  refresh: Subject<any> = new Subject();
  events: CalendarEvent[] = new Array<CalendarEvent>();
  activeDayIsOpen: boolean = false;
  isAdmin: boolean = false;
  isHr: boolean = false;
  @ViewChild('modalContent') modalContent: TemplateRef<any>;

  @Output("getCommesse") getCommesse: EventEmitter<any> = new EventEmitter();

  view: CalendarView = CalendarView.Month;

  CalendarView = CalendarView;

  viewDate: Date = new Date();

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  @Input() lavoro: any;
  dataRapportino: Date;
  dialogRef: any;




  constructor(private modal: NgbModal){}
    //private rapportinoMensileService: RapportinoMensileService,
    //private token: TokenStorageService,
    //private userService: UserService,
    //private dialog: MatDialog,
    //private snackBar: MatSnackBar) 

    ngOnInit() {
      /*if(this.token.getAuthorities().includes('ROLE_HR') == true){
        this.isHr = true;
      } else if(this.token.getAuthorities().includes('ROLE_ADMIN') == true){
        this.isAdmin = true;
      }
      this.updateEvents();*/
    }

	/*
    dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
        this.activeDayIsOpen = false;
      } else {
        //this.lavoro.rapportino.conferma
        let ore = new Array();
        this.activeDayIsOpen = true;
        if(this.lavoro.rapportino.conferma == false || this.isAdmin == true || this.isHr == true){

          this.dialogRef = this.dialog.open(OreLavorativeDialogComponent, {
            data: {
              idCommessa: this.lavoro.commessa.id,
              idRapportino: this.lavoro.rapportino.id,
              giorno: date
              },
              width: "500px"
          });
          this.dialogRef.afterClosed().subscribe(result =>{
            if(result && result.salva == true){
              if(result && result.data.length > 0){
                this.aggiornaEventiGiorno(result.data, date);
                this.openSnackBar("Il rapportino del giorno " + date.getDate() + "/" + (date.getMonth()+1) + " è stato compilato correttamente!");
              } else {
                this.aggiungiEventoNonCompilato(date);
              }
              this.puliziaEventi();
              this.getCommesse.emit();
            }

          })
        }
      }
      this.viewDate = date;
      }
    }*/


    setView(view: CalendarView) {
    this.view = view;
    }

    closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
    }
/*
  updateEvents(){
    this.dataRapportino = new Date(this.lavoro.rapportino.data);
      this.viewDate = this.dataRapportino;
      this.initializeEvents();
      if(this.lavoro.orelavorative!= null){
        this.pushEvents(this.lavoro.orelavorative);
        //this.modify[i].push(false);
        this.puliziaEventi();
      }
  }

  private puliziaEventi(){
    let array = new Array();
    let temp = this.events;
    let i, k;
    let check : boolean = false
    for(i = 0; i < temp.length; i++){
      for(k = i; k < temp.length; k++){
        if(temp[i].title == "Giorno non compilato" && temp[k].title != "Giorno non compilato"){
          if(temp[i].start.getTime() ==  temp[k].start.getTime()){
            this.events.splice(i, 1);
            i--;
            break;
          }
        }
      }
    }
    this.events = temp;
  }

  private pushEvents(oreLavorative){
    for(let ore of oreLavorative){
      let date;
      date = new Date(this.dataRapportino.setDate(ore.giorno));
      if(ore.tipologia.nome == "ORDINARIO"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore ordinarie: ' + ore.ore,
            color: colors.green
          }
        )
      } else if(ore.tipologia.nome == "STRAORDINARIO"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore straordinarie: ' + ore.ore,
            color: colors.darkorange
          }
        )
      } else if(ore.tipologia.nome == "STRAORDINARIO_FESTIVO"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore straordinarie festive: ' + ore.ore,
            color: colors.red
          }
        )
      } else if(ore.tipologia.nome == "STRAORDINARIO_NOTTURNO"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore straordinarie notturne: ' + ore.ore,
            color: colors.midnight
          }
        )
      } else if(ore.tipologia.nome == "REPERIBILITA_ATTIVA"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore reperibilità attiva: ' + ore.ore,
            color: colors.darkviolet
          }
        )
      } else if(ore.tipologia.nome == "REPERIBILITA_PASSIVA"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore reperibilità passiva: ' + ore.ore,
            color: colors.purple
          }
        )
      } else if(ore.tipologia.nome == "REPERIBILITA_FESTIVA"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore reperibilità festiva: ' + ore.ore,
            color: colors.darkblue
          }
        )
      } else if(ore.tipologia.nome == "REPERIBILITA_NOTTURNA"){
        this.events.push(
          {
            start: startOfDay(new Date (date)),
            title: 'Ore reperibilità notturna: ' + ore.ore,
            color: colors.mediumpurple
          }
        )
      }

    //this.modify[i].push(false);
    }
  }

  private initializeEvents(){
    let dataOdierna = new Date();
    let dateInizio = new Date(this.dataRapportino.setDate(1));
    let dateFine = new Date();
    if(this.dataRapportino.getMonth() != dataOdierna.getMonth()){
      dateFine = new Date(this.dataRapportino.getFullYear(), this.dataRapportino.getMonth() + 1, 0);
    }
    this.events = new Array<CalendarEvent>();
    let k;
    for(k = dateInizio.getDate(); k <= dateFine.getDate(); k++){
      let temp = new Date(dateInizio);
      this.events.push({
        start: startOfDay(new Date (temp.setDate(k))),
        title: 'Giorno non compilato',
        color: colors.yellow
      })
    }
  }

  aggiornaEventiGiorno(result, giorno){

    this.cancellaEventiGiorno(giorno);

    for(let res of result){
      if(res.tipologia.nome == "ORDINARIO"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore ordinarie: ' + res.ore,
            color: colors.green
          }
        ]
      } else if(res.tipologia.nome == "STRAORDINARIO"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore straordinarie: ' + res.ore,
            color: colors.darkorange
          }
        ]
      } else if(res.tipologia.nome == "STRAORDINARIO_FESTIVO"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore straordinarie: ' + res.ore,
            color: colors.red
          }
        ]
      } else if(res.tipologia.nome == "STRAORDINARIO_NOTTURNO"){
        this.events.push(
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore straordinarie notturne: ' + res.ore,
            color: colors.midnight
          }
        )
      } else if(res.tipologia.nome == "REPERIBILITA_ATTIVA"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore reperibilità attiva: ' + res.ore,
            color: colors.darkviolet
          }
        ]
      } else if(res.tipologia.nome == "REPERIBILITA_PASSIVA"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore reperibilità passiva: ' + res.ore,
            color: colors.purple
          }
        ]
      } else if(res.tipologia.nome == "REPERIBILITA_FESTIVA"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore reperibilità festiva: ' + res.ore,
            color: colors.darkblue
          }
        ]
      } else if(res.tipologia.nome == "REPERIBILITA_NOTTURNA"){
        this.events = [
          ...this.events,
          {
            start: startOfDay(new Date (giorno)),
            title: 'Ore reperibilità notturna: ' + res.ore,
            color: colors.mediumpurple
          }
        ]
      }
    }
  }

  aggiungiEventoNonCompilato(giorno){
    this.cancellaEventiGiorno(giorno);
    this.events.push(
      {
        start: startOfDay(new Date (giorno)),
        title: "Giorno non compilato",
        color: colors.yellow
      }
    )
  }

  cancellaEventiGiorno(giorno){
    let i = 0;
    for(i = 0; i < this.events.length; i++){
      if (this.events[i].start.getTime() == giorno.getTime()){
        this.events.splice(i, 1);
        i--;
      }
    }
  }

  openSnackBar(message:string) {
    this.snackBar.open(message, "OK",{
       duration: 3000,
    });
  }*/


}
