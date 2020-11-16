import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  isLoggedIn$: Observable<boolean>;
  loggedInUser$: Observable<User>;

  constructor(
    private userService: UserService,
    private router: Router,
    private snackbar: MatSnackBar
    ) {
    this.isLoggedIn$ = this.userService.isLoggedIn$;
    this.loggedInUser$ = this.userService.loggedInUser$;
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.userService.logout();
    this.router.navigateByUrl('/home');
    this.snackbar.open('You have been logged out!');
  }
}
