import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable()
export class S3StorageService {

  resource: string = `${environment.apiUrl}/s3-storage`;

  constructor(public http: HttpClient) {}

  getImage(name: string) {

    return this.http.get(`${this.resource}/image/${name}`);
  }

}
