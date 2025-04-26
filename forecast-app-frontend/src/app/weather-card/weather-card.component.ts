import { Component, EventEmitter, Input, Output } from '@angular/core';
import { WeatherDetail } from '../weather-detail';
import { WeatherDataService } from '../weather-data.service';
import { UserLoginService } from '../auth/user-login.service';
import { PersistentWeatherDataService } from '../persistent-weather-data.service';

@Component({
  selector: 'app-weather-card',
  imports: [],
  templateUrl: './weather-card.component.html',
  styleUrl: './weather-card.component.css'
})
export class WeatherCardComponent {
  @Input() weatherDetail!: WeatherDetail;
  @Output() refreshDashboard = new EventEmitter<void>();

  constructor(private service: WeatherDataService, 
    private authServie: UserLoginService, 
    private persistentService: PersistentWeatherDataService) {

  }

  removeCard(city: string): void {
    if (this.authServie.isUserLoggedIn()) {
      this.removeCardPersistently(city);
    } else {
      this.removeCardLocally(city);
    }
  }

  removeCardLocally(city: string): void {
    this.service.deleteItem(city);
    this.refreshDashboard.emit();
  }

  removeCardPersistently(city: string): void {
    this.persistentService.deleteCity(city).subscribe({
      next: next => {
        this.refreshDashboard.emit();
      }
    });
  }
}
