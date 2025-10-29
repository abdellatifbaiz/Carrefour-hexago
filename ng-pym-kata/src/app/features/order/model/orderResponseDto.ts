import {InstallmentDto} from './installmentDto';

export interface OrderResponseDto {
  id: string;
  amount: number;
  paymentOption: string;
  installments: InstallmentDto[];
}
