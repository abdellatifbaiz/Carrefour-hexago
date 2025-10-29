import {Component, effect, inject, OnDestroy, OnInit, Signal, signal, WritableSignal} from '@angular/core';

import {OrderService} from '../../service/order-service';
import {OrderResponseDto} from '../../model/orderResponseDto';
import {PaymentOption} from '../../model/paymentOption';
import {NgClass} from '@angular/common';
import {PopupOption} from '../popup-option/popup-option';
import {MatDialog} from '@angular/material/dialog';
import {environment} from '../../../../../environment';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-order-list-component',
  standalone:false,
  templateUrl: './order-list-component.html',
  styleUrl: './order-list-component.css'
})
export class OrderListComponent implements OnInit, OnDestroy{


  dialog = inject(MatDialog);
  orderService = inject(OrderService);
  orders = signal<OrderResponseDto[] | undefined>(undefined);
  page = environment.page;
  pageSize = environment.pageSize;
  totalItems = 0;
  getAllOrdersSubscription:Subscription = new Subscription();
  readonly paymentOptions = signal<PaymentOption[]>([
    {value: 'THREE_TIMES_NO_FEES', label: 'Pay in 3 (No Fees)'},
    {value: 'FOUR_TIMES_WITH_FEES', label: 'Pay in 4 (With 5% Fees)'},
    {value: 'BANK_TRANSFER', label: 'Virement bancaire + 1€'},
  ]);

  ngOnInit() :void{
    this.loadOrderData();
  }

  loadOrderData(){
    this.getAllOrdersSubscription = this.orderService.findAllOrders(this.page-1,this.pageSize).subscribe((res:any) =>{
      this.orders.set(res.content);
      this.totalItems = res.totalElements;
    })
  }

  getOptionLabel(value: string | undefined): string {
    const opt = this.paymentOptions().find(o => o.value === value);
    return opt ? opt.label : '';
  }

  openPopup(order: OrderResponseDto) {
    const dialogRef = this.dialog.open(PopupOption, {
      width: '2300px',
      data: { order }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const updatedOrder  = this.orders()?.map(o =>{
          return o.id === result.id?result:o;
        })
        this.orders.set(updatedOrder)
        console.log('Order confirmed:', result);
      }
    });

  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber;
    this.loadOrderData();
  }

  ngOnDestroy() {
    this.getAllOrdersSubscription.unsubscribe();
  }

}
