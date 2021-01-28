import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { SocialLoginModule, SocialAuthServiceConfig  } from "angularx-social-login";
import { GoogleLoginProvider } from 'angularx-social-login';
import { FormsModule } from '@angular/forms';
import { BoardUserComponent } from './board-user/board-user.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { authInterceptorProviders } from './common/auth.interceptor';

import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import interactionPlugin from '@fullcalendar/interaction';
import { OurCalendarComponent } from './calendar/calendar.component';
//DIALOG
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from "@angular/material/dialog";
import { EventDialogComponent } from './calendar/event-dialog/event-dialog.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
//DIALOG

FullCalendarModule.registerPlugins([
  dayGridPlugin,
  timeGridPlugin,
  listPlugin,
  interactionPlugin
])

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChatComponent } from './chat/chat.component';


@NgModule({
  declarations: [
    AppComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    HomeComponent,
    LoginComponent,
    ProfileComponent,
    RegisterComponent,
    OurCalendarComponent,
	//DIALOG
	EventDialogComponent,

    ChatComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatCardModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    SocialLoginModule,
    FormsModule,
    NgbModule,
    FullCalendarModule,
	//DIALOG
	MatDialogModule,
	MatFormFieldModule,
	ReactiveFormsModule,
	MatInputModule,
	BrowserModule,
	MatCheckboxModule
	//DIALOG
  ],
  //DIALOG
  exports: [
	MatFormFieldModule,
    NgbModule
  ],
  //DIALOG
  providers: [
    authInterceptorProviders
  ],
  bootstrap: [AppComponent],
  //DIALOG
  entryComponents: [EventDialogComponent]
  //DIALOG
})
export class AppModule { }
