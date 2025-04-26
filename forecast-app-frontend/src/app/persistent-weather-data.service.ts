import { Injectable } from '@angular/core';
import { WeatherDetail } from './weather-detail';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserLoginService } from './auth/user-login.service';

const SERVICE_URL = 'http://localhost:8080/weather-service/api/forecasts';

export interface City {
    id: number;
    name: string;
}

@Injectable({
    providedIn: 'root'
})
export class PersistentWeatherDataService {

    constructor(private httpClient: HttpClient, private authService: UserLoginService) {
    }

    persistCity(cityName: string) {
        let token = this.authService.getAccessToken();

        const headers = new HttpHeaders({
            'Accept': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        let url = `${SERVICE_URL}/${cityName}`;
        return this.httpClient.post<void>(url, null, { headers });
    }

    isCityExists(cityName: string) {
        let token = this.authService.getAccessToken();

        const headers = new HttpHeaders({
            'Accept': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        let url = `${SERVICE_URL}/${cityName}`;
        return this.httpClient.get<{ exists: boolean }>(url, { headers });
    }

    deleteCity(cityName: string) {
        let token = this.authService.getAccessToken();

        const headers = new HttpHeaders({
            'Accept': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        let url = `${SERVICE_URL}/${cityName}`;
        return this.httpClient.delete<void>(url, { headers });
    }

    getCities() {
        let token = this.authService.getAccessToken();

        const headers = new HttpHeaders({
            'Accept': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        let url = `${SERVICE_URL}`;
        return this.httpClient.get<City[]>(url, { headers });
    }

}