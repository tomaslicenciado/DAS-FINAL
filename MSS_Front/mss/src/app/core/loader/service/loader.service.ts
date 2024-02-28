import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {

  private _subject = new Subject<boolean>();

  public getObservable(): Observable<boolean> {
    return this._subject.asObservable();
  }

  public start(): void {
    this._subject.next(true);
  }

  public complete(): void {
    this._subject.next(false);
  }
}
