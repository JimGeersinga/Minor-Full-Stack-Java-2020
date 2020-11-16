import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Observable } from 'rxjs/internal/Observable';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private users: User[];
  public isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public loggedInUser$: BehaviorSubject<User | undefined> = new BehaviorSubject(undefined);

  constructor() {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.users = [
      { username: 'jim', password: 'password', initials: 'J', lastName: 'Geersinga' },
      { username: 'demo', password: 'password', initials: 'D', lastName: 'Demo' }
    ];
  }

  public getUsers(): User[] {
    return this.users;
  }

  public save(user: User): void {

  }

  public login(username: string, password: string): Observable<boolean> {
    return new Observable((observer) => {
      const user = this.users.find((u) => u.username.toLowerCase() === username.toLowerCase() && u.password === password);
      if (user) {
        this.isLoggedIn$.next(true);
        this.loggedInUser$.next(user);

        observer.next(true);
      } else {
        observer.next(false);
      }
    });
  }

  public logout(): void {
    this.isLoggedIn$.next(false);
    this.loggedInUser$.next(undefined);
  }
}
