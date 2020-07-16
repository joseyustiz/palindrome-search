import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import {map, catchError, tap} from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';
import { SearchResult } from './search-result';
import { Search } from './search';




@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private defaultResult: SearchResult = {
    content: [],
    pageable: {
      sort: {
        unsorted: false,
        sorted: false,
        empty: true
      },
      pageNumber: 0,
      pageSize: 0,
      offset: 0,
      unpaged: false,
      paged: false
    },
    totalPages: 1,
    totalElements: 0,
    last: true,
    numberOfElements: 0,
    first: true,
    sort: {
      unsorted: false,
      sorted: false,
      empty: true
    },
    size: 0,
    number: 0,
    empty: true,
    errorMessage:""
  };

  private productsUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) { }

  searchProduct(search: Search): Observable<SearchResult>{
    return this.http.get<SearchResult>(`${this.productsUrl}/?phrase=${search.phrase}&page=${search.page}&size=${search.size}`)
      .pipe(
        tap((result: SearchResult) => console.log(`products=${result}`)),
        catchError(this.handleError<SearchResult>('searchProduct', this.defaultResult))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: SearchResult) {
    return (error: HttpErrorResponse): Observable<SearchResult> => {
      result.errorMessage = "Invalid phrase! Numbers, spanish characters and spaces are allowed only";
      return of(result as SearchResult);
    };
  }
}
