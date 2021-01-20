import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms'


@Component({
  selector: 'app-event-dialog',
  templateUrl: './event-dialog.component.html',
  styleUrls: ['./event-dialog.component.css']

})
export class EventDialogComponent implements OnInit {

	isShowSingleEvent = false;
	isShowRecurrentEvent = true;


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
	isRecurrent = false;
	dialogRef: MatDialogRef<EventDialogComponent>;
    constructor(
        private fb: FormBuilder,
        private _dialogRef: MatDialogRef<EventDialogComponent>){
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
				endRecur:new FormControl()
			});
			this.dialogRef = _dialogRef;
		}
        /*@Inject(MAT_DIALOG_DATA) {description} ) {

        this.description = description;


        this.form = fb.group({
            description: [description, Validators.required],
        });
		

    }*/

    ngOnInit() {

    }


    save() {
        this.dialogRef.close([this.form.value, this.isRecurrent]);
    }

    close() {
        this.dialogRef.close();
    }
	
	singleEventSelection() {
		if(this.isShowSingleEvent){
			this.isShowSingleEvent = !this.isShowSingleEvent;
			this.isShowRecurrentEvent = true;
			this.isRecurrent = false;
		}
	}
	
	recurrentEventSelection() {
		if(this.isShowRecurrentEvent){
			this.isShowRecurrentEvent = !this.isShowRecurrentEvent;
			this.isShowSingleEvent = true;
			this.isRecurrent = true;
		}
	}

}