import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Page } from '../model/page.model';

@Injectable()
export class EntityService<V, K> {

  private readonly PAGE_SIZE: string = 'page_size';
  private readonly SEARCH: string = 'search';
  private readonly DEFAULT_PAGE_SIZE: number = 20;

  resource: string = '';

  constructor(
    public httpClient: HttpClient
  ) { }

  public findAllDTO(completeParamsRequest: string): Observable<Page<V>> {

    return this.httpClient.get<Page<V>>(`${this.resource}/dto?${completeParamsRequest}`);
  }

  public findAll(pageSize: number = this.DEFAULT_PAGE_SIZE, search?: string): Observable<V[]> {

    const params = new HttpParams()
      .set(this.PAGE_SIZE, pageSize.toString())
      .set(this.SEARCH, search ? search : '');

    return this.httpClient.get<Page<V>>(this.resource, { params: params })
      .pipe(map((page: Page<V>) => page.content));
  }

  public findById(id: K): Observable<V> {

    return this.httpClient.get<V>(`${this.resource}/${id}`);
  }

  public create(newDTO: V): Observable<HttpResponse<K>> {

    return this.httpClient.post<K>(this.resource, newDTO, {
      observe: 'response'
    });
  }

  public update(id: K, updateDTO: V): Observable<K> {

    return this.httpClient.put<K>(`${this.resource}/${id}`, updateDTO);
  }

  public delete(id: K): Observable<void> {

    return this.httpClient.delete<void>(`${this.resource}/${id}`);
  }
}
