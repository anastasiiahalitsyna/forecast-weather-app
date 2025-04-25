import { Routes } from '@angular/router';
import { UserLogin } from './auth/user-login.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './registration/register.component';

export const routes: Routes = [
    {path: 'login', component: UserLogin},
    {path: 'register', component: RegisterComponent},
    {path: '', component: HomeComponent},
    {path: '**', component: HomeComponent}
];
