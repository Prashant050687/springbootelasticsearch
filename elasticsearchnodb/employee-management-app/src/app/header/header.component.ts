import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { User } from '../auth/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  private subscription: Subscription;
  isAuthenticated = false;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.subscription = this.authService.user.subscribe((user: User) => {
      this.isAuthenticated = !user ? false : true;
    })
  }

  onLogout() {
    this.authService.logout();
  }

}
