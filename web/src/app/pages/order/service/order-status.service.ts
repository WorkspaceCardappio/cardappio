import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";

@Injectable()
export class OrderStatusService {

  resource: string = `${environment.apiUrl}/order-status`;

  constructor(
    private http: HttpClient
  ) {}

  findAll(): Observable<any[]> {

    return this.http.get<any[]>(this.resource);
  }

}
