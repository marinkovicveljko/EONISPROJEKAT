import { Product } from "../products/product.model";

export interface Review {
  id: number;
  rating: number;
  comment: string;
  date: string;
}

export interface User {
  id: number;
  name: string;
  surname: string;
  email: string;
  role: string;
}

export interface Address {
  id: number;
  street: string;
  city: string;
  postalCode: string;
  country: string;
}

export interface Payment {
  id: number;
  method: string;
  status: string;
  amount: number;
  paymentDate: string;
}

export interface OrderItem {
  id: number;
  quantity: number;
  pricePerUnit: number;
  total: number;
  product: Product;
}

export interface Coupon {
  id: number;
  code: string;
  discountPercent: number;
  expiryDate: string;
}

export interface Order {
  id: number;
  createdDate: string;
  status: string;
  totalPrice: number;
  shippingDate: string;
  discount: number;
  note?: string;
  user: User;
  address: Address;
  payment: Payment;
  items: OrderItem[];
  coupon?: Coupon;
}
