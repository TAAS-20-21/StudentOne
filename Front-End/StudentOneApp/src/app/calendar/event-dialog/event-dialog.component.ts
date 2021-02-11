import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms'
import { CalendarService } from 'src/app/services/calendar.service';


@Component({
  selector: 'app-event-dialog',
  templateUrl: './event-dialog.component.html',
  styleUrls: ['./event-dialog.component.css']

})
export class EventDialogComponent implements OnInit {

	hiddenSingleEvent = false;
	hiddenRecurrentEvent = true;


    form: FormGroup;
    title:string;
	startTime:string;
	endTime:string;
	lun:boolean;
	mar:boolean;
	mer:boolean;
	gio:boolean;
	ven:boolean;
	sab:boolean;
	dom:boolean;
	startRecur:string;
	endRecur:string;
	//DROPDOWN
	dropDown:string;
	listDropDown:any;
	//DROPDOWN
	isRecurrent = false;
	dialogRef: MatDialogRef<EventDialogComponent>;
	isProfessor:boolean;
	loggedUser:any;
	calendarService: CalendarService;
    constructor(
        private fb: FormBuilder,
        private _dialogRef: MatDialogRef<EventDialogComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any){
			this.form = new FormGroup({
				title: new FormControl(),
				startTime: new FormControl(),
				endTime: new FormControl(),
				lun: new FormControl(),
				mar:new FormControl(),
				mer:new FormControl(),
				gio:new FormControl(),
				ven:new FormControl(),
				sab:new FormControl(),
				dom:new FormControl(),
				endRecur:new FormControl(),
				dropDown:new FormControl()
			});
			this.dialogRef = _dialogRef;
			this.isProfessor = data.isProfessor;
			this.loggedUser = data.loggedUser;
			this.calendarService = data.calendarService;
			if(this.isProfessor == false){
				console.log("Studente", this.loggedUser);
				const _dataToUpload = {
					id:this.loggedUser.id
				}
				this.calendarService.getWorkingGroupsById(_dataToUpload)
				.subscribe(
					response => {
						this.initializeListDropDown(response);
					},
					error => {
						console.log(error)
				});
			}else{
				console.log("Docente", this.loggedUser);
				/*DA MODIFICARE!!!! ORA È UNA CHIAMATA A STUDENTE, NON A DOCENTE
				this.calendarService.getCoursesById(_dataToUpload)
				.subscribe(
					response => {
						this.initializeListDropDown(response);
					},
					error => {
						console.log(error)
				});*/
			}
		}

    ngOnInit() {

    }
	initializeListDropDown(response){
		this.listDropDown = response;
		for (let i = 0; i < this.listDropDown.length; i++){
			const _dataToUpload = {
					id:this.listDropDown[i]
			}
			if(this.isProfessor == true){
				this.calendarService.getCourse(_dataToUpload)
					.subscribe(
						response => {
							console.log("CORSO",response);
							this.setResponseName(response.name, this.listDropDown[i], i);
						},
						error => {
							console.log(error)
					});
				//this.listDropDown[i] = {id: i, value: this.listDropDown[i]};
			}else{
				this.calendarService.getWorkingGroup(_dataToUpload)
					.subscribe(
						response => {
							console.log("WG",response);
							this.setResponseName(response.name, this.listDropDown[i], i);
						},
						error => {
							console.log(error)
					});
			}
		}
	}
	
	setResponseName(response, index, i){
		this.listDropDown[i] = {id: index, name: response};
		console.log(this.listDropDown[i]);
	}

    save() {
        this.dialogRef.close([this.form.value, this.isRecurrent]);
    }

    close() {
        this.dialogRef.close();
    }
	
	//Cambio da Dialog ricorrente a Dialog evento singolo.
	singleEventSelection() {
		if(this.hiddenSingleEvent){
			this.hiddenSingleEvent = !this.hiddenSingleEvent;
			this.hiddenRecurrentEvent = true;
			this.isRecurrent = false;
		}
	}
	
	//Cambio da Dialog evento singolo a Dialog ricorrente.
	recurrentEventSelection() {
		if(this.hiddenRecurrentEvent){
			this.hiddenRecurrentEvent = !this.hiddenRecurrentEvent;
			this.hiddenSingleEvent = true;
			this.isRecurrent = true;
		}
	}

}