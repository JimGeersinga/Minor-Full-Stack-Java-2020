import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: any;
  errorMessage: string;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
    ) {
    this.loginForm = this.formBuilder.group({
      email: '',
      password: '',
    });
   }

  ngOnInit(): void {
  }

  onSubmit(loginData: any): void {
    this.errorMessage = '';

    if (this.loginForm.dirty && this.loginForm.valid) {
      this.loginForm.reset();
      this.userService.login(loginData.email, loginData.password).subscribe(success => {
        if (success) {
          this.router.navigate(['contact']);
        } else {
          this.errorMessage = 'Login failed';
        }
      },
      error => {
        this.errorMessage = 'Something went wrong: ' + error;
      });
    }
  }
}
