import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, Validator, Validators} from '@angular/forms'

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  
  searchForm: FormGroup;
  searchError: boolean = false;
  
  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.createSearchForm();
  }

  createSearchForm(){
    this.searchForm = this.fb.group({
      phrase: ['', Validators.required]
    });
  }
  
  submitSearchForm(){
    console.log("Form submited!")
  }
}
