import { ErrorHandler, Injectable, Injector, NgZone } from "@angular/core";
import { MensajeService } from "../mensajes/service/mensaje.service";
import { IMensaje } from "../mensajes/model/i-mensaje";
import { environment } from "src/environments/environment";

@Injectable()
export class AppErrorHandler implements ErrorHandler {

    private _service: MensajeService | undefined;

    constructor(private _injector: Injector, private _ngZone: NgZone) { }

    handleError(error: any): void {
      let message: IMensaje;
      console.log(error);
      if (!this._service) {
        this._service = this._injector.get(MensajeService);
      }

      if(error.rejection) {
        error = error.rejection;
      }

      if(error.body) {
        if(error.body.message) {
          message = { text: error.body.message, num: error.status };
        }
        else if(error.body.error) {
          message = { text: error.body.error, num: error.status };
        }
        else {
          if(error.status == 0) {
            message = { text: "Error al conectarse al servicio", num: error.status };
          }
          else {
            message = { text: error.body, num: error.status };
          }
        }
      }
      else if(error.error) {
        message = { text: error.error, num: error.status };
      }
      else if(error.message) {
        message = { text: error.message, num: error.status };
      }
      else {
        message = { text: error, num: error.status };
      }

      if (!environment.production)
        console.log(error);

      this._ngZone.run(() => {
        this._service?.showMessage(message);
      });
    }

}
