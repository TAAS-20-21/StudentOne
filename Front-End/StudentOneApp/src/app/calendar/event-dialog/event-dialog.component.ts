import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms'


@Component({
  selector: 'app-event-dialog',
  templateUrl: './event-dialog.component.html',
  styleUrls: ['./event-dialog.component.css']

})
export class EventDialogComponent implements OnInit {

    form: FormGroup;
    title:string;
	startTime:string;
	endTime:string;
	dialogRef: MatDialogRef<EventDialogComponent>;
    constructor(
        private fb: FormBuilder,
        private _dialogRef: MatDialogRef<EventDialogComponent>){
			this.form = new FormGroup({
				title: new FormControl(),
				startTime: new FormControl(),
				endTime: new FormControl()
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
        this.dialogRef.close(this.form.value);
    }

    close() {
        this.dialogRef.close();
    }

}