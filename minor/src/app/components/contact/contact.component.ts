import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent implements OnInit {
  contactForm: any;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.loggedInUser$.subscribe((user) => {
      this.contactForm = this.formBuilder.group({
        initials: user?.initials,
        lastName: user?.lastName,
        street: user?.street,
        houseNumber: user?.houseNumber,
        city: user?.city,
        postalCode: user?.postalCode,
        bsn: user?.bsn,
        birthDate: user?.birthDate,
        phoneNumber: user?.phoneNumber,
        email: user?.email
      });
    });

  }

  onSubmit(contactData: User): void {
    this.userService.save(contactData);
  }
}
