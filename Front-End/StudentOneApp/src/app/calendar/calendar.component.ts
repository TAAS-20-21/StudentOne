import { Component } from '@angular/core';
import { CalendarOptions, DateSelectArg, EventClickArg, EventApi } from '@fullcalendar/angular';
import { INITIAL_EVENTS, createEventId } from './event-utils';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class OurCalendarComponent {
  calendarVisible = true;
  calendarOptions: CalendarOptions = {
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    initialView: 'timeGridWeek',
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
    eventChange: this.handleEventChange(this),
    eventRemove: this.handleEventRemove(this)
  };
  currentEvents: EventApi[] = [];
  
  
  //SEE @fullcalendar/interaction/main.js row:1444
  handleEventAdd(addInfo){
	console.log("INIZIO ADD");
	console.log(addInfo.event.title);
	console.log(addInfo.event.startStr);
	console.log(addInfo.event.endStr);
	//console.log(addInfo.event);
	console.log("FINE ADD");
  }
  
  
  handleEventChange(changeInfo){
	if(changeInfo){
		console.log("INIZIO CHANGE");
		//console.log(changeInfo.event.title);
		//console.log(changeInfo.event.startStr);
		//console.log(changeInfo.event.endStr);
		console.log(changeInfo);
		console.log("FINE CHANGE");
	}
  }
  
  handleEventRemove(removeInfo){
	if(removeInfo){
		//console.log("INIZIO REMOVE");
		//console.log(removeInfo.event.title);
		//console.log(removeInfo.event.startStr);
		//console.log(removeInfo.event.endStr);
		console.log(removeInfo);
		console.log("FINE REMOVE"); 
	}
  }
  
  handleCalendarToggle() {
    this.calendarVisible = !this.calendarVisible;
  }

  handleWeekendsToggle() {
    const { calendarOptions } = this;
    calendarOptions.weekends = !calendarOptions.weekends;
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    const title = prompt('Inserire il titolo per il tuo evento');
    const calendarApi = selectInfo.view.calendar;
    calendarApi.unselect(); // clear date selection

    if (title) {
      calendarApi.addEvent({
        id: createEventId(),
        title,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        allDay: selectInfo.allDay
      });
    }
  }

  handleEventClick(clickInfo: EventClickArg) {
    if (confirm(`Sei sicuro di voler rimuovere l'evento '${clickInfo.event.title}'?`)) {
      clickInfo.event.remove();
    }
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents = events;
  }

}
