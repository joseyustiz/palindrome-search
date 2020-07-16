import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, Validator, Validators} from '@angular/forms'
import {SearchResult} from '../search-result'
import {SearchService} from '../search.service'
import {Search} from '../search'


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  currentPage: number;
  searchForm: FormGroup;
  searchError: boolean = false;
  searchPhrase: string;
  searchResult: SearchResult = {
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
    errorMessage: ""
  };

  constructor(private fb: FormBuilder, private service: SearchService) { }
  ngOnInit(): void {
    this.createSearchForm();
  }

  createSearchForm(){
    this.searchForm = this.fb.group({
      phrase: ['', Validators.required]
    });
  }

  submitSearchForm(){
    const search: Search = {
      phrase: this.searchForm.get("phrase").value,
      page: 1,
      size: 20
    };
    this.searchPhrase="";
    console.log("submitin !!!")
    this.service.searchProduct(search)
    .subscribe(result => {this.searchResult = result; this.currentPage= result.pageable.pageNumber;this.searchPhrase=search.phrase});

  }
}
