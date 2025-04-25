import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const ACCESS_TOKEN = 'access-token';

@Injectable({
    providedIn: 'root'
})
export class UserLoginService {

    constructor(private httpClient: HttpClient) {
    }

    isUserLoggedIn(): boolean {
        let accessToken: string | null = localStorage.getItem(ACCESS_TOKEN);
        // verify the token optionally
        return accessToken !== null;
    }

    setLoggedInUser(): void {
        localStorage.setItem(ACCESS_TOKEN, 'sth');
    }

    logout(): void {
        localStorage.removeItem(ACCESS_TOKEN);
    }


    //   findWeatherDataByCity(city: string): Observable<WeatherDetail> {
    //     let headers: HttpHeaders = new HttpHeaders();
    //     headers = headers.append('Accept', 'application/json');
    //     let url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&APPID=06e0579dbc344a3c84397874b6896e43`;
    //     return this.httpClient.get(url, { headers: headers })
    //       .pipe(map((res: any) => {
    //         let wd: WeatherDetail = {
    //           id: res.id,
    //           city: res.name,
    //           description: res.weather[0].description,
    //           temprature: 1
    //         };
    //         return wd;
    //       }));
    //   }


}
