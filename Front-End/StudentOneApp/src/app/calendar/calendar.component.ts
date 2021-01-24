import { Component, ViewChild  } from '@angular/core';
import { CalendarOptions, DateSelectArg, EventClickArg, EventApi, FullCalendarComponent } from '@fullcalendar/angular';
import { EventChangeArg, EventAddArg, EventRemoveArg } from '@fullcalendar/common'
//DIALOG
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { EventDialogComponent } from './event-dialog/event-dialog.component'
//DIALOG
import { CalendarService } from 'src/app/services/calendar.service';


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class OurCalendarComponent {
	//DIALOG  
	constructor(private dialog: MatDialog, private calendarService: CalendarService) {}
  
	ngOnInit(): void {
		this.calendarService.getAll()
		.subscribe(
			response => {
			this.addInitialEvents(response);
			},
			error => {
				console.log(error);
			});
	}

  
  
	openDialog(selectInfo: DateSelectArg) {

		const dialogConfig = new MatDialogConfig();

		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = true;
		
		const dialogRef = this.dialog.open(EventDialogComponent, dialogConfig);
		
		/*const calendarApi = this.fullcalendar.getApi(); 
		const viewType = calendarApi.currentData.currentViewType;
		if(viewType != 'dayGridMonth'){
			console.log(selectInfo.startStr.substr(0,10));
		}
		console.log(calendarApi);*/
		
		dialogRef.afterClosed().subscribe( (data) => {
			if(data){
				this.addEvent(selectInfo, data);
			}
		});
	}
  
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
		locale: 'en'
		};
	currentEvents: EventApi[] = [];
  
  
	//SEE @fullcalendar/interaction/main.js row:1444
	//SEE @fullcalendar/common/main.d.ts row 1790
	handleEventAdd(addInfo: EventAddArg){
		const _dataToUpload = {
			id: addInfo.event.id
		}
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
		if(queryRes ==null){
			let _dataToUpload: any;
			if(addInfo.event.startStr != ""){
			//PER EVENTI SINGOLI
				_dataToUpload = {
					summary : addInfo.event.title,
					startDateTime : addInfo.event.start,
					endDateTime : addInfo.event.end,
					//DA MODIFICARE QUANDO SI AVRANNO PIU' UTENTi
					workingGroup: {
						id: 1
					},
					course: null,
					type:null,
					angularId: addInfo.event.id
				}
				console.log(addInfo.event.endStr);
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
				
				_dataToUpload = {
					summary : addInfo.event.title,
					startDateTime : startTime,
					endDateTime : endTime,
					//DA MODIFICARE QUANDO SI AVRANNO PIU' UTENTi
					workingGroup: {
						id: 1
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
  
	handleEventChange(changeInfo: EventChangeArg){
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
	
	changeTimeEvent(changeInfo, response){
		const _dataToUpload = {
			id: response.id,
			startDate: changeInfo.event.startStr,
			endDate: changeInfo.event.endStr
		}
		console.log(_dataToUpload);
		this.calendarService.changeTime(_dataToUpload)
			.subscribe(
				response => {
				console.log(response);
			},
			error => {
				console.log(error);
			});
	}

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
  
	handleCalendarToggle() {
		this.calendarVisible = !this.calendarVisible;
	}

	handleWeekendsToggle() {
		const { calendarOptions } = this;
		calendarOptions.weekends = !calendarOptions.weekends;
	}

	handleDateSelect(selectInfo: DateSelectArg) {
		this.openDialog(selectInfo);
	}

	handleEventClick(clickInfo: EventClickArg) {
		if (confirm(`Sei sicuro di voler rimuovere l'evento '${clickInfo.event.title}'?`)) {
			clickInfo.event.remove();
		}
	}

	handleEvents(events: EventApi[]) {
		this.currentEvents = events;
	}
  
	addEvent(selectInfo: DateSelectArg, data){		
		const _calendarApi = this.fullcalendar.getApi()
		const viewType = _calendarApi.currentData.currentViewType;
		//console.log(viewType == 'dayGridMonth');
		const calendarApi = selectInfo.view.calendar;
		calendarApi.unselect();
		if(!data[1]){
			if(viewType == 'dayGridMonth'){
				calendarApi.addEvent({
					id: this.createEventId(),
					title: data[0].title,
					start: selectInfo.startStr + 'T' + data[0].startTime +':00+01:00',
					end: selectInfo.startStr + 'T' + data[0].endTime +':00+01:00',
					allDay: false
				});
				//console.log(calendarApi.getEvents());
			}else{
				
				calendarApi.addEvent({
					id: this.createEventId(),
					title: data[0].title,
					start: selectInfo.startStr.substr(0,10) + 'T' + data[0].startTime +':00+01:00',
					end: selectInfo.startStr.substr(0,10) + 'T' + data[0].endTime +':00+01:00',
					allDay: false
				});
			}
		}else{
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
			calendarApi.addEvent({
				id: this.createEventId(),
				title: data[0].title,
				startTime: data[0].startTime +':00+01:00',
				endTime: data[0].endTime +':00+01:00',
				startRecur: selectInfo.startStr,
				endRecur: data[0].endRecur,
				daysOfWeek: _dayOfTheWeek,
				allDay: false
			});
		}
	}
	
	addInitialEvents(data){
		const calendarApi = this.fullcalendar.getApi();
		for (let entry of data) {
			if(calendarApi.getEventById(entry.angularId) == null){
				if(entry.daysOfWeek == null){
					calendarApi.addEvent({
						id: entry.angularId,
						title: entry.title,
						start: entry.startTime,
						end: entry.endTime,
						allDay: false
					});
				}else{
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
		console.log(calendarApi.getEvents());
	}
	
	createEventId(){
		const calendarApi = this.fullcalendar.getApi();
		const events = calendarApi.getEvents();
		var max: number = 0;
		for( let entry of events){
			if(Number(entry._def.publicId) >= max){
				max = Number(entry._def.publicId);
				
			}
		}
		max++;
		return String(max);
	}
}
