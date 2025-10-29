import {Injectable, computed, signal, resource, inject} from '@angular/core';

import {environment} from '../../../../environment';
import {OrderResponseDto} from '../model/orderResponseDto';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


@Injectable({ providedIn: 'root' })
export class OrderService {

  private apiUrl = environment.apiUrl;
  http = inject(HttpClient)

  generateInstallment(order:OrderResponseDto):Observable<OrderResponseDto>{
    return this.http.post<OrderResponseDto>(`${this.apiUrl}/orders/generate`,order);
  }

  updateOrder(order:OrderResponseDto|undefined):Observable<OrderResponseDto>{
    return this.http.post<OrderResponseDto>(`${this.apiUrl}/orders/updateOrder`,order);
  }

  findAllOrders(page:any,size:any) {
    return this.http.get(`${this.apiUrl}/orders/findAll?page=${page}&size=${size}`)
  }

}
