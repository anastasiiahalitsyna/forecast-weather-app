import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const ACCESS_TOKEN = 'access-token';
const SERVICE_URL = 'http://localhost:8080/weather-service/api/auth';

@Injectable({
    providedIn: 'root'
})
export class UserLoginService {

    constructor(private httpClient: HttpClient) {
    }

    login(email: string, password: string) {
        const payload = { email, password };
        return this.httpClient.post<{ accessToken: string }>(`${SERVICE_URL}/login`, payload);
    }

    getAccessToken(): string | null {
        return localStorage.getItem(ACCESS_TOKEN);
    }

    storeToken(token: string) {
        localStorage.setItem(ACCESS_TOKEN, token);
    }

    isUserLoggedIn(): boolean {
        let accessToken: string | null = localStorage.getItem(ACCESS_TOKEN);
        // verify the token optionally
        return accessToken !== null;
    }


    logout(): void {
        localStorage.removeItem(ACCESS_TOKEN);
    }


}
