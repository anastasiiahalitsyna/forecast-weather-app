import { Component } from '@angular/core';

import { Router, RouterModule } from '@angular/router';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { WeatherCardComponent } from '../weather-card/weather-card.component';
import { WeatherDetail } from '../weather-detail';
import { WeatherDataService } from '../weather-data.service';
import { UserLoginService } from '../auth/user-login.service';

@Component({
  selector: 'app-root',
  imports: [SearchBarComponent, WeatherCardComponent, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  weatherDetails: WeatherDetail[] = [];


  constructor(
    private serviceInj: WeatherDataService,
    private authService: UserLoginService,
    private router: Router) {

    this.readItems();
  };

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  logoutUser(): void {
    this.authService.logout();
  }

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

