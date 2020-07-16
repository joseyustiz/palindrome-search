import { Product } from './product';
import { Sort } from './sort';
import { Pageable } from './pageable';

export interface SearchResult {
    content: Product[];
    pageable: Pageable;
    last: boolean;
    totalPages: number;
    totalElements: number;
    numberOfElements: number;
    first: boolean;
    sort: Sort;
    size: number;
    number: number;
    empty: boolean;
    errorMessage: string;  
  }
