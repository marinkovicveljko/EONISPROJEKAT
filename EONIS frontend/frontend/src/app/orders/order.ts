// order-item.ts
export interface OrderItem {
  productId: number;
  quantity: number;
}

// order.ts
export interface Order {
  id?: number;
  userId: number;
  address: string;
  note?: string;
  items: OrderItem[];

  // polja koja backend vraća (opciono)
  totalPrice?: number;
  status?: string;
  createdDate?: string;
}
