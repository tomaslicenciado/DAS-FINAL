import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { CoreModule } from './core/core.module';
import { ApiModule } from './api/api.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ResourceModule } from '@ngx-resource/handler-ngx-http';
import { HomeModule } from './home/home.module';
import { MssApiService } from './api/resolvers/mss-api.service';
import { MssApiRestResourceService } from './api/resources/mss-api-rest-resource.service';
import { LoaderInterceptor } from './core/loader/interceptor/loader.interceptor';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppErrorHandler } from './core/errorHandler/app-error-handler';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    CoreModule,
    ApiModule,
    HttpClientModule,
    ResourceModule.forRoot(),
    HomeModule,
    NgbModule
  ],
  providers: [
    MssApiService,
    MssApiRestResourceService,
    {provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true},
    NgbActiveModal,
    {provide: ErrorHandler, useClass: AppErrorHandler}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
