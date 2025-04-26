import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    standalone: true,
    selector: 'app-register',
    templateUrl: './register.component.html',
    imports: [CommonModule, ReactiveFormsModule, RouterModule],
})
export class RegisterComponent {

    registerForm: FormGroup;

    constructor(private fb: FormBuilder, private router: Router) {
        this.registerForm = this.fb.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
        });
    }

    get firstName() {
        return this.registerForm.get('firstName')!;
    }

    get lastName() {
        return this.registerForm.get('lastName')!;
    }

    get email() {
        return this.registerForm.get('email')!;
    }

    get password() {
        return this.registerForm.get('password')!;
    }

    onSubmit() {
        if (this.registerForm.valid) {
            console.log('User registered:', this.registerForm.value);
            // need to call register api
            this.router.navigate(['/login']);
        } else {
            console.log('Form is not valid')
        }
    }
}
