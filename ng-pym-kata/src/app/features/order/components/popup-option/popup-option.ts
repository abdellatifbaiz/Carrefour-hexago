import {
  ChangeDetectionStrategy,
  Component,
  inject, OnDestroy,
  output,
  signal
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OrderService } from '../../service/order-service';
import { OrderResponseDto } from '../../model/orderResponseDto';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-popup-option',
  standalone: false,
  templateUrl: './popup-option.html',
  styleUrls: ['./popup-option.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PopupOption implements OnDestroy{

  dialogRef = inject(MatDialogRef<PopupOption>);
  orderService = inject(OrderService);
  confiremSave = output();
  paymentOptionSelected = signal('');
  genreatedOrder = signal<OrderResponseDto | undefined >(undefined)
  errorMessage  = signal(null);
  data = inject(MAT_DIALOG_DATA, { optional: true });
  order = signal<OrderResponseDto>(this.data?.order);
  genreatedOrderSubscribtion : Subscription = new Subscription();


  selectOption(option: string): void {
    this.paymentOptionSelected.update(op => op = option);
    this.generateInstallment();
  }


  confirm(){
    if (this.genreatedOrder())
    {
      this.orderService.updateOrder(this.genreatedOrder())
      this.dialogRef.close(this.genreatedOrder());
    }
  }

  generateInstallment(){

    const o = this.order();
    const option = this.paymentOptionSelected();
    const orderParam = {
      id: o?.id,
      amount: o?.amount,
      paymentOption: option,
      installments: []
    };

    if (orderParam) {
      this.genreatedOrderSubscribtion = this.orderService.generateInstallment(orderParam).subscribe(
        {
          next: (res) =>{
            this.genreatedOrder.set(res);
          },
          error : (error ) => {
            this.errorMessage.set(error.error.error);
          }
        }
      )
    }
  }

  closePopup(){
    this.dialogRef.close();
  }

  ngOnDestroy() {
    this.genreatedOrderSubscribtion.unsubscribe();
  }


}
