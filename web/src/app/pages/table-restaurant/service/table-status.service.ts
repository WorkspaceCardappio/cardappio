import { Injectable } from '@angular/core';
import { environment } from "../../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable()
export class TableStatusService {

  resource: string = `${environment.apiUrl}/table-status`;

  constructor(
    private http: HttpClient
  ) {}

  findAll(): Observable<any[]> {

    return this.http.get<any[]>(this.resource);
  }

}
