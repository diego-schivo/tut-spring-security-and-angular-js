import { Component } from '@angular/core';
import { AppService } from './app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private app: AppService, private http: HttpClient, private router: Router) {
      this.app.authenticate({}, undefined);
    }
    logout() {
      this.http.post('logout', {}).pipe(tap(() => {
          this.app.authenticated = false;
          this.router.navigateByUrl('/login');
      }));
    }

}
