import { Injectable } from '@angular/core';
import { WeatherDetail } from './weather-detail';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const FAVOURITE_CITIES_KEY = 'favourite-cities';


// if user is logged in =>  save data to both localstorage and database.
// if user is not logged in =>  just localstorage
@Injectable({
  providedIn: 'root'
})
export class WeatherDataService {

  constructor(private httpClient: HttpClient) {
  }


  findWeatherDataByCity(city: string): Observable<WeatherDetail> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Accept', 'application/json');
    let url = `https://api.openweathermap.org/data/2.5/weather?lang=pl&q=${city}&APPID=06e0579dbc344a3c84397874b6896e43`;
    return this.httpClient.get(url, { headers: headers })
      .pipe(map((res: any) => {
        let wd: WeatherDetail = {
          id: res.id,
          city: res.name,
          description: res.weather[0].description,
          temprature: this.convertToCelcius(res.main.temp)
        };
        return wd;
      }));
  }

  getAllWeatherData(): WeatherDetail[] {
    let storedCities = localStorage.getItem(FAVOURITE_CITIES_KEY);
    if (!storedCities) {
      storedCities = "[]";
    }
    let citiesContainerArr: WeatherDetail[] = JSON.parse(storedCities);
    return citiesContainerArr.sort((w1, w2) => w1.id - w2.id);
  }

  saveItem(itemToSave: WeatherDetail): void {
    if (this.findItemInLocalStorage(itemToSave.city)) {
      return;
    }
    let currentCities: WeatherDetail[] = this.getAllWeatherData();
    currentCities.push(itemToSave);
    localStorage.setItem(FAVOURITE_CITIES_KEY, JSON.stringify(currentCities));
  }

  deleteItem(city: string): void {
    let currentCities: WeatherDetail[] = this.getAllWeatherData();
    currentCities = currentCities.filter(c => c.city.toLocaleLowerCase() !== city.toLocaleLowerCase());
    localStorage.setItem(FAVOURITE_CITIES_KEY, JSON.stringify(currentCities));
  }

  findItemInLocalStorage(city: string): boolean {
    let currentCities: WeatherDetail[] = this.getAllWeatherData();
    return currentCities.find(c => c.city.toLocaleLowerCase() === city.toLocaleLowerCase()) !== undefined;
  }


  private convertToCelcius(tempKelvin: number): number {
    return Math.round(tempKelvin - 273.15);
  }
}
