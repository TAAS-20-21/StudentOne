import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms'
import { CalendarService } from 'src/app/services/calendar.service';
import { WorkingGroup } from "../../models/working-group.model";
import { Course } from "../../models/course.model";


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
	listDropDownWG:WorkingGroup[];
	listDropDownCourses: Course[];
	//DROPDOWN
	isRecurrent = false;
	dialogRef: MatDialogRef<EventDialogComponent>;
	isProfessor:boolean;
	loggedUser:any;
	calendarService: CalendarService;
	selectInfo: any;
	day: any;
	monBool: boolean;
	tueBool: boolean;
	wedBool: boolean;
	thuBool: boolean;
	friBool: boolean;
	satBool: boolean;
	sunBool: boolean;
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
			this.selectInfo = data.selectInfo;
			const _dataToUpload = {
					id:this.loggedUser.id
			}
			if(this.isProfessor == false){
				this.calendarService.getWorkingGroupsById(_dataToUpload)
				.subscribe(
					response => {
						this.initializeListDropDown(response);
					},
					error => {
						console.log(error)
				});
			}else{
				this.calendarService.getCoursesById(_dataToUpload)
				.subscribe(
					response => {
						this.initializeListDropDown(response);
					},
					error => {
						console.log(error)
				});
			}
		}

    ngOnInit() {

    }
	
	initializeListDropDown(response){
		this.listDropDownWG = [];
		this.listDropDownCourses = [];
		this.listDropDown = response;
		console.log(this.listDropDown);
		for (let i = 0; i < this.listDropDown.length; i++){
			const _dataToUpload = {
					id:this.listDropDown[i]
			}
			if(this.isProfessor == true){
				this.calendarService.getCourse(_dataToUpload)
					.subscribe(
						response => {
							this.setResponseName(response, this.listDropDown[i]);
						},
						error => {
							console.log(error)
					});
			}else{
				this.calendarService.getWorkingGroup(_dataToUpload)
					.subscribe(
						response => {
							this.setResponseName(response, this.listDropDown[i]);
						},
						error => {
							console.log(error)
					});
			}
		}
	}
	
	setResponseName(response, index){
		if(this.isProfessor == false){
			this.listDropDownWG.push({id: index, name: response.name});
		}else{
			this.listDropDownCourses.push({id: index, name: response.name});
		}
		console.log("WG",this.listDropDownWG);
		console.log("CORSI",this.listDropDownCourses);
	}

    save() {
		console.log(this.form.value);
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
			this.day = this.selectInfo.start.toDateString().substr(0,3);
			
			this.monBool = false;
			this.tueBool = false;
			this.wedBool = false;
			this.thuBool = false;
			this.friBool = false;
			this.satBool = false;
			this.sunBool = false;
			
			if(this.day == 'Mon'){
				this.monBool = true;
				this.form.controls['lun'].setValue(true);
			} else if(this.day == 'Tue'){
				this.tueBool = true;
				this.form.controls['mar'].setValue(true);
			}else if(this.day == 'Wed'){
				this.wedBool = true;
				this.form.controls['mer'].setValue(true);
			}else if(this.day == 'Thu'){
				this.thuBool = true;
				this.form.controls['gio'].setValue(true);
			}else if(this.day == 'Fri'){
				this.friBool = true;
				this.form.controls['ven'].setValue(true);
			}else if(this.day == 'Sat'){
				this.satBool = true;
				this.form.controls['sab'].setValue(true);
			}else if(this.day == 'Sun'){
				this.sunBool = true;
				this.form.controls['dom'].setValue(true);
			}
			this.hiddenRecurrentEvent = !this.hiddenRecurrentEvent;
			this.hiddenSingleEvent = true;
			this.isRecurrent = true;
		}
	}

}