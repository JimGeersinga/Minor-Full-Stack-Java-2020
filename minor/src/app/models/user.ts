export interface User {
  username: string;
  password: string;

  // info
  initials?: string;
  lastName?: string;
  street?: string;
  houseNumber?: number;
  city?: string;
  postalCode?: string;
  bsn?: string;
  birthDate?: Date;
  phoneNumber?: string;
  email?: string;
}
