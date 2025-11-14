import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { EntityService } from '../../../entity-service/entity.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends EntityService<any, string> {

  override resource: string = `${environment.apiUrl}/categories`;

  constructor(public http: HttpClient) {
    super(http);
  }

  saveWithImage(dto: any, file: any) {

    const formData = new FormData();
    formData.append('file', file);
    formData.append('dto', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

    return this.http.post(`${ this.resource }/with-image`, formData);
  }

  updateWithImage(id: any, dto: any, file: any) {

    const formData = new FormData();
    formData.append('file', file);
    formData.append('dto', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

    return this.http.put(`${ this.resource }/${ id }/with-image`, formData);
  }
}
