import { Component, OnDestroy, OnInit } from '@angular/core';
import { Loader, LoaderService } from '../../service/loader.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent implements OnInit, OnDestroy {

  private _subscription!: Subscription;
  private _loaded: boolean = false;

  constructor(private _service: LoaderService) { }

  ngOnInit(): void {
    this._subscription = this._service.getObservable().subscribe((ref: Loader) => {
      this._loaded = ref.loaded;
    });
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  get loaded(): boolean {
    return this._loaded;
  }

}
