import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TicketStatusService {

  resource: string = `${environment.apiUrl}/ticket-status`;

  constructor(public http: HttpClient) {}

  find(): Observable<any[]> {
    return this.http.get<any[]>(this.resource);
  }

}
