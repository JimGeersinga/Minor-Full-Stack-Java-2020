import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Observable } from 'rxjs/internal/Observable';
import { User } from '../models/user';
import {login, logout} from '../ngrx/auth/auth.actions';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private users: User[];
  public loggedInUser$: BehaviorSubject<User | undefined> = new BehaviorSubject(undefined);

  constructor(
    private store: Store<{isLoggedIn: boolean}>
  ) {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.users = [
      { username: 'jim', password: 'password', initials: 'J', lastName: 'Geersinga' },
      { username: 'demo', password: 'password', initials: 'D', lastName: 'Demo' }
    ];
  }

  public getUserByUsername(username: string): User {
    return this.users.find((u) => u.username.toLowerCase() === username?.toLowerCase());
  }

  public getUsers(): User[] {
    return this.users;
  }

  public save(user: User): void {
    const foundUser = this.getUserByUsername(user.username);
    if (foundUser) {
      foundUser.birthDate = user.birthDate;
      foundUser.initials = user.initials;
      foundUser.lastName = user.lastName;
      foundUser.street = user.street;
      foundUser.houseNumber = user.houseNumber;
      foundUser.city = user.city;
      foundUser.postalCode = user.postalCode;
      foundUser.bsn = user.bsn;
      foundUser.birthDate = user.birthDate;
      foundUser.phoneNumber = user.phoneNumber;
      foundUser.email = user.email;
    }
  }

  public login(username: string, password: string): Observable<boolean> {
    return new Observable((observer) => {
      const user = this.getUserByUsername(username);
      if (user && user.password === password) {
        this.store.dispatch(login());
        this.loggedInUser$.next(user);

        observer.next(true);
      } else {
        observer.next(false);
      }
    });
  }

  public logout(): void {
    this.store.dispatch(logout());
    this.loggedInUser$.next(undefined);
  }
}
