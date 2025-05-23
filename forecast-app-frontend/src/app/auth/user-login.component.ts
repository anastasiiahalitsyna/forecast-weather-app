import { Component } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { UserLoginService } from "./user-login.service";

@Component({
  selector: 'app-root',
  templateUrl: './user-login.component.html',
  imports: [ReactiveFormsModule, RouterModule]
})
export class UserLogin {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private authService: UserLoginService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });

    if(authService.isUserLoggedIn()){
      router.navigate(['/home']);
    }
  }

  get email() {
    return this.loginForm.get('email')!;
  }

  get password() {
    return this.loginForm.get('password')!;
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      this.authService.login(credentials.email, credentials.password).subscribe({
        next: (res) => {
          this.authService.storeToken(res.accessToken);
          console.log('Logged in!');
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error('Login failed', err);
        }
      });
    }
  }
}