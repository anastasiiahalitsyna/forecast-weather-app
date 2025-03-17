import { Component } from '@angular/core';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { WeatherCardComponent } from './weather-card/weather-card.component';
import { WeatherDetail } from './weather-detail';
import { WeatherDataService } from './weather-data.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports: [SearchBarComponent, WeatherCardComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  weatherDetails: WeatherDetail[] = [];
  // new AppComponent(new WeatherDataService(new HttpClient(....) ))
  constructor(private serviceInj: WeatherDataService) {
    this.readItems();
  };

  readItems(): void {
    this.weatherDetails = this.serviceInj.getAllWeatherData();
    this.refreshingCity();
  }

  refreshDashboard(): void {
    this.readItems();

  }


  refreshingCity(): void {
    for (let weatherDetail of this.weatherDetails) {
      this.serviceInj.findWeatherDataByCity(weatherDetail.city)
        .subscribe({
          next: found => {
           this.serviceInj.saveItem(found);
          }
        });
    }
  }
}

