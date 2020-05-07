import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { map, take } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
        boolean | Promise<boolean> | Observable<boolean | UrlTree> {
        return this.authService.user.pipe(
            //take 1 means the observale is subscribed only once
            take(1),
            map(user => {
                const isAuth = user ? true : false
                if (isAuth) {
                    return true;
                }
                //redirect user to auth login if user is not authnticated
                return this.router.createUrlTree(['/auth'])
            }))

    }
}