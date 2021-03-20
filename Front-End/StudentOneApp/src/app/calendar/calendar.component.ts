import { Component, ViewChild  } from '@angular/core';
import { CalendarOptions, DateSelectArg, EventClickArg, EventApi, FullCalendarComponent } from '@fullcalendar/angular';
import { EventChangeArg, EventAddArg, EventRemoveArg } from '@fullcalendar/common'
//DIALOG
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { EventDialogComponent } from './event-dialog/event-dialog.component'
//DIALOG
import { CalendarService } from 'src/app/services/calendar.service';

//LOGIN
import { User } from "../models/User";
import { TokenStorageService } from "../services/token-storage.service";
import { map } from 'rxjs/operators';
import itLocale from '@fullcalendar/core/locales/it';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class OurCalendarComponent {
	
	loggedUser: User;
	isLogin:boolean;
	isProfessor:boolean;
	max = this.calendarService.getMaxAngularId().toPromise();
	  
	//DIALOG  
	constructor(private dialog: MatDialog, private calendarService: CalendarService, private token: TokenStorageService) {}
  
	//Alla creazione del calendario carico gli eventi dal DB per mostrarli.
	ngOnInit(): void {
		this.loggedUser = this.token.getUser();
		let verifyToken = this.token.getToken();
		if(verifyToken == null){
			this.isLogin = false;
		} else {
			this.isLogin = true;
		}
		
		const _dataToUpload = {
			id:this.loggedUser.id
		}
		this.calendarService.getIsProfessor(_dataToUpload)
		.subscribe(
			response => {
				this.setIsProfessor(response);
			},
			error => {
				console.log(error)
		});

		//Metodo per ottenere tutti gli eventi che coinvolgono l'utente.
		this.calendarService.getAll()
		.subscribe(
			response => {
			//Metodo per aggiungere in fullcalendar tutti gli eventi trovati.
			this.addInitialEvents(response);
			},
			error => {
				console.log(error);
			});
	}
	
	setIsProfessor(response){
		this.isProfessor = response;
	}

  
	//Metodo per gestire l'apertura della dialog per la creazione degli eventi.
	openDialog(selectInfo: DateSelectArg) {
		
		//console.log(this.loggedUser);
		//console.log(response);

		const dialogConfig = new MatDialogConfig();

		//Quando si clicka fuori non si chiude la dialog.
		dialogConfig.disableClose = true;
		//Evidenzia il primo elemento editabile della dialog.
		dialogConfig.autoFocus = true;
		dialogConfig.data = {
			isProfessor: this.isProfessor,
			loggedUser: this.loggedUser,
			calendarService: this.calendarService,
			selectInfo: selectInfo
		}
		const dialogRef = this.dialog.open(EventDialogComponent, dialogConfig);
		
		/*const calendarApi = this.fullcalendar.getApi(); 
		const viewType = calendarApi.currentData.currentViewType;
		if(viewType != 'dayGridMonth'){
			console.log(selectInfo.startStr.substr(0,10));
		}
		console.log(calendarApi);*/
		
		
		//Dopo che salvo invio uso i dati per creare l'evento.
		dialogRef.afterClosed().subscribe( (data) => {
			if(data){
				this.addEvent(selectInfo, data);
			}
		});
	}
  
	//Seleziono l'elemento con id 'fullcalendar' dall'html per poterlo utilizzare
	//nei metodi che non mettono a dispozione un'istanza di fullcalendar.
	@ViewChild('fullcalendar', { static: false }) fullcalendar: FullCalendarComponent;
	//DIALOG
	calendarVisible = true;
	calendarOptions: CalendarOptions = {
		headerToolbar: {
		  left: 'prev,next today',
		  center: 'title',
		  right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
		},
		initialView: 'dayGridMonth', // alternatively, use the `events` setting to fetch from a feed
		weekends: true,
		editable: true,
		selectable: true,
		selectMirror: true,
		dayMaxEvents: true,
		select: this.handleDateSelect.bind(this),
		eventClick: this.handleEventClick.bind(this),
	
		eventsSet: this.handleEvents.bind(this),
		//you can update a remote database when these fire:
		eventAdd: this.handleEventAdd.bind(this),
		eventChange: this.handleEventChange.bind(this),
		eventRemove: this.handleEventRemove.bind(this),
		
		
		
		buttonIcons: false,
		weekNumbers: true,
		navLinks: true,
		locale: itLocale
	};
	currentEvents: EventApi[] = [];
  
  
	//Metodo per la gestione della sincronizzazione tra front-end e DB in caso di creazione di un evento.
	handleEventAdd(addInfo: EventAddArg){
		const _dataToUpload = {
			id: addInfo.event.id
		}
		//Controllo che non esista un altro evento con angularId uguale.
		this.calendarService.findByAngularId(_dataToUpload)
		.subscribe(
			response => {
				this.uploadEvent(addInfo, response);
			},
			error => {
				console.log(error)
			}); 
	}	
	
	
	uploadEvent(addInfo, queryRes){
		//UPLOAD TO DB
		//Se non esiste un altro evento con angularId allora creo un evento.
		if(queryRes ==null){
			//METTERE CONTROLLO SU PROFESSORE O ALUNNO
			let wgName = addInfo.event.title.split("@")[1];
			let wgId = addInfo.event.title.split("@")[2];
			let _dataToUpload: any;
			
			let name = addInfo.event.title.split("@")[0] + "@" + addInfo.event.title.split("@")[1];
			//PER EVENTI SINGOLI
			if(addInfo.event.startStr != ""){
				if(this.isProfessor == false){
					_dataToUpload = {
						summary : name,
						startDateTime : addInfo.event.start,
						endDateTime : addInfo.event.end,
						//DA MODIFICARE QUANDO SI AVRANNO PIU' UTENTI
						workingGroup: {
							id: Number(wgId)
						},
						course: null,
						type:null,
						angularId: addInfo.event.id
					}
				}else{
					_dataToUpload = {
						summary : name,
						startDateTime : addInfo.event.start,
						endDateTime : addInfo.event.end,
						//DA MODIFICARE QUANDO SI AVRANNO PIU' UTENTI
						workingGroup: null,
						course: {
							id: Number(wgId)
						},
						type:null,
						angularId: addInfo.event.id
					}
				}
			}
			else {
				//PER EVENTI RICORRENTI
				let _daysOfWeek:string = ""; 

				let daysTypeData = addInfo.event._def.recurringDef.typeData;
				for (var i = 0; i < daysTypeData.daysOfWeek.length; i++){
					_daysOfWeek += daysTypeData.daysOfWeek[i];
				}
				
				//step necessari per ricavare l'orario d'inizio dell'evento ricorrente
				let startData = new Date(Date.UTC(daysTypeData.startRecur.getFullYear(), daysTypeData.startRecur.getMonth(), daysTypeData.startRecur.getDate()));
				let dataInMilliseconds: number;
				let dataInMillisecondsTmp: number;
				let startTimeInMilliseconds: number;
				let endTimeInMilliseconds: number;
				
				dataInMilliseconds = startData.getTime(); //otteniamo i ms della data d'inizio della ricorrenza
				startTimeInMilliseconds = daysTypeData.startTime.milliseconds;	//otteniamo i ms dell'ora d'inizio dell'evento
				dataInMillisecondsTmp = dataInMilliseconds + startTimeInMilliseconds - 3600000; //il decremento è stato fatto per far fronte al GMT+1:00 impostato in automatico
				let startTime = new Date(dataInMillisecondsTmp);
				
				//step necessari per ricavare l'orario di fine dell'evento ricorrente
				endTimeInMilliseconds = daysTypeData.endTime.milliseconds;	//otteniamo i ms dell'ora di fine dell'evento
				dataInMillisecondsTmp = dataInMilliseconds + endTimeInMilliseconds - 3600000; //il decremento è stato fatto per far fronte al GMT+1:00 impostato in automatico
				let endTime = new Date(dataInMillisecondsTmp);
				

				startTimeInMilliseconds = daysTypeData.startTime.milliseconds;	//otteniamo i ms dell'ora d'inizio dell'evento
				endTimeInMilliseconds = daysTypeData.endTime.milliseconds;	//otteniamo i ms dell'ora di fine dell'evento
				if(this.isProfessor == false){
					_dataToUpload = {
						summary : name,
						startDateTime : startTime,
						endDateTime : endTime,
						//DA MODIFICARE QUANDO SI AVRANNO PIU' UTENTi
						workingGroup: {
							id: Number(wgId)
						},
						course: null,
						type:null,
						angularId: addInfo.event.id,
						startRecur: daysTypeData.startRecur,
						endRecur: daysTypeData.endRecur,
						daysOfWeek: _daysOfWeek,
						startTimeRecurrent: startTimeInMilliseconds,
						endTimeRecurrent: endTimeInMilliseconds
					}
				}else{
					_dataToUpload = {
						summary : name,
						startDateTime : startTime,
						endDateTime : endTime,
						//DA MODIFICARE QUANDO SI AVRANNO PIU' UTENTi
						workingGroup:null,
						course:  {
							id: Number(wgId)
						},
						type:null,
						angularId: addInfo.event.id,
						startRecur: daysTypeData.startRecur,
						endRecur: daysTypeData.endRecur,
						daysOfWeek: _daysOfWeek,
						startTimeRecurrent: startTimeInMilliseconds,
						endTimeRecurrent: endTimeInMilliseconds
					}

				}
				//console.log("end rec: ",daysTypeData.endRecur);
			}
			this.calendarService.create(_dataToUpload)
				.subscribe(
					response => {
						console.log(response);
					},
					error => {
						console.log(error);
					});
		}
	}
  
	//Metodo per la gestione della sincronizzazione tra front-end e DB in caso di cambiamento di un evento.
	handleEventChange(changeInfo: EventChangeArg){
		//console.log("evento da modificare:", changeInfo);
		this.changeTime(changeInfo);
	}
	
	changeTime(changeInfo){
		const _dataToUpload = {
				id: changeInfo.event.id,
		}
		this.calendarService.findByAngularId(_dataToUpload)
		.subscribe(
			response => {
				this.changeTimeEvent(changeInfo, response);
			},
			error => {
				console.log(error)
			});
	}
	
	//Metodo per effettuare la richiesta a backend per modificare gli orari di un evento.
	changeTimeEvent(changeInfo, response){
		if(!(this.isProfessor == false && response.course != null)){
			const _calendarApi = this.fullcalendar.getApi()
			const _dataToUpload = {
				id: response.id,
				startDate: changeInfo.event.start,
				endDate: changeInfo.event.end,
				oldStartDate: changeInfo.oldEvent.start,
				oldEndDate: changeInfo.oldEvent.end
			}
			console.log("Data da cambiare: ", _dataToUpload);
			this.calendarService.changeTime(_dataToUpload)
				.subscribe(
					response => {
						this.updateEventsInFrontEnd(response, changeInfo);
						//changeInfo.event.remove();
				},
				error => {
					console.log(error);
				});
		}
	}

	//Metodo per la gestione della sincronizzazione tra front-end e DB in caso di rimozione di un evento.
	handleEventRemove(removeInfo: EventRemoveArg){
		const _dataToUpload = {
			id: removeInfo.event.id
		}
		this.calendarService.findByAngularId(_dataToUpload)
		.subscribe(
			response => {
				this.deleteEvent(response);
			},
			error => {
				console.log(error);
			});
	
	}
  
	//Metodo per effettuare la richiesta a backend per rimuovere un evento.
	deleteEvent(response){
		const _dataToUpload = {
			id: response.id
		}
		this.calendarService.delete(_dataToUpload)
			.subscribe(
				response => {
					console.log(response);
				},
				error => {
					console.log(error);
				});
	}

	//Metodo per la gestione dell'evento di selezione di una data.
	handleDateSelect(selectInfo: DateSelectArg) {
		const _dataToUpload = {
			id:this.loggedUser.id
		}
		this.openDialog(selectInfo);
		/*this.calendarService.getIsProfessor(_dataToUpload)
		.subscribe(
			response => {
				this.openDialog(selectInfo, response);
			},
			error => {
				console.log(error)
		});*/
		
	}

	//Metodo per la gestione dell'evento di click su un evento già esistente.
	handleEventClick(clickInfo: EventClickArg) {
		const _dataToUpload = {
			id: clickInfo.event.id
		}
		this.calendarService.findByAngularId(_dataToUpload)			
			.subscribe(
				response => {
					this.checkEventDelete(response, clickInfo);
				},
				error => {
					console.log(error);
				});
		//clickInfo.event.remove();
	}
	
	checkEventDelete(response, clickInfo){
		console.log(response);
		if(!(this.isProfessor == false && response.course != null)){
			if (confirm(`Sei sicuro di voler rimuovere l'evento '${clickInfo.event.title}'?`)) {
				clickInfo.event.remove();
			}
		}
	}

	handleEvents(events: EventApi[]) {
		this.currentEvents = events;
	}
  
	async addEvent(selectInfo: DateSelectArg, data){		
		const _calendarApi = this.fullcalendar.getApi()
		const viewType = _calendarApi.currentData.currentViewType;
		const calendarApi = selectInfo.view.calendar;
		calendarApi.unselect();
		let max: any;
		max = await this.max;
		if(max == null){
			max = String(1);
		}else{
			max++;
			max = String(max);
		}
		//Se isRecurrent è false allora è un evento singolo
		if(!data[1]){
			//Se sono in visione mensile aggiungo un evento concatenando l'ora.
			if(viewType == 'dayGridMonth'){
				calendarApi.addEvent({
					id: max,
					title: data[0].title + "@" +data[0].dropDown.name + "@" + data[0].dropDown.id,
					start: selectInfo.startStr + 'T' + data[0].startTime +':00+01:00',
					end: selectInfo.startStr + 'T' + data[0].endTime +':00+01:00',
					allDay: false
				});
				//console.log(calendarApi.getEvents());
			}else{
				//Se sono in un'altra visione l'orario è già presente quindi seleziono solo la data e concateno l'orario.  this.createEventId(),
				calendarApi.addEvent({
					id: max,
					title: data[0].title + "@" +data[0].dropDown.name + "@" + data[0].dropDown.id,
					start: selectInfo.startStr.substr(0,10) + 'T' + data[0].startTime +':00+01:00',
					end: selectInfo.startStr.substr(0,10) + 'T' + data[0].endTime +':00+01:00',
					allDay: false
				});
			}
		}else{
			//Se è ricorrente allora creo la lista di giorni in cui l'evento si ripete.
			var _dayOfTheWeek: Array<string> = [];
			if(data[0].lun){
				_dayOfTheWeek.push('1');
			}
			if(data[0].mar){
				_dayOfTheWeek.push('2');
			}
			if(data[0].mer){
				_dayOfTheWeek.push('3');
			}
			if(data[0].gio){
				_dayOfTheWeek.push('4');
			}
			if(data[0].ven){
				_dayOfTheWeek.push('5');
			}
			if(data[0].sab){
				_dayOfTheWeek.push('6');
			}
			if(data[0].dom){
				_dayOfTheWeek.push('0');
			}
			//Siccome l'evento è ricorrente basta passare solo l'orario senza anno-mese-giorno
			calendarApi.addEvent({
				id: max,
				title: data[0].title + "@" +data[0].dropDown.name + "@" + data[0].dropDown.id,
				startTime: data[0].startTime +':00+01:00',
				endTime: data[0].endTime +':00+01:00',
				startRecur: selectInfo.startStr,
				endRecur: data[0].endRecur,
				daysOfWeek: _dayOfTheWeek,
				allDay: false
			});
		}
	}
	
	//Metodo per caricare in fullcalendar gli eventi da DB quando si carica la pagina.
	addInitialEvents(data){
		const calendarApi = this.fullcalendar.getApi();
		//ciclo su tutti gli eventi nel DB.
		for (let entry of data) {
			//Se non esiste un evento con lo stesso angularId
			if(calendarApi.getEventById(entry.angularId) == null){
				//Se è un evento singolo.
				if(entry.daysOfWeek == null){
					calendarApi.addEvent({
						id: entry.angularId,
						title: entry.title,
						start: entry.startTime,
						end: entry.endTime,
						allDay: false
					});
				}else{
					//Se è ricorrente.
					calendarApi.addEvent({
						id: entry.angularId,
						title: entry.title,
						startRecur: entry.startRecur,
						endRecur: entry.endRecur,
						daysOfWeek: entry.daysOfWeek.split(""),
						endTime: entry.endTimeRecurrent,
						startTime: entry.startTimeRecurrent,
						allDay: false						
					});
				}
			}
		}
		//console.log(calendarApi.getEvents());
	}
	
	//Evento per aggiornare gli eventi di fullcalendar in caso di cambiamenti a DB:
	//Esempio: Modifica di eventi ricorrenti a DB genera 3 eventi.
	updateEventsInFrontEnd(data, changeInfo){
		const calendarApi = this.fullcalendar.getApi();
		for (let entry of data) {
			if(entry.daysOfWeek == null){
				calendarApi.addEvent({
					id: entry.angularId,
					title: entry.title,
					start: entry.startTime,
					end: entry.endTime,
					allDay: false
				});
			}else{
				changeInfo.event.remove();
				calendarApi.addEvent({
					id: entry.angularId,
					title: entry.title,
					startRecur: entry.startRecur,
					endRecur: entry.endRecur,
					daysOfWeek: entry.daysOfWeek.split(""),
					endTime: entry.endTimeRecurrent,
					startTime: entry.startTimeRecurrent,
					allDay: false						
				});
			}
		}
		//console.log("aggiornati", data);
	}
	
	//Crea un angularId incrementando il massimo angularId già presente.

}