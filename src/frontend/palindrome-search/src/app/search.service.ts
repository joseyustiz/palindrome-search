import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {map, catchError, tap} from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';
import { SearchResult } from './search-result';
import { Search } from './search';




@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private productsUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) { }

  searchProduct(search: Search): Observable<SearchResult>{
    return this.http.get<SearchResult>(`${this.productsUrl}/?phrase=${search.phrase}&page=${search.page}&size=${search.size}`)
      .pipe(
        tap((result: SearchResult) => console.log(`products=${result}`)),
        catchError(this.handleError<SearchResult>('searchProduct'))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
