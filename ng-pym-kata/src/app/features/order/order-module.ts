import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {OrderListComponent} from './components/order-list-component/order-list-component';
import {RouterModule, Routes} from '@angular/router';
import {PopupOption} from './components/popup-option/popup-option';
import {MatButton} from '@angular/material/button';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {MatDialogActions, MatDialogContent, MatDialogModule} from '@angular/material/dialog';
import {MatTable, MatTableModule} from '@angular/material/table';
import {MatChip} from '@angular/material/chips';
import {NgbPagination} from '@ng-bootstrap/ng-bootstrap';


const routes:Routes = [
  {
    path:'',
    component:OrderListComponent
  }
]


@NgModule({
  declarations: [OrderListComponent,PopupOption],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatButton,
    MatCard,
    MatDialogContent,
    MatDialogActions,
    MatChip,
    MatDialogModule,
    NgbPagination
  ]
})
export class OrderModule { }
