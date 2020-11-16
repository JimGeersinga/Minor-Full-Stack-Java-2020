import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should not be found', () => {
    expect(service.getUserByUsername('abcd')).toBeFalsy();
  });

  it('should be found', () => {
    expect(service.getUserByUsername('jim')).toBeTruthy();
  });

  it('should be success', () => {
    expect(service.login('jim', 'password')).toBeTruthy();
    expect(service.loggedInUser$.value).toBeTruthy();
    service.logout();
    expect(service.loggedInUser$.value).toBeFalsy();
  });

  it('should be failed', () => {
    expect(service.login('jim', 'abcd')).toBeFalsy();
    expect(service.loggedInUser$.value).toBeFalsy();
  });

});
