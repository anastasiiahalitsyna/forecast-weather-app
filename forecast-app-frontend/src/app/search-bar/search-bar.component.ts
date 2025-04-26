import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WeatherDataService } from '../weather-data.service';
import { ToastrService } from 'ngx-toastr';
import { UserLoginService } from '../auth/user-login.service';
import { PersistentWeatherDataService } from '../persistent-weather-data.service';

@Component({
  selector: 'app-search-bar',
  imports: [CommonModule, FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {

  @Output() refreshDashboard = new EventEmitter<void>();

  searchedValue: string = '';
  loading: boolean = false;

  constructor(private service: WeatherDataService,
    private notifier: ToastrService,
    private authService: UserLoginService,
    private persistentService: PersistentWeatherDataService
  ) {
  }
  search(val: string): void {
    if (this.authService.isUserLoggedIn()) {
      this.searchAuthenticated(val);
    } else {
      this.searchLocal(val);
    }
  }


  searchLocal(val: string): void {
    if (!val || val.length < 2) {
      this.notifier.warning('Ups!', 'Wprowadź dane wejściowe!');
      return;
    }
    this.loading = true;
    if (this.service.findItemInLocalStorage(val)) {
      this.loading = false;
      this.notifier.warning('Ups!', 'Mamy już to miasto');
      return;
    }
    this.service.findWeatherDataByCity(val)
      .subscribe({
        next: found => {
          this.service.saveItem(found);
          this.searchedValue = '';
          this.refreshDashboard.emit();
          this.notifier.success('Sukces!', 'Miasto jest dodane do pulpitu nawigacyjnego');
        },
        error: err => {
          this.loading = false;
          if (err.status === 404) {
            this.notifier.warning('Ups!', 'Nie udało się znaleźć miasta');
          } else {
            this.notifier.error('Ups!', 'Nie udało się połączyć z dostawcą danych');
          }
        },
        complete: () => {
          this.loading = false;
        }
      });
  }

  searchAuthenticated(val: string): void {
    if (!val || val.length < 2) {
      this.notifier.warning('Ups!', 'Wprowadź dane wejściowe!');
      return;
    }
    this.loading = true;

    this.persistentService.isCityExists(val).subscribe({
      next: res => {
        if (res.exists) {
          this.loading = false;
          this.notifier.warning('Ups!', 'Mamy już to miasto');
        } else {

          this.service.findWeatherDataByCity(val)
            .subscribe({
              next: found => {
                this.persistentService.persistCity(val).subscribe({
                  next: res => {
                    this.searchedValue = '';
                    this.refreshDashboard.emit();
                    this.notifier.success('Sukces!', 'Miasto jest dodane do pulpitu nawigacyjnego');
                  }
                });

              },
              error: err => {
                this.loading = false;
                if (err.status === 404) {
                  this.notifier.warning('Ups!', 'Nie udało się znaleźć miasta');
                } else {
                  this.notifier.error('Ups!', 'Nie udało się połączyć z dostawcą danych');
                }
              },
              complete: () => {
                this.loading = false;
              }
            });
        }
      },
      error: err => {
        this.loading = false;
        if (err.status === 404) {
          this.notifier.warning('Ups!', 'Nie udało się znaleźć miasta');
        } else {
          this.notifier.error('Ups!', 'Nie udało się połączyć z dostawcą danych');
        }
      },
      complete: () => {
        this.loading = false;
      }
    }
    );

  }
}
