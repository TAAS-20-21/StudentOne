import { Component } from '@angular/core';
import { CalendarOptions, DateSelectArg, EventClickArg, EventApi } from '@fullcalendar/angular';
import { INITIAL_EVENTS, createEventId } from './event-utils';
import { EventChangeArg, EventAddArg, EventRemoveArg } from '@fullcalendar/common'
//DIALOG
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { EventDialogComponent } from './event-dialog/event-dialog.component'
//DIALOG
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class OurCalendarComponent {
  //DIALOG  
  constructor(private dialog: MatDialog) {}
  openDialog(selectInfo: DateSelectArg) {

    const dialogConfig = new MatDialogConfig();

	dialogConfig.disableClose = true;
	dialogConfig.autoFocus = true;
	
	const dialogRef = this.dialog.open(EventDialogComponent, dialogConfig);

	dialogRef.afterClosed().subscribe( (data) => {
		if(data){
			this.addEvent(selectInfo, data);
		}
    });
  }
  //DIALOG
  calendarVisible = true;
  calendarOptions: CalendarOptions = {
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    initialView: 'dayGridMonth',
    initialEvents: INITIAL_EVENTS, // alternatively, use the `events` setting to fetch from a feed
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
    eventRemove: this.handleEventRemove.bind(this)
  };
  currentEvents: EventApi[] = [];
  
  
  //SEE @fullcalendar/interaction/main.js row:1444
  //SEE @fullcalendar/common/main.d.ts row 1790
  handleEventAdd(addInfo: EventAddArg){
	console.log("INIZIO ADD");
	console.log(addInfo.event.title);
	console.log(addInfo.event.startStr);
	console.log(addInfo.event.endStr);
	console.log("FINE ADD");
  }
  
  
  handleEventChange(changeInfo: EventChangeArg){
	console.log("INIZIO CHANGE");
	console.log(changeInfo.event.title);
	console.log(changeInfo.event.startStr);
	console.log(changeInfo.event.endStr);
	console.log("FINE CHANGE");
  }

  handleEventRemove(removeInfo: EventRemoveArg){
	console.log("INIZIO REMOVE");
	console.log(removeInfo.event.title);
	console.log(removeInfo.event.startStr);
	console.log(removeInfo.event.endStr);
	console.log("FINE REMOVE"); 
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
	const calendarApi = selectInfo.view.calendar;
	calendarApi.unselect();
	calendarApi.addEvent({
		id: createEventId(),
		title: data.title,
		start: selectInfo.startStr + 'T' + data.startTime +':00',
		end: selectInfo.startStr + 'T' + data.endTime +':00',
		allDay: false
	});
  }

}
